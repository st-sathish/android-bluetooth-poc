package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    ArrayList<BluetoothDevice> devices ;
    ListView listView;
    RecyclerView listDevices;
    ProgressBar spinner;
    MenuItem mView;
    private static final int BLUETOOTH_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setting the title
        toolbar.setTitle("My Toolbar");
        //placing toolbar in place of actionbar
        setSupportActionBar(toolbar);
        listDevices = (RecyclerView) findViewById(R.id.recyclerView);
        scan=(Button) findViewById(R.id.button);
        ba = BluetoothAdapter.getDefaultAdapter();
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        enableBluetooth();
        //initReceiver();

    /*final int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
    mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                devices = new ArrayList<>();
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
            ArrayList<Devices> deviceList = new ArrayList<>();


           if (devices.size() > 0) {
                for (BluetoothDevice currentDevice : devices) {
                   deviceList.add(new Devices(currentDevice.getName(),currentDevice));
                }

                DeviceAdapter adapter = new DeviceAdapter(deviceList, MainActivity.this);
                listDevices.setAdapter(adapter);
                listDevices.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }
        }

    };*/

    /*scan.setOnClickListener(new View.OnClickListener() {
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
    });*/
    }

    public void initReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();
                if (action != null && action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                    final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                        switch (state) {
                            case BluetoothAdapter.STATE_OFF:
                                // Bluetooth has been turned off;
                                enableBluetooth();
                                break;
                            case BluetoothAdapter.STATE_TURNING_OFF:
                                // Bluetooth is turning off;
                                break;
                            case BluetoothAdapter.STATE_ON:
                                // Bluetooth is on
                                break;
                            case BluetoothAdapter.STATE_TURNING_ON:
                                // Bluetooth is turning on
                                break;
                        }
                }
            }
        };
        // Register for broadcasts on BluetoothAdapter state change
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
    }

    public void enableBluetooth() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            return;
        }
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, BLUETOOTH_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case BLUETOOTH_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    mView.setVisible(true);
                    Toast.makeText(this, "Bluetooth enabled success", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(this, "Bluetooth not enabled", Toast.LENGTH_LONG).show();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startSearchActivity();
                break;
        }
        return true;
    }

    private void startSearchActivity() {
        Intent intent = new Intent(MainActivity.this, SearchBluetoothDeviceActivity.class);
        startActivity(intent);
    }

    void showDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment newFragment = SearchBluetoothDeviceDialogFragment.newInstance();
        newFragment.setCancelable(false);
        newFragment.show(ft, "dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        mView  = menu.findItem(R.id.menu_search);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            mView.setVisible(true);
        }
        return true;
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


