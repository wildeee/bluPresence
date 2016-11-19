package br.com.wilderossi.blupresence.vo;

import java.util.List;

public class TurmaVO {

    private String id;
    private String descricao;
    private String professor;
    private List<String> alunos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public List<String> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<String> alunos) {
        this.alunos = alunos;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
