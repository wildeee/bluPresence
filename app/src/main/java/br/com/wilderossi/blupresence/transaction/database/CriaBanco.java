package br.com.wilderossi.blupresence.transaction.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CriaBanco extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "banco.db";
    private static final int VERSAO = 1;

    private static final TableCreator[] tabelas = {
            new TabelaInstituicao(),
            new TabelaTurma(),
            new TabelaAluno()
    };

    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (TableCreator tabela : tabelas){
            tabela.onCreate(db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (TableCreator tabela : tabelas){
            tabela.onUpgrade(db, oldVersion, newVersion);
        }
    }
}