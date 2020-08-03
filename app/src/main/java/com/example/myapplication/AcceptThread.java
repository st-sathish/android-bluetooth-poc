package com.example.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

public class AcceptThread extends Thread {
    private  final BluetoothServerSocket bluetoothServerSocket;
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private String secureType;
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a67");
    public static final String NAME_SECURE = "Secured";
    public static final String NAME_INSECURE = "In Secured";


    public AcceptThread(boolean secure) throws IOException {
        BluetoothServerSocket tmp = null;
        secureType = secure ? "Secure" : "Insecure";
        if (secure) {
          tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);
        } else {
           tmp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, MY_UUID_INSECURE);
        }
          bluetoothServerSocket = tmp;

    }
    public void run(){
      //  Log.d(TAG, "run: AcceptThread Running.");

        BluetoothSocket socket = null;

        try{
            // This is a blocking call and will only return on a
            // successful connection or an exception
          //  Log.d(TAG, "run: RFCOM server socket start.....");

            socket = bluetoothServerSocket.accept();

           // Log.d(TAG, "run: RFCOM server socket accepted connection.");

        }catch (IOException e){
          //  Log.e(TAG, "AcceptThread: IOException: " + e.getMessage() );
        }

        //talk about this is in the 3rd
        if(socket != null){
            ConnectedThread con = new ConnectedThread(socket);
            con.start();
        }

     //   Log.i(TAG, "END mAcceptThread ");
    }

    public void cancel() {
       // Log.d(TAG, "cancel: Canceling AcceptThread.");
        try {
            bluetoothServerSocket.close();
        } catch (IOException e) {
        //    Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage() );
        }
    }

}
