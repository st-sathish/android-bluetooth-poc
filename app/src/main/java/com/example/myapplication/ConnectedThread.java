package com.example.myapplication;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class ConnectedThread extends Thread {
    private final BluetoothSocket bluetoothSocket;
    private  final InputStream inputStream;
    private  final OutputStream outputStream;
    public ConnectedThread(BluetoothSocket bluetoothSocket) {
        this.bluetoothSocket = bluetoothSocket;
        InputStream tmp = null;
        OutputStream tmp1 = null;
        try {
            tmp = bluetoothSocket.getInputStream();
            tmp1 =bluetoothSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStream = tmp;
        outputStream = tmp1;
    }
    public void run(){
        byte[] buffer = new byte[1024];  // buffer store for the stream

        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            // Read from the InputStream
            try {
                bytes = inputStream.read(buffer);
                final String incomingMessage = new String(buffer, 0, bytes);
               // Log.d(TAG, "InputStream: " + incomingMessage);
                SessionStore.msg.add(incomingMessage);

             /*   runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                       // view_data.setText(incomingMessage);
                        SessionStore.msg.add(incomingMessage);
                    }
                });  */


            } catch (IOException e) {
             //   Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage() );
                break;
            }
        }
    }


    public void write(byte[] bytes) {
        String text = new String(bytes, Charset.defaultCharset());
       // Log.d(TAG, "write: Writing to outputstream: " + text);
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
           // Log.e(TAG, "write: Error writing to output stream. " + e.getMessage() );
        }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            bluetoothSocket.close();
        } catch (IOException e) { }
    }
}
