package br.com.wilderossi.blupresence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChamadaListActivity extends BaseActivity {

    private String url;
    private Long idTurma;
    private String nomeTurma;

    public static final String TURMA_PARAM = "turma";
    public static final String NOME_TURMA_PARAM = "nomeTurma";

    @Override
    public int getActivity() {
        return R.layout.chamada_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getStringExtra(savedInstanceState, ChamadaTurmaListActivity.URL_PARAM);
        idTurma = getLongExtra(savedInstanceState, ChamadaTurmaListActivity.TURMA_PARAM);
        nomeTurma = getStringExtra(savedInstanceState, ChamadaTurmaListActivity.NOME_TURMA_PARAM);

    }

    public void onClickNovaChamada(View view){
        redirectTo(ChamadaFormActivity.class);
    }

    @Override
    protected Intent setParameters(Intent intent) {
        intent.putExtra(TURMA_PARAM, idTurma);
        intent.putExtra(NOME_TURMA_PARAM, nomeTurma);
        return super.setParameters(intent);
    }
}
