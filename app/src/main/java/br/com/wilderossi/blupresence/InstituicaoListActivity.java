package br.com.wilderossi.blupresence;

import android.view.View;

public class InstituicaoListActivity extends BaseActivity {

    @Override
    public int getActivity() {
        return R.layout.instituicao_list;
    }

    public void onClickNovaInstituicao(View view){
        redirectTo(InstituicaoFormActivity.class);
    }
}
