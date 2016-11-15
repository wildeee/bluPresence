package br.com.wilderossi.blupresence.transaction;

public class Instituicao {

    private Integer id;
    private String nome;
    private String url;
    private String idProfessor;

    public Instituicao(Integer id, String nome, String cidade, String idProfessor) {
        this.id = id;
        this.nome = nome;
        this.url = cidade;
        this.idProfessor = idProfessor;
    }

    public Instituicao(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        this.idProfessor = idProfessor;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}