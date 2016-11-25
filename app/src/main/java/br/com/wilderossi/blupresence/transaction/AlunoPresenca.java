package br.com.wilderossi.blupresence.transaction;

public class AlunoPresenca {

    private Long id;
    private Long idChamada;
    private Long idAluno;
    private Boolean presente;

    public AlunoPresenca() { }

    public AlunoPresenca(Long id, Long idChamada, Long idAluno, Integer presente) {
        this.id = id;
        this.idChamada = idChamada;
        this.idAluno = idAluno;
        this.presente = presente != 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdChamada() {
        return idChamada;
    }

    public void setIdChamada(Long idChamada) {
        this.idChamada = idChamada;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    public Integer getPresenteSQLite() {
        return presente ? 1 : 0;
    }
}
