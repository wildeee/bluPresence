package br.com.wilderossi.blupresence.components;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class BluetoothServer implements Runnable {

    private final BluetoothServerSocket serverSocket;

    public BluetoothServer(BluetoothAdapter mBluetoothAdapter) throws IOException {
        serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("bluPresence", UUID.randomUUID());
    }

    @Override
    public void run() {
        BluetoothSocket socket = null;
        while (Boolean.TRUE) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                break;
            }
            if (socket != null) {
                new Thread(new BluetoothRequestHandler(socket)).run();
            }
        }
    }

    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) { }
    }
}
