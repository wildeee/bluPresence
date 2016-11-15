package br.com.wilderossi.blupresence;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.wilderossi.blupresence.api.TurmaApi;
import br.com.wilderossi.blupresence.api.TurmaVO;
import br.com.wilderossi.blupresence.components.LoaderDialog;

public class TurmaListActivity extends BaseActivity {

    private ListView listagem;

    @Override
    public int getActivity() {
        return R.layout.turma_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final LoaderDialog loader = new LoaderDialog(this);
        super.onCreate(savedInstanceState);
        String url = getStringExtra(savedInstanceState, TurmaInstituicaoListActivity.PARAM_URL_INSTITUICAO);
        String idProfessor = getStringExtra(savedInstanceState, TurmaInstituicaoListActivity.PARAM_IDPROFESSOR_INSTITUICAO);
        listagem = (ListView) findViewById(R.id.listagemTurma);
        TurmaApi service = new TurmaApi(url, idProfessor){
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
    }
}
