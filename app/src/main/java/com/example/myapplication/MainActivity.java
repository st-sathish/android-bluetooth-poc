package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
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
    RecyclerView listDevices;
    ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    listDevices = (RecyclerView) findViewById(R.id.recyclerView);
    scan=(Button) findViewById(R.id.button);
    ba = BluetoothAdapter.getDefaultAdapter();
    spinner = (ProgressBar)findViewById(R.id.progressBar1);

    final int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
    mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                spinner.setVisibility(View.VISIBLE);
                listDevices.setVisibility(View.GONE);

                //discovery starts, we can show progress dialog or perform other tasks
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                spinner.setVisibility(View.GONE);
                listDevices.setVisibility(View.VISIBLE);
                showList();
                //discovery finishes, dismis progress dialog
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found

                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devices.add(device);


            }
        }
    

        private void showList() {
            ArrayList<String> deviceList = new ArrayList<>();


           if (devices.size() > 0) {
                for (BluetoothDevice currentDevice : devices) {
                   deviceList.add(currentDevice.getName());
                }

                DeviceAdapter adapter = new DeviceAdapter(deviceList, MainActivity.this);
                listDevices.setAdapter(adapter);
                listDevices.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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


