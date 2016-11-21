package br.com.wilderossi.blupresence.transaction.services;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.wilderossi.blupresence.transaction.Instituicao;
import br.com.wilderossi.blupresence.transaction.database.CriaBanco;
import br.com.wilderossi.blupresence.transaction.database.TabelaInstituicao;

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

        valores.put(TabelaInstituicao.NOME, instituicao.getNome());
        valores.put(TabelaInstituicao.URL, instituicao.getUrl());
        valores.put(TabelaInstituicao.ID_PROFESSOR, instituicao.getIdProfessor());



        if (instituicao.getId() != null && instituicao.getId() != 0){
            String where = TabelaInstituicao.ID + " = " + instituicao.getId();
            resultado = db.update(TabelaInstituicao.TABELA, valores, where, null);
        } else {
            resultado = db.insert(TabelaInstituicao.TABELA, null, valores);
        }

        db.close();
        return resultado != -1;
    }

    public boolean remover(Integer id){
        String where = TabelaInstituicao.ID + " = " + id;
        db = banco.getReadableDatabase();
        int resultado = db.delete(TabelaInstituicao.TABELA, where, null);
        db.close();
        return resultado != -1;
    }

    public List<Instituicao> buscar(){
        Cursor dados;
        List<Instituicao> instituicoes = new ArrayList<>();
        String[] campos =  {TabelaInstituicao.ID, TabelaInstituicao.NOME, TabelaInstituicao.URL, TabelaInstituicao.ID_PROFESSOR};

        db = banco.getReadableDatabase();
        dados = db.query(TabelaInstituicao.TABELA, campos, null, null, null, null, null, null);

        if(dados != null && dados.moveToFirst()){
            do {
                instituicoes.add(new Instituicao(dados.getLong(0), dados.getString(1), dados.getString(2), dados.getString(3)));
            } while (dados.moveToNext());
        }

        db.close();
        return instituicoes;
    }

    public Instituicao getById(Long id){
        for (Instituicao instituicao : this.buscar()){
            if (instituicao.getId().equals(id))
                return instituicao;
        }
        return null;
    }
}