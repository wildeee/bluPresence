package br.com.wilderossi.blupresence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.wilderossi.blupresence.api.TurmaApi;
import br.com.wilderossi.blupresence.vo.TurmaVO;
import br.com.wilderossi.blupresence.components.LoaderDialog;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;

public class TurmaListActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static final String PARAM_URL_INSTITUICAO = "PARAM_URL";
    public static final String PARAM_ID_INSTITUICAO  = "idInstituicao";
    private ListView listagem;
    private String baseUrl;
    private Long idInstituicao;

    @Override
    public int getActivity() {
        return R.layout.turma_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LoaderDialog loader = new LoaderDialog(this);
        super.onCreate(savedInstanceState);
        baseUrl = getStringExtra(savedInstanceState, TurmaInstituicaoListActivity.PARAM_URL_INSTITUICAO);
        String idProfessor = getStringExtra(savedInstanceState, TurmaInstituicaoListActivity.PARAM_IDPROFESSOR_INSTITUICAO);
        idInstituicao = Long.valueOf(getIntExtra(savedInstanceState, TurmaInstituicaoListActivity.PARAM_ID_INSTITUICAO));
        listagem = (ListView) findViewById(R.id.listagemTurma);
        TurmaApi service = new TurmaApi(baseUrl, idProfessor){
            @Override
            protected void onPostExecute(List<TurmaVO> turmas) {
                loader.cancel();
                listagem.setAdapter(
                        new ArrayAdapter<>(
                                TurmaListActivity.this,
                                android.R.layout.simple_list_item_1,
                                turmas
                        )
                );
            }
        };

        loader.show();
        service.execute();
        listagem.setOnItemClickListener(this);
    }

    @Override
    protected Intent setParameters(Intent intent) {
        intent.putExtra(PARAM_URL_INSTITUICAO, baseUrl);
        intent.putExtra(PARAM_ID_INSTITUICAO, idInstituicao.intValue());
        return super.setParameters(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SingletonHelper.turmaVO = (TurmaVO) listagem.getItemAtPosition(position);
        redirectTo(TurmaFormActivity.class);
    }
}
