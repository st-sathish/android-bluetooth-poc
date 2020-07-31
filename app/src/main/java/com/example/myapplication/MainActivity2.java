package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView allMessages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        allMessages = (RecyclerView) findViewById(R.id.recyclerView1);
        message();

    }
    void message(){
        ArrayList<String> msg= new ArrayList<>();
        //testing
        msg.add("hi");
        msg.add("hello");
        msg.add("how are you");
        msg.add("fine");
        //testing
        MessageAdapter adapter = new MessageAdapter(msg, MainActivity2.this);
        allMessages.setAdapter(adapter);
        allMessages.setLayoutManager(new LinearLayoutManager(MainActivity2.this));
    }
}