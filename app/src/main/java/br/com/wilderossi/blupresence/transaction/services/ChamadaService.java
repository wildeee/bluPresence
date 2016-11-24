package br.com.wilderossi.blupresence.transaction.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.wilderossi.blupresence.transaction.Chamada;
import br.com.wilderossi.blupresence.transaction.database.CriaBanco;
import br.com.wilderossi.blupresence.transaction.database.TabelaChamada;

public class ChamadaService {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public ChamadaService(Context context) {
        banco = new CriaBanco(context);
    }

    public Long salvar(Chamada instituicao) throws DatabaseServiceException {
        ContentValues valores;
        long resultado = -1;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(TabelaChamada.SINCRONIZADO, instituicao.getSincronizadoSQLite());
        valores.put(TabelaChamada.TURMA_FK, instituicao.getIdTurma());
        valores.put(TabelaChamada.DATA, instituicao.getDataSQLite());

        if (instituicao.getId() != null && instituicao.getId() != 0){
            String where = TabelaChamada.ID + " = " + instituicao.getId();
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

    public boolean remover(Integer id){
        String where = TabelaChamada.ID + " = " + id;
        db = banco.getReadableDatabase();
        int resultado = db.delete(TabelaChamada.TABELA, where, null);
        db.close();
        return resultado != -1;
    }

    public List<Chamada> buscar(){
        Cursor dados;
        List<Chamada> instituicoes = new ArrayList<>();
        String[] campos =  {TabelaChamada.ID, TabelaChamada.SINCRONIZADO, TabelaChamada.TURMA_FK, TabelaChamada.DATA};

        db = banco.getReadableDatabase();
        dados = db.query(TabelaChamada.TABELA, campos, null, null, null, null, null, null);

        if(dados != null && dados.moveToFirst()){
            do {
                instituicoes.add(new Chamada(dados.getLong(0), dados.getInt(1), dados.getLong(2), dados.getString(3)));
            } while (dados.moveToNext());
        }

        db.close();
        return instituicoes;
    }
}
