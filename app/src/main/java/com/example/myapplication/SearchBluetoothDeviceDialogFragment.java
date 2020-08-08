package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

public class SearchBluetoothDeviceDialogFragment extends DialogFragment {

    public static SearchBluetoothDeviceDialogFragment newInstance() {
        SearchBluetoothDeviceDialogFragment f = new SearchBluetoothDeviceDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_device_dialog_fragment, container, false);
        return v;
    }
}
