package br.com.wilderossi.blupresence.api;

import br.com.wilderossi.blupresence.vo.LoginVO;

public class AuthenticationApi extends BaseApi<ProfessorVO> {

    private static final String RELATIVE_URL = "/authentication";
    private final LoginVO login;

    public AuthenticationApi(String baseUrl, LoginVO login) {
        super(baseUrl);
        this.login = login;
    }

    @Override
    protected String getRelativeUrl() {
        return RELATIVE_URL;
    }

    @Override
    protected ProfessorVO doInBackground(Void... params) {

        return rest.postForObject(getUrl(), login, ProfessorVO.class);

    }
}
