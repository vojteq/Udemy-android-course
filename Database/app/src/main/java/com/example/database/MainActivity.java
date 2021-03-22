package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.database.adapter.RecyclerViewAdapter;
import com.example.database.data.DatabaseHandler;
import com.example.database.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Contact> contactArrayList;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactArrayList = new ArrayList<>();
        DatabaseHandler databaseHandler = new DatabaseHandler(MainActivity.this);

        List<Contact> contacts = databaseHandler.getAllContacts();
        for (Contact contact : contacts) {
            Log.d("MainActivity", "onCreate: " +contact.getId() + " " + contact.getName() + " " + contact.getPhoneNumber());
            contactArrayList.add(contact);
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, contactArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

    }
}