package com.example.myapplication;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeviceAdapter extends
        RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private ArrayList<Devices> devices;
    Context context;

    DeviceAdapter(ArrayList<Devices> deviceList, Context context){
        this.context = context;
        devices = deviceList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View deviceView = inflater.inflate(R.layout.device_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(deviceView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       Devices device = devices.get(position);
        RadioButton deviceRadio = holder.deviceName;
        deviceRadio.setText(device.deviceName);
    }


    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public RadioButton deviceName;
        final BluetoothDevice[] targetDevice = new BluetoothDevice[1];

        public ViewHolder(View itemView) {

            super(itemView);

            deviceName = (RadioButton) itemView.findViewById(R.id.device);
            deviceName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (Devices device : devices) {
                        if(deviceName.getText().equals(device.deviceName)) {
                            targetDevice[0] = device.device;
                            break;
                        }
                    }
                    if(targetDevice[0] !=null) {
                        if (targetDevice[0].getBondState() != BluetoothDevice.BOND_BONDED) {
                            pairDialogBox();
                        }
                        else {
                            connectDialogBox();
                        }
                    }


                }
            });


        }
        public void pairDialogBox(){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final boolean[] n = new boolean[1];
            EditText input = new EditText(context);

            builder.setView(input);
            builder.setTitle("Pair!");
            builder.setCancelable(false);
            builder.setPositiveButton("Pair", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                           targetDevice[0].createBond();
                           connectDialogBox();
                    }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deviceName.setChecked(false);
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        public void connectDialogBox(){
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            final boolean[] n = new boolean[1];
            EditText input = new EditText(context);

            builder.setView(input);
            builder.setTitle("Connect!");
            builder.setCancelable(false);
            builder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(DialogInterface dialog, int which) {

                            ConnectThread conn = new ConnectThread(targetDevice[0]);
                            conn.run();
                            Intent i = new Intent(context,MainActivity2.class);
                            context.startActivity(i);
                    try {
                        conn.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deviceName.setChecked(false);
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }
}