package br.com.wilderossi.blupresence.transaction;

public class Turma {

    private Long id;
    private String serverId;
    private Long instituicaoId;
    private String descricao;

    public Turma(Long id, String serverId, Long instituicaoId, String descricao) {
        this.id = id;
        this.serverId = serverId;
        this.instituicaoId = instituicaoId;
        this.descricao = descricao;
    }

    public Turma() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public Long getInstituicaoId() {
        return instituicaoId;
    }

    public void setInstituicaoId(Long instituicaoId) {
        this.instituicaoId = instituicaoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
