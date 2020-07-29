package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
 BluetoothAdapter ba;
 Button scan;
    BroadcastReceiver mReceiver;
    ArrayList<BluetoothDevice> devices = new ArrayList<>();
    ListView listView;
@Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    scan=(Button) findViewById(R.id.button);
    ba = BluetoothAdapter.getDefaultAdapter();
    listView =(ListView) findViewById(R.id.listView);
    final int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
    mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Toast.makeText(getApplicationContext(), "Discover starts ", Toast.LENGTH_LONG).show();
                //discovery starts, we can show progress dialog or perform other tasks
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Toast.makeText(getApplicationContext(), "Discover stops", Toast.LENGTH_LONG).show();
                //discovery finishes, dismis progress dialog
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found

                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(device);
                play();

            }
        }

        private void play() {
            ArrayList<String> deviceList = new ArrayList<>();


            if (devices.size() > 0) {
                for (BluetoothDevice currentDevice : devices) {
                    deviceList.add("Device Name: " + currentDevice.getName() + "\nDevice Address: " + currentDevice.getAddress());
                    listView.setAdapter(new ArrayAdapter<>(getApplication(),
                            android.R.layout.simple_list_item_1, deviceList));
                }
        }
        }

    };
    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    startActivityForResult(turnOn, 0);

    scan.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            IntentFilter filter = new IntentFilter();

            filter.addAction(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

            registerReceiver(mReceiver, filter);
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                ba.startDiscovery();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                ba.startDiscovery();

            }
        }
    });





}
    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);

        super.onDestroy();
    }
            }


