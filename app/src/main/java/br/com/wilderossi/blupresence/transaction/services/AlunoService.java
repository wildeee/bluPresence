package br.com.wilderossi.blupresence.transaction.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.wilderossi.blupresence.transaction.Aluno;
import br.com.wilderossi.blupresence.transaction.database.CriaBanco;
import br.com.wilderossi.blupresence.transaction.database.TabelaAluno;

public class AlunoService {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public AlunoService(Context context) {
        banco = new CriaBanco(context);
    }

    public Long salvar(Aluno aluno) throws DatabaseServiceException {
        ContentValues valores;
        long resultado = -1;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(TabelaAluno.SERVERID, aluno.getServerId());
        valores.put(TabelaAluno.NOME, aluno.getNome());
        valores.put(TabelaAluno.TURMA_FK, aluno.getTurmaId());



        if (aluno.getId() != null && aluno.getId() != 0){
            String where = TabelaAluno.ID + " = " + aluno.getId();
            resultado = db.update(TabelaAluno.TABELA, valores, where, null);
        } else {
            resultado = db.insert(TabelaAluno.TABELA, null, valores);
        }

        db.close();

        if (resultado == -1){
            throw new DatabaseServiceException("Não foi possível salvar.");
        }
        return resultado;
    }

    public boolean remover(Integer id){
        String where = TabelaAluno.ID + " = " + id;
        db = banco.getReadableDatabase();
        int resultado = db.delete(TabelaAluno.TABELA, where, null);
        db.close();
        return resultado != -1;
    }

    public List<Aluno> buscar(){
        Cursor dados;
        List<Aluno> alunos = new ArrayList<>();
        String[] campos =  {TabelaAluno.ID, TabelaAluno.SERVERID, TabelaAluno.TURMA_FK, TabelaAluno.NOME};

        db = banco.getReadableDatabase();
        dados = db.query(TabelaAluno.TABELA, campos, null, null, null, null, null, null);

        if(dados != null && dados.moveToFirst()){
            do {
                alunos.add(new Aluno(dados.getLong(0), dados.getString(1), dados.getLong(2), dados.getString(3)));
            } while (dados.moveToNext());
        }

        db.close();
        return alunos;
    }

    public List<Aluno> buscarPorTurma(Long idTurma) {
        List<Aluno> alunos = new ArrayList<>();
        for(Aluno aluno : this.buscar()){
            if (idTurma.equals(aluno.getTurmaId())){
                alunos.add(aluno);
            }
        }

        return alunos;
    }

    public Aluno findById(Long idAluno) {
        for (Aluno aluno : this.buscar()){
            if (idAluno.equals(aluno.getId())){
                return aluno;
            }
        }
        return null;
    }

    public String getServerIdById(Long id) {
        for (Aluno aluno : this.buscar()){
            if (id.equals(aluno.getId())){
                return aluno.getServerId();
            }
        }
        return null;
    }
}
