package br.com.wilderossi.blupresence.api;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class AlunosApi extends BaseApi<List<AlunoVO>>{

    private static final String RELATIVE_URL = "/students";
    private static final String URL_FORMAT = "%s/%s";
    private final String commaSeparatedAlunosId;

    public AlunosApi(String baseUrl, String commaSeparatedAlunosId) {
        super(baseUrl);
        this.commaSeparatedAlunosId = commaSeparatedAlunosId;
    }

    @Override
    protected String getRelativeUrl() {
        return String.format(URL_FORMAT, RELATIVE_URL, commaSeparatedAlunosId);
    }

    @Override
    protected List<AlunoVO> doInBackground(Void... params) {
        try {
            AlunoVO[] alunos = rest.getForObject(getUrl(), AlunoVO[].class);
            return Arrays.asList(alunos);
        } catch (Exception ex) {
            Log.e("AlunosApi", ex.getMessage(), ex);
        }
        return null;
    }
}
