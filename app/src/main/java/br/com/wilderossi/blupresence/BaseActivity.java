package br.com.wilderossi.blupresence;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements Creatable {

    protected Intent setParameters(Intent intent){ return intent; }

    protected void redirectTo(Class<? extends BaseActivity> destiny) {
        Intent intent = new Intent(this, destiny);
        startActivity(setParameters(intent));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivity());
    }


}
