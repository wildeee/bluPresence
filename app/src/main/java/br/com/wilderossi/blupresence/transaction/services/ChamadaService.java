package br.com.wilderossi.blupresence.transaction.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.wilderossi.blupresence.transaction.AlunoPresenca;
import br.com.wilderossi.blupresence.transaction.Chamada;
import br.com.wilderossi.blupresence.transaction.database.CriaBanco;
import br.com.wilderossi.blupresence.transaction.database.TabelaChamada;
import br.com.wilderossi.blupresence.vo.AlunoPresencaVO;
import br.com.wilderossi.blupresence.vo.ChamadaEditVO;
import br.com.wilderossi.blupresence.vo.EnviarDadosAlunoVO;
import br.com.wilderossi.blupresence.vo.EnviarDadosVO;

public class ChamadaService {
    private SQLiteDatabase db;
    private CriaBanco banco;

    private final AlunoPresencaService alunoPresencaService;
    private final AlunoService alunoService;
    private final TurmaService turmaService;

    public ChamadaService(Context context) {
        banco = new CriaBanco(context);
        alunoPresencaService = new AlunoPresencaService(context);
        alunoService = new AlunoService(context);
        turmaService = new TurmaService(context);
    }

    public Long salvar(Chamada chamada) throws DatabaseServiceException {
        ContentValues valores;
        long resultado = -1;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(TabelaChamada.SINCRONIZADO, chamada.getSincronizadoSQLite());
        valores.put(TabelaChamada.TURMA_FK, chamada.getIdTurma());
        valores.put(TabelaChamada.DATA, chamada.getDataStr());

        if (chamada.getId() != null && chamada.getId() != 0){
            String where = TabelaChamada.ID + " = " + chamada.getId();
            resultado = db.update(TabelaChamada.TABELA, valores, where, null);
        } else {
            resultado = db.insert(TabelaChamada.TABELA, null, valores);
        }

        db.close();

        if (resultado == -1){
            throw new DatabaseServiceException("Erro ao salvar Chamada");
        }

        return resultado;
    }

    public boolean remover(Long id){
        String where = TabelaChamada.ID + " = " + id;
        db = banco.getReadableDatabase();
        int resultado = db.delete(TabelaChamada.TABELA, where, null);
        db.close();
        return resultado != -1;
    }

    public List<Chamada> buscar(){
        Cursor dados;
        List<Chamada> chamadas = new ArrayList<>();
        String[] campos =  {TabelaChamada.ID, TabelaChamada.SINCRONIZADO, TabelaChamada.TURMA_FK, TabelaChamada.DATA};

        db = banco.getReadableDatabase();
        dados = db.query(TabelaChamada.TABELA, campos, null, null, null, null, null, null);

        if(dados != null && dados.moveToFirst()){
            do {
                chamadas.add(new Chamada(dados.getLong(0), dados.getInt(1), dados.getLong(2), dados.getString(3)));
            } while (dados.moveToNext());
        }

        db.close();
        return chamadas;
    }

    public List<Chamada> findByTurma(Long idTurma) {
        List<Chamada> returnList = new ArrayList<>();
        for (Chamada chamada : this.buscar()){
            if (idTurma.equals(chamada.getIdTurma())){
                returnList.add(chamada);
            }
        }

        return returnList;
    }

    public ChamadaEditVO findById(Long idChamada) {
        Chamada chamada = null;
        for (Chamada c : this.buscar()){
            if (idChamada.equals(c.getId())){
                chamada = c;
                break;
            }
        }
        if (chamada == null){
            return null;
        }
        ChamadaEditVO vo = new ChamadaEditVO();

        vo.setId(chamada.getId());
        vo.setIdTurma(chamada.getIdTurma());
        vo.setData(chamada.getData());
        vo.setSincronizado(chamada.getSincronizado());

        List<AlunoPresenca> alunosPresenca = alunoPresencaService.findByChamada(chamada.getId());
        List<AlunoPresencaVO> alunosVo = new ArrayList<>();

        for (AlunoPresenca alunoPresenca : alunosPresenca){
            AlunoPresencaVO alunoVo = new AlunoPresencaVO();
            alunoVo.setPresente(alunoPresenca.getPresente());
            alunoVo.setAluno(alunoService.findById(alunoPresenca.getIdAluno()));
            alunoVo.setId(alunoPresenca.getId());
            alunosVo.add(alunoVo);
        }

        vo.setAlunos(alunosVo);
        return vo;
    }

    public List<EnviarDadosVO> getDadosParaEnvio(Long idTurma) {
        List<EnviarDadosVO> dadosEnvio = new ArrayList<>();
        String idTurmaServer = turmaService.getServerIdById(idTurma);

        for (Chamada chamada : findNaoIntegradosByTurma(idTurma)){
            EnviarDadosVO dado = new EnviarDadosVO();
            dado.setData(chamada.getData());
            dado.setIdChamadaApp(chamada.getId());
            dado.setIdTurma(idTurmaServer);
            dado.setAlunos(alunoPresencaService.getDadosAlunosParaEnvio(chamada.getId()));

            dadosEnvio.add(dado);
        }

        return dadosEnvio;
    }

    private List<Chamada> findNaoIntegradosByTurma(Long idTurma){
        List<Chamada> chamadas = new ArrayList<>();

        for (Chamada chamada : this.buscar()){
            if (!chamada.getSincronizado() && idTurma.equals(chamada.getIdTurma())){
                chamadas.add(chamada);
            }
        }

        return chamadas;
    }

    public void setSincronizado(List<EnviarDadosVO> dadosSucesso) {
        List<Long> idsParaAtualizar = new ArrayList<>();
        for (EnviarDadosVO dado : dadosSucesso){
            idsParaAtualizar.add(dado.getIdChamadaApp());
        }
        if (!idsParaAtualizar.isEmpty()){
            atualizaSincronizacao(idsParaAtualizar);
        }
    }

    private void atualizaSincronizacao(List<Long> ids){
        ContentValues valores;
        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(TabelaChamada.SINCRONIZADO, 1);

        StringBuilder where = new StringBuilder()
                .append(TabelaChamada.ID)
                .append(" in (")
                .append(ids.remove(0));

        for (Long id : ids){
            where = where
                    .append(", ")
                    .append(id);
        }

        where = where.append(")");

        long res = db.update(TabelaChamada.TABELA, valores, where.toString(), null);

        db.close();
    }
}
