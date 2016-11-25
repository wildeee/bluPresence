package br.com.wilderossi.blupresence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.wilderossi.blupresence.components.SubtitledArrayAdapter;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;
import br.com.wilderossi.blupresence.transaction.Chamada;
import br.com.wilderossi.blupresence.transaction.services.ChamadaService;

public class ChamadaListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private String url;
    private Long idTurma;
    private String nomeTurma;

    private ListView listViewChamada;

    private Chamada chamadaClicada;

    public static final String TURMA_PARAM = "turma";
    public static final String NOME_TURMA_PARAM = "nomeTurma";
    public static final String CHAMADA_CLICADA = "chamadaClicada";

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

        listViewChamada = (ListView) findViewById(R.id.listViewChamadas);
        listViewChamada.setOnItemClickListener(this);
        carregaChamadas();
    }

    public void onClickNovaChamada(View view){
        chamadaClicada = null;
        redirectTo(ChamadaFormActivity.class);
    }

    @Override
    protected Intent setParameters(Intent intent) {
        SingletonHelper.chamadaListActivity = this;
        intent.putExtra(TURMA_PARAM, idTurma);
        intent.putExtra(NOME_TURMA_PARAM, nomeTurma);
        intent.putExtra(CHAMADA_CLICADA, chamadaClicada != null ? chamadaClicada.getId() : -1L);
        return super.setParameters(intent);
    }

    public void carregaChamadas(){
        ChamadaService chamadaService = new ChamadaService(this);
        List<Chamada> chamadasTurma = chamadaService.findByTurma(idTurma);
        listViewChamada.setAdapter(new SubtitledArrayAdapter(
                this,
                R.layout.subtitled_listadapter_layout,
                chamadasTurma
        ));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        chamadaClicada = (Chamada) listViewChamada.getItemAtPosition(position);
        redirectTo(ChamadaFormActivity.class);
    }
}
