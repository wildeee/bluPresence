package br.com.wilderossi.blupresence.transaction;

public class AlunoPresenca {
    private Long id;
    private Long idChamada;
    private Boolean presente;

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

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }
}
