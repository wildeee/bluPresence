package br.com.wilderossi.blupresence.components;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;

import br.com.wilderossi.blupresence.navigation.SingletonHelper;

public class BluetoothServer implements Runnable {

    private final BluetoothServerSocket serverSocket;

    public BluetoothServer(BluetoothAdapter mBluetoothAdapter) throws IOException {
        serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("bluPresence", SingletonHelper.APP_UUID);
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
                new Thread(new BluetoothRequestHandler(socket)).start();
            }
        }
    }

    public void cancel() {
        try {
            serverSocket.close();
        } catch (IOException e) { }
    }
}
