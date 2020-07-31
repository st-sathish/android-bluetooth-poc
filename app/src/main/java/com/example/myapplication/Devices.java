package com.example.myapplication;

import android.bluetooth.BluetoothDevice;

public class Devices {
    String deviceName;
    BluetoothDevice device;
    Devices(String name, BluetoothDevice device){
        deviceName = name;
        this.device = device;
    }
}
