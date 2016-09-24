package br.com.wilderossi.blupresence.api;

import br.com.wilderossi.blupresence.vo.ApiVO;

public class ProfessorVO extends ApiVO {

    private String id;
    private String login;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
