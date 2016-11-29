package br.com.wilderossi.blupresence.vo;

public class EnviarDadosAlunoVO {

    private final String idAluno;
    private final Boolean presente;

    public EnviarDadosAlunoVO(String idAluno, Boolean presente) {
        this.idAluno = idAluno;
        this.presente = presente;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public Boolean getPresente() {
        return presente;
    }
}
