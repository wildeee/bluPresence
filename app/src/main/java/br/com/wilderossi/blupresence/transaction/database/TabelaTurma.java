package br.com.wilderossi.blupresence.transaction.database;

import android.database.sqlite.SQLiteDatabase;

public class TabelaTurma implements TableCreator {

    public static final String TABELA = "turmas";

    public static final String ID             = "id";
    public static final String SERVERID       = "serverId";
    public static final String INSTITUICAO_FK = "instituicaoId";
    public static final String DESCRICAO      = "descricao";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement, "
                + SERVERID + " text, "
                + INSTITUICAO_FK + " integer, "
                + DESCRICAO + " text"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
