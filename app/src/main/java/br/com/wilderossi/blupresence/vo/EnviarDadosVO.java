package br.com.wilderossi.blupresence.vo;

import java.util.Calendar;
import java.util.List;

public class EnviarDadosVO {

    private Long idChamadaApp;
    private String idTurma;
    private Calendar data;
    private List<EnviarDadosAlunoVO> alunos;

    public Long getIdChamadaApp() {
        return idChamadaApp;
    }

    public void setIdChamadaApp(Long idChamadaApp) {
        this.idChamadaApp = idChamadaApp;
    }

    public String getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(String idTurma) {
        this.idTurma = idTurma;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public List<EnviarDadosAlunoVO> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<EnviarDadosAlunoVO> alunos) {
        this.alunos = alunos;
    }
}