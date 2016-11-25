package br.com.wilderossi.blupresence.transaction.database;

import android.database.sqlite.SQLiteDatabase;

public class TabelaAlunoPresenca implements TableCreator {

    public static final String TABELA = "alunopresenca";

    public static final String ID         = "id";
    public static final String CHAMADA_FK = "chamadaId";
    public static final String ALUNO_FK   = "alunoId";
    public static final String PRESENTE   = "isPresente";

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String sql = "CREATE TABLE " + TABELA + "("
                + ID + " integer primary key autoincrement, "
                + CHAMADA_FK + " integer, "
                + ALUNO_FK + " integer, "
                + PRESENTE + " integer"
                + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }
}
