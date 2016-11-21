package br.com.wilderossi.blupresence.vo;

import br.com.wilderossi.blupresence.transaction.Instituicao;
import br.com.wilderossi.blupresence.transaction.Turma;

public class TurmaInstituicaoVO {

    private Turma turma;
    private Instituicao instituicao;

    public TurmaInstituicaoVO(Turma turma, Instituicao instituicao) {
        this.turma = turma;
        this.instituicao = instituicao;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
}
