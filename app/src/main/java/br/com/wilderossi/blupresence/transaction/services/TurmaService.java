package br.com.wilderossi.blupresence.transaction.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.wilderossi.blupresence.transaction.Turma;
import br.com.wilderossi.blupresence.transaction.database.CriaBanco;
import br.com.wilderossi.blupresence.transaction.database.TabelaTurma;
import br.com.wilderossi.blupresence.vo.TurmaInstituicaoVO;

public class TurmaService {
    private SQLiteDatabase db;
    private CriaBanco banco;
    private Context context;

    public TurmaService(Context context) {
        this.context = context;
        banco = new CriaBanco(context);
    }

    public Long salvar(Turma turma) throws DatabaseServiceException {
        ContentValues valores;
        long resultado = -1;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(TabelaTurma.SERVERID, turma.getServerId());
        valores.put(TabelaTurma.DESCRICAO, turma.getDescricao());
        valores.put(TabelaTurma.INSTITUICAO_FK, turma.getInstituicaoId());



        if (turma.getId() != null && turma.getId() != 0){
            String where = TabelaTurma.ID + " = " + turma.getId();
            resultado = db.update(TabelaTurma.TABELA, valores, where, null);
        } else {
            resultado = db.insert(TabelaTurma.TABELA, null, valores);
        }

        db.close();

        if (resultado == -1){
            throw new DatabaseServiceException("Não foi possível salvar.");
        }
        return resultado;
    }

    public boolean remover(Integer id){
        String where = TabelaTurma.ID + " = " + id;
        db = banco.getReadableDatabase();
        int resultado = db.delete(TabelaTurma.TABELA, where, null);
        db.close();
        return resultado != -1;
    }

    public List<Turma> buscar(){
        Cursor dados;
        List<Turma> turmas = new ArrayList<>();
        String[] campos =  {TabelaTurma.ID, TabelaTurma.SERVERID, TabelaTurma.INSTITUICAO_FK, TabelaTurma.DESCRICAO};

        db = banco.getReadableDatabase();
        dados = db.query(TabelaTurma.TABELA, campos, null, null, null, null, null, null);

        if(dados != null && dados.moveToFirst()){
            do {
                turmas.add(new Turma(dados.getLong(0), dados.getString(1), dados.getLong(2), dados.getString(3)));
            } while (dados.moveToNext());
        }

        db.close();
        return turmas;
    }

    public List<TurmaInstituicaoVO> buscarTurmasInstituicao(){
        List<TurmaInstituicaoVO> turmasInstituicao = new ArrayList<>();
        InstituicaoService instituicaoService = new InstituicaoService(context);

        for (Turma turma : this.buscar()){
            turmasInstituicao.add(new TurmaInstituicaoVO(turma, instituicaoService.getById(turma.getInstituicaoId())));
        }

        return turmasInstituicao;
    }

    public String getServerIdById(Long idTurma) {
        for (Turma t : this.buscar()){
            if (idTurma.equals(t.getId())){
                return t.getServerId();
            }
        }
        return null;
    }
}
