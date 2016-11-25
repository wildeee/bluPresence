package br.com.wilderossi.blupresence.components;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    public static void makeToast(Context ctx, String message){
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(ctx, message, duration);
        toast.show();
    }

}
