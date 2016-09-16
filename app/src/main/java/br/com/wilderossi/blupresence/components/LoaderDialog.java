package br.com.wilderossi.blupresence.components;

import android.app.ProgressDialog;
import android.content.Context;

import br.com.wilderossi.blupresence.R;

public class LoaderDialog extends ProgressDialog {

    public LoaderDialog(Context context) {
        super(context);
        super.setIndeterminate(Boolean.TRUE);
        super.setCancelable(Boolean.FALSE);
        String loaderMessage = context.getString(R.string.carregando);
        super.setMessage(loaderMessage);
    }
}
