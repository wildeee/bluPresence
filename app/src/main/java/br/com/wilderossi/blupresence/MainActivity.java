package br.com.wilderossi.blupresence;

import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    public int getActivity() {
        return R.layout.activity_main;
    }

    public void onClickInstituicao(View view){
        redirectTo(InstituicaoListActivity.class);
    }

    public void onClickTurma(View view){
        redirectTo(TurmaInstituicaoListActivity.class);
    }
}
