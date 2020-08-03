package com.example.myapplication;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.myapplication.SessionStore.msg;

public class Message extends Fragment {
    RecyclerView allMessages;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        allMessages = (RecyclerView) view.findViewById(R.id.recyclerView1);
        message();
        return view;
    }



  /*  @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(msg){
        @Override
        public boolean handleMessage(@NonNull android.os.Message message) {
            super.handleMessage(msg);
            switch(msg.what){
                case SUCCESS_CONNECT:
                    // DO something
                    ConnectedThread connectedThread = new ConnectedThread((BluetoothSocket)msg.obj);
                    Toast.makeText(getApplicationContext(), "CONNECT", Toast.LENGTH_SHORT).show();
                    String s = "successfully connected";
                    connectedThread.write(s.getBytes());
                    Log.i(tag, "connected");
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[])msg.obj;
                    String string = new String(readBuf);
                    Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
          //  Log.i(tag, "in handler");
            super.handleMessage(msg);
            switch(msg.what){
                case SUCCESS_CONNECT:
                    // DO something
                    ConnectedThread connectedThread = new ConnectedThread((BluetoothSocket)msg.obj);
                    Toast.makeText(getApplicationContext(), "CONNECT", Toast.LENGTH_SHORT).show();
                    String s = "successfully connected";
                    connectedThread.write(s.getBytes());
                    Log.i(tag, "connected");
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[])msg.obj;
                    String string = new String(readBuf);
                    Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    public void write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
        r.write(out);
    }

    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            //  byte[] send = message.getBytes();
            //   mChatService.write(send);

            String messagess="blabla";
            byte[] send = messagess.getBytes();
            write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            mOutEditText.setText(mOutStringBuffer);
        }
    }


    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.content_main);

        mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
        //   mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Send a message using content of the edit text widget


                TextView textView = (TextView) findViewById(R.id.edit_text_out);
                String message = textView.getText().toString();
                sendMessage(message);

            }
        });


        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    } */


    void message(){
        //ArrayList<String> msg= new ArrayList<>();
        //testing
       msg.add("hi");
        msg.add("hello");
        msg.add("how are you");
        msg.add("fine");
        //testing
        MessageAdapter adapter = new MessageAdapter(msg, getActivity());
        allMessages.setAdapter(adapter);
        allMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
    }




}