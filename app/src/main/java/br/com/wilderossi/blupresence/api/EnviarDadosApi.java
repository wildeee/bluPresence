package br.com.wilderossi.blupresence.api;

import java.util.Arrays;
import java.util.List;

import br.com.wilderossi.blupresence.vo.EnviarDadosErroVO;
import br.com.wilderossi.blupresence.vo.EnviarDadosVO;

public class EnviarDadosApi extends BaseApi<List<EnviarDadosErroVO>> {

    private static final String RELATIVE_URL = "/presenceControl";
    private final List<EnviarDadosVO> dados;

    public EnviarDadosApi(String baseUrl, List<EnviarDadosVO> dados) {
        super(baseUrl);
        this.dados = dados;
    }

    @Override
    protected String getRelativeUrl() {
        return RELATIVE_URL;
    }

    @Override
    protected List<EnviarDadosErroVO> doInBackground(Void... params) {
        EnviarDadosErroVO[] result = rest.postForObject(getUrl(), dados, EnviarDadosErroVO[].class);
        return Arrays.asList(result);
    }
}
