package com.example.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.net.ConnectException;
import java.util.UUID;

public class ConnectThread extends Thread{
    private BluetoothDevice bluetoothDevice;
    private BluetoothSocket bluetoothSocket;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private static  final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public ConnectThread(BluetoothDevice device){
        BluetoothSocket tmp = null;
        bluetoothDevice = device;
        try {
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bluetoothSocket = tmp;
    }

    public void run(){
        bluetoothAdapter.cancelDiscovery();
        try {
        bluetoothSocket.connect();
        } catch (IOException e) {
            try{
                bluetoothSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        catch (Exception e) {
        }

        SessionStore.cn = new ConnectedThread(bluetoothSocket);
       SessionStore.cn.start();

    }
public  void cancel() throws IOException {
    bluetoothSocket.close();
}

}
