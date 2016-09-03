package br.com.wilderossi.blupresence;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends BaseActivity {

    @Override
    public int getActivity() {
        return R.layout.activity_main;
    }

    public void onClickInstituicao(View view){
        redirectTo(InstituicaoListActivity.class);
    }
}
