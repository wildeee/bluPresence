package br.com.wilderossi.blupresence.transaction.services;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.wilderossi.blupresence.transaction.Instituicao;
import br.com.wilderossi.blupresence.transaction.database.CriaBanco;

public class InstituicaoService {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public InstituicaoService(Context context) {
        banco = new CriaBanco(context);
    }

    public boolean salvar(Instituicao instituicao) {
        ContentValues valores;
        long resultado = -1;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(CriaBanco.NOME, instituicao.getNome());
        valores.put(CriaBanco.URL, instituicao.getUrl());



        if (instituicao.getId() != null && instituicao.getId() != 0){
            String where = CriaBanco.ID + " = " + instituicao.getId();
            resultado = db.update(CriaBanco.TABELA, valores, where, null);
        } else {
            resultado = db.insert(CriaBanco.TABELA, null, valores);
        }

        db.close();
        return resultado != -1;
    }

    public boolean remover(Integer id){
        String where = CriaBanco.ID + " = " + id;
        db = banco.getReadableDatabase();
        int resultado = db.delete(CriaBanco.TABELA, where, null);
        db.close();
        return resultado != -1;
    }

    public List<Instituicao> buscar(){
        Cursor dados;
        List<Instituicao> instituicoes = new ArrayList<>();
        String[] campos =  {CriaBanco.ID, CriaBanco.NOME, CriaBanco.URL};

        db = banco.getReadableDatabase();
        dados = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(dados != null && dados.moveToFirst()){
            do {
                instituicoes.add(new Instituicao(dados.getInt(0), dados.getString(1), dados.getString(2), dados.getString(3)));
            } while (dados.moveToNext());
        }

        db.close();
        return instituicoes;
    }
}