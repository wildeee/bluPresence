package br.com.wilderossi.blupresence.vo;

import java.util.Calendar;
import java.util.List;

public class ChamadaEditVO {

    private Long id;
    private Long idTurma;
    private Calendar data;
    private Boolean sincronizado;
    private List<AlunoPresencaVO> alunos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Long idTurma) {
        this.idTurma = idTurma;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public List<AlunoPresencaVO> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<AlunoPresencaVO> alunos) {
        this.alunos = alunos;
    }

    public Boolean getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(Boolean sincronizado) {
        this.sincronizado = sincronizado;
    }
}
