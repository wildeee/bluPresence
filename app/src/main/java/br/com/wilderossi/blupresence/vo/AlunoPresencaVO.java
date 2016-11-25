package br.com.wilderossi.blupresence.vo;

import br.com.wilderossi.blupresence.transaction.Aluno;

public class AlunoPresencaVO {

    private Long id;
    private Aluno aluno;
    private Boolean presente;

    public AlunoPresencaVO(){
        this.presente = Boolean.FALSE;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
