package br.com.wilderossi.blupresence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.wilderossi.blupresence.components.SubtitledArrayAdapter;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;
import br.com.wilderossi.blupresence.transaction.Chamada;
import br.com.wilderossi.blupresence.transaction.services.ChamadaService;

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

        carregaChamadas();
    }

    public void onClickNovaChamada(View view){
        redirectTo(ChamadaFormActivity.class);
    }

    @Override
    protected Intent setParameters(Intent intent) {
        SingletonHelper.chamadaListActivity = this;
        intent.putExtra(TURMA_PARAM, idTurma);
        intent.putExtra(NOME_TURMA_PARAM, nomeTurma);
        return super.setParameters(intent);
    }

    public void carregaChamadas(){
        ListView listViewChamada = (ListView) findViewById(R.id.listViewChamadas);
        ChamadaService chamadaService = new ChamadaService(this);
        List<Chamada> chamadasTurma = chamadaService.findByTurma(idTurma);
        listViewChamada.setAdapter(new SubtitledArrayAdapter(
                this,
                R.layout.subtitled_listadapter_layout,
                chamadasTurma
        ));
    }
}
