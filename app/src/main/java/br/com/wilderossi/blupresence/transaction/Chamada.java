package br.com.wilderossi.blupresence.transaction;

import java.util.Calendar;

import br.com.wilderossi.blupresence.components.SubtitledAdapter;
import br.com.wilderossi.blupresence.util.DateUtils;

public class Chamada implements SubtitledAdapter {

    private Long id;
    private Boolean sincronizado;
    private Long idTurma;
    private Calendar data;

    public Chamada() { }

    public Chamada(Long id, Integer sincronizado, Long idTurma, String data) {
        this.id = id;
        this.sincronizado = sincronizado != 0;
        this.idTurma = idTurma;
        this.data = DateUtils.stringToCalendar(data);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(Boolean sincronizado) {
        this.sincronizado = sincronizado;
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

    public Integer getSincronizadoSQLite() {
        return sincronizado ? 1 : 0;
    }

    public String getDataStr() {
        return DateUtils.getDateString(data);
    }

    @Override
    public String getMainString() {
        return this.getDataStr();
    }

    @Override
    public String getSubtitle() {
        return sincronizado ? "" : "Não sincronizado";
    }
}
