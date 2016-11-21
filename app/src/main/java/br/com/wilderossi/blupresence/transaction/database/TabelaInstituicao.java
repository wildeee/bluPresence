package br.com.wilderossi.blupresence.transaction.database;

import android.database.sqlite.SQLiteDatabase;

public class TabelaInstituicao implements TableCreator {

    public static final String TABELA = "instituicoes";

    public static final String ID = "id";
    public static final String NOME = "nome";
    public static final String URL = "url";
    public static final String ID_PROFESSOR = "idprofessor";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement, "
                + NOME + " text, "
                + URL + " text, "
                + ID_PROFESSOR + " text"
                + ");";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
