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

    protected String getStringExtra(Bundle savedInstanceState, String id) {
        String returnData;
        returnData = (savedInstanceState == null) ? null : (String) savedInstanceState
                .getSerializable(id);
        if (returnData == null) {
            Bundle extras = getIntent().getExtras();
            returnData = extras != null ? extras.getString(id) : null;
        }
        return returnData;
    }

    protected Integer getIntExtra(Bundle savedInstanceState, String id) {
        Integer returnData;
        returnData = (savedInstanceState == null) ? null : (Integer) savedInstanceState
                .getSerializable(id);
        if (returnData == null) {
            Bundle extras = getIntent().getExtras();
            returnData = extras != null ? extras.getInt(id) : null;
        }
        return returnData;
    }

    protected Long getLongExtra(Bundle savedInstanceState, String id) {
        Long returnData;
        returnData = (savedInstanceState == null) ? null : (Long) savedInstanceState
                .getSerializable(id);
        if (returnData == null) {
            Bundle extras = getIntent().getExtras();
            returnData = extras != null ? extras.getLong(id) : null;
        }
        return returnData;
    }


}
