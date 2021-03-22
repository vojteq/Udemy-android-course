package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.database.util.Util;

public class ContactInfoActivity extends AppCompatActivity {
    private TextView name;
    private TextView phone;
    private TextView timeOfClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        name = findViewById(R.id.contact_info_name);
        phone = findViewById(R.id.contact_info_phone);
        timeOfClick = findViewById(R.id.contact_info_time);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra(Util.KEY_NAME));
        phone.setText(intent.getStringExtra(Util.KEY_PHONE_NUMBER));
        timeOfClick.setText(intent.getStringExtra(Util.TIME));
    }
}