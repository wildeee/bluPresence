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

public class AlunoPresencaService {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public AlunoPresencaService(Context context) {
        banco = new CriaBanco(context);
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

    public boolean remover(Integer id){
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
}
