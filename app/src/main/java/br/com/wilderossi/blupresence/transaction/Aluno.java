package br.com.wilderossi.blupresence.transaction;

public class Aluno {

    private Long id;
    private String serverId;
    private Long turmaId;
    private String nome;

    public Aluno(Long id, String serverId, Long turmaId, String nome) {
        this.id = id;
        this.serverId = serverId;
        this.turmaId = turmaId;
        this.nome = nome;
    }

    public Aluno() { }

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

    public Long getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Long turmaId) {
        this.turmaId = turmaId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
