package br.com.wilderossi.blupresence.transaction.database;

import android.database.sqlite.SQLiteDatabase;

public class TabelaChamada implements TableCreator {

    public static final String TABELA = "chamadas";

    public static final String ID           = "id";
    public static final String SINCRONIZADO = "sincronizado";
    public static final String TURMA_FK     = "turmaId";
    public static final String DATA         = "data";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement, "
                + SINCRONIZADO + " integer, "
                + TURMA_FK + " integer, "
                + DATA + " text"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
