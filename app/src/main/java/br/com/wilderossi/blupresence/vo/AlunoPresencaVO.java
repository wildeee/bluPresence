package br.com.wilderossi.blupresence.vo;

import br.com.wilderossi.blupresence.transaction.Aluno;

public class AlunoPresencaVO {

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
}
