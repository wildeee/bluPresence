package br.com.wilderossi.blupresence.api;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class TurmaApi extends BaseApi<List<TurmaVO>> {

    private static final String URL_FORMAT = "%s/%s";
    private static final String RELATIVE_URL = "/classes/professor";
    private final String professorId;

    public TurmaApi(String baseUrl, String professorId) {
        super(baseUrl);
        this.professorId = professorId;
    }

    @Override
    protected String getRelativeUrl() {
        return String.format(URL_FORMAT, RELATIVE_URL, professorId);
    }

    @Override
    protected List<TurmaVO> doInBackground(Void... params) {
        try {
            TurmaVO[] turmas = rest.getForObject(getUrl(), TurmaVO[].class);
            return Arrays.asList(turmas);
        } catch (Exception ex) {
            Log.e("TurmaApi", ex.getMessage(), ex);
        }
        return null;
    }
}
