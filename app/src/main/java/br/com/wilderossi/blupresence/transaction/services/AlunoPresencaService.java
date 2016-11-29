package br.com.wilderossi.blupresence.transaction.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.wilderossi.blupresence.transaction.AlunoPresenca;
import br.com.wilderossi.blupresence.transaction.database.CriaBanco;
import br.com.wilderossi.blupresence.transaction.database.TabelaAlunoPresenca;
import br.com.wilderossi.blupresence.vo.AlunoPresencaVO;
import br.com.wilderossi.blupresence.vo.EnviarDadosAlunoVO;

public class AlunoPresencaService {
    private SQLiteDatabase db;
    private CriaBanco banco;

    private final AlunoService alunoService;

    public AlunoPresencaService(Context context) {
        banco = new CriaBanco(context);
        alunoService = new AlunoService(context);
    }

    public Long salvar(AlunoPresenca alunoPresenca) throws DatabaseServiceException {
        ContentValues valores;
        long resultado = -1;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(TabelaAlunoPresenca.CHAMADA_FK, alunoPresenca.getIdChamada());
        valores.put(TabelaAlunoPresenca.ALUNO_FK, alunoPresenca.getIdAluno());
        valores.put(TabelaAlunoPresenca.PRESENTE, alunoPresenca.getPresenteSQLite());

        if (alunoPresenca.getId() != null && alunoPresenca.getId() != 0){
            String where = TabelaAlunoPresenca.ID + " = " + alunoPresenca.getId();
            resultado = db.update(TabelaAlunoPresenca.TABELA, valores, where, null);
        } else {
            resultado = db.insert(TabelaAlunoPresenca.TABELA, null, valores);
        }

        db.close();

        if (resultado == -1){
            throw new DatabaseServiceException("Erro ao salvar Chamada");
        }

        return resultado;
    }

    public boolean remover(Long id){
        String where = TabelaAlunoPresenca.ID + " = " + id;
        db = banco.getReadableDatabase();
        int resultado = db.delete(TabelaAlunoPresenca.TABELA, where, null);
        db.close();
        return resultado != -1;
    }

    public List<AlunoPresenca> buscar(){
        Cursor dados;
        List<AlunoPresenca> instituicoes = new ArrayList<>();
        String[] campos =  {TabelaAlunoPresenca.ID, TabelaAlunoPresenca.CHAMADA_FK, TabelaAlunoPresenca.ALUNO_FK, TabelaAlunoPresenca.PRESENTE};

        db = banco.getReadableDatabase();
        dados = db.query(TabelaAlunoPresenca.TABELA, campos, null, null, null, null, null, null);

        if(dados != null && dados.moveToFirst()){
            do {
                instituicoes.add(new AlunoPresenca(dados.getLong(0), dados.getLong(1), dados.getLong(2), dados.getInt(3)));
            } while (dados.moveToNext());
        }

        db.close();
        return instituicoes;
    }

    public List<AlunoPresenca> findByChamada(Long chamadaId) {
        List<AlunoPresenca> alunos = new ArrayList<>();

        for (AlunoPresenca alunoPresenca : this.buscar()){
            if (chamadaId.equals(alunoPresenca.getIdChamada())){
                alunos.add(alunoPresenca);
            }
        }

        return alunos;
    }

    public void removeByChamadaId(Long id) {
        for (AlunoPresenca aluno : this.buscar()){
            if (id.equals(aluno.getIdChamada())){
                this.remover(aluno.getId());
            }
        }
    }

    public List<EnviarDadosAlunoVO> getDadosAlunosParaEnvio(Long idChamada) {
        List<EnviarDadosAlunoVO> dadosAlunos = new ArrayList<>();

        for (AlunoPresenca aluno : this.buscar()){
            if (idChamada.equals(aluno.getIdChamada())){
                dadosAlunos.add(new EnviarDadosAlunoVO(alunoService.getServerIdById(aluno.getIdAluno()) , aluno.getPresente()));
            }
        }

        return dadosAlunos;
    }
}
