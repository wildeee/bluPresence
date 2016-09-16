package br.com.wilderossi.blupresence.api;

import android.util.Log;

import br.com.wilderossi.blupresence.components.LoaderDialog;
import br.com.wilderossi.blupresence.vo.TesteConexaoVO;

public class TesteConexaoApi extends BaseApi<TesteConexaoVO> {

    private static final String RELATIVE_URL = "/testConnection";

    public TesteConexaoApi(String baseUrl, LoaderDialog loader) {
        super(baseUrl);
    }

    @Override
    protected String getRelativeUrl() {
        return RELATIVE_URL;
    }

    @Override
    protected TesteConexaoVO doInBackground(Void... params) {
        try {
            return rest.getForObject(getUrl(), TesteConexaoVO.class);
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }
}
