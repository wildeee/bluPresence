package br.com.wilderossi.blupresence;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.wilderossi.blupresence.components.SingletonHelper;
import br.com.wilderossi.blupresence.transaction.services.InstituicaoService;

public class InstituicaoListActivity extends BaseActivity {

    private InstituicaoService instituicaoService;

    @Override
    public int getActivity() {
        return R.layout.instituicao_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instituicaoService = new InstituicaoService(getBaseContext());
        carregaInstituicoes();
    }

    public void carregaInstituicoes(){
        ListView listagem = (ListView) findViewById(R.id.listagemInstituicoes);
        assert listagem != null;
        listagem.setAdapter(
                new ArrayAdapter<>(
                        InstituicaoListActivity.this,
                        android.R.layout.simple_list_item_1,
                        instituicaoService.buscar()
                )
        );
    }

    public void onClickNovaInstituicao(View view){
        redirectTo(InstituicaoFormActivity.class);
        SingletonHelper.instituicaoListActivity = this;
    }
}
