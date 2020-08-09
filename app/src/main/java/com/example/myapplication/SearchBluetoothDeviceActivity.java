package com.example.myapplication;

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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchBluetoothDeviceActivity extends AppCompatActivity {

    private static final int REQUEST_ALL_PERMISSION_CODE = 101;
    BluetoothAdapter ba;
    BroadcastReceiver mReceiver;
    ProgressBar spinner;
    private List<BluetoothDevice> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_search_bluetooth);
        //getting the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setting the title
        toolbar.setTitle("Bluetooth Devices");
        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        ba = BluetoothAdapter.getDefaultAdapter();
        initReceiver();
        spinner = findViewById(R.id.progressBar1);
    }

    public void initReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                    //discovery starts, we can show progress dialog or perform other tasks
                    devices = new ArrayList<>();
                    spinner.setVisibility(View.VISIBLE);
                } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    //discovery finishes, dismiss progress dialog
                    //spinner.setVisibility(View.GONE);
                } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    //bluetooth device found
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    devices.add(device);
                }
            }
        };
    }

    private void discoverDevice() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
        ba.startDiscovery();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }

    private void checkPermission() {
        final List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        final String[] permissions = new String[permissionList.size()];
        for(int i = 0;i < permissions.length;i++) {
            permissions[i] = permissionList.get(i);
        }
        if(permissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_ALL_PERMISSION_CODE);
            return;
        }
        discoverDevice();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ALL_PERMISSION_CODE:
                final List<String> per = new ArrayList<>();
                int i = 0;
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                    } else {
                        per.add(permissions[i]);
                    }
                    i++;
                }
                if(per.size() == 0) {
                    discoverDevice();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mReceiver == null){
            return;
        }
        unregisterReceiver(mReceiver);
    }
}
