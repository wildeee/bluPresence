package br.com.wilderossi.blupresence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.wilderossi.blupresence.components.SubtitledArrayAdapter;
import br.com.wilderossi.blupresence.transaction.services.TurmaService;
import br.com.wilderossi.blupresence.vo.TurmaInstituicaoVO;

public class ChamadaTurmaListActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private TurmaInstituicaoVO turmaInstituicaoVOclicado;

    public static final String URL_PARAM        = "url";
    public static final String TURMA_PARAM      = "turma";
    public static final String NOME_TURMA_PARAM = "nomeTurma";

    @Override
    public int getActivity() {
        return R.layout.chamada_turma_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TurmaService service = new TurmaService(getBaseContext());

        List<TurmaInstituicaoVO> turmasInstituicaoVO = service.buscarTurmasInstituicao();

        listView = (ListView) findViewById(R.id.listViewChamadaTurma);

        listView.setAdapter(new SubtitledArrayAdapter(
                this,
                R.layout.subtitled_listadapter_layout,
                turmasInstituicaoVO
        ));
        listView.setOnItemClickListener(this);
    }

    @Override
    protected Intent setParameters(Intent intent) {
        intent.putExtra(URL_PARAM, turmaInstituicaoVOclicado.getInstituicao().getUrl());
        intent.putExtra(TURMA_PARAM, turmaInstituicaoVOclicado.getTurma().getId());
        intent.putExtra(NOME_TURMA_PARAM, turmaInstituicaoVOclicado.getTurma().getDescricao());
        return super.setParameters(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        turmaInstituicaoVOclicado = (TurmaInstituicaoVO) listView.getItemAtPosition(position);
        redirectTo(ChamadaListActivity.class);
    }
}
