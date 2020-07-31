package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeviceAdapter extends
        RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private ArrayList<String> devices;
    Context context;

    DeviceAdapter(ArrayList<String> deviceList, Context context){
        this.context = context;
        devices = deviceList;
        // added for testing purpose
        devices.add("From this fake");
        devices.add("Redmi");
        devices.add("Oppo");
        devices.add("Vivo");
        devices.add("Samsung");
        devices.add("Nokia");
        devices.add("Asus");
        devices.add("Realme");
        devices.add("I phone");
        devices.add("I phone2");
        devices.add("I phone3");
        devices.add("I phone4");
        devices.add("I phone5");
        devices.add("I phone6");
        devices.add("I phone7");
        // added for testing purpose
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

        String device = devices.get(position);
        RadioButton deviceRadio = holder.deviceName;
        deviceRadio.setText(device);
    }


    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        public RadioButton deviceName;




        public ViewHolder(View itemView) {

            super(itemView);

            deviceName = (RadioButton) itemView.findViewById(R.id.device);
            deviceName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pairDialogBox();
                }
            });


        }
        public void pairDialogBox(){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Do you want to pair with "+deviceName.getText());
            builder.setTitle("Welcome!");
            builder.setCancelable(false);
            builder.setPositiveButton("Pair", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();
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