package br.com.wilderossi.blupresence;

import android.view.View;
import android.widget.ListView;

import br.com.wilderossi.blupresence.navigation.SingletonHelper;

public class InstituicaoListActivity extends InstituicaoListBaseActivity {

    @Override
    public int getActivity() {
        return R.layout.instituicao_list;
    }

    @Override
    protected ListView getListagem() {
        return (ListView) findViewById(R.id.listagemInstituicoes);
    }

    public void onClickNovaInstituicao(View view){
        redirectTo(InstituicaoFormActivity.class);
        SingletonHelper.instituicaoListActivity = this;
    }
}
