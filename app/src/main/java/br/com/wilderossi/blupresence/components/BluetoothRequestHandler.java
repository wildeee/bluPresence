package br.com.wilderossi.blupresence.components;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothRequestHandler implements Runnable {

    private final BluetoothSocket socket;

    public BluetoothRequestHandler(BluetoothSocket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            handleRequest(input, output);
        } catch (IOException e) {
            Log.e("BluetoothRequestHandler", e.getMessage());
        }
    }

    private void handleRequest(InputStream input, OutputStream output) {
        Log.v("Sucesso!", input.toString());
    }
}
