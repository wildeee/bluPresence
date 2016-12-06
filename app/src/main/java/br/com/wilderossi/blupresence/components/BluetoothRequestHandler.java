package br.com.wilderossi.blupresence.components;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import br.com.wilderossi.blupresence.ChamadaFormActivity;
import br.com.wilderossi.blupresence.navigation.SingletonHelper;

public class BluetoothRequestHandler implements Runnable {

    private final BluetoothSocket socket;
    private final ChamadaFormActivity chamadaFormActivity;

    private final String REQUEST_URL = "REQUEST_URL";
    private final String AUTH_ERROR  = "AUTH ERROR";

    public BluetoothRequestHandler(BluetoothSocket socket, ChamadaFormActivity chamadaFormActivity){
        this.socket = socket;
        this.chamadaFormActivity = chamadaFormActivity;
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

    private void handleRequest(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        input.read(buffer);
        String request = new String(getFilledBuffer(buffer));

        switch (request){
            case REQUEST_URL:
                output.write(SingletonHelper.URL_INSTITUICAO.getBytes());
                buffer = new byte[1024];
                input.read(buffer);
                String alunoServerId = new String(getFilledBuffer(buffer));
                if (AUTH_ERROR.equals(alunoServerId)){
                    return;
                }
                registraPresenca(alunoServerId);
                output.write("Sucesso!".getBytes());
                break;
        }
    }

    private void registraPresenca(String alunoServerId) {
        chamadaFormActivity.insereAluno(alunoServerId);
    }

    private byte[] getFilledBuffer(byte[] buffer) {
        int size = 0;
        for (byte b : buffer){
            if (b == 0){
                break;
            }
            size++;
        }
        byte[] ret = new byte[size];
        for (int i = 0; i < size; i++){
            ret[i] = buffer[i];
        }
        return ret;
    }
}
