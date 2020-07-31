package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends
        RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private ArrayList<String> msg;
    Context context;
    MessageAdapter(ArrayList<String> msg, Context context){
        this.msg = msg;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View messageView = inflater.inflate(R.layout.message, parent, false);

        MessageAdapter.ViewHolder viewHolder = new MessageAdapter.ViewHolder(messageView);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


    }


    @Override
    public int getItemCount() {
        return msg.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        public ViewHolder(View messageView) {
            super(messageView);
            message = (TextView) messageView.findViewById(R.id.messages);
        }
    }
}
