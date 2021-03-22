package com.example.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shoppinglist.adapter.RecyclerViewAdapter;
import com.example.shoppinglist.data.DatabaseHandler;
import com.example.shoppinglist.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> itemList;
    private DatabaseHandler databaseHandler;
    private FloatingActionButton floatingActionButton;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private TextView productNameTextView;
    private TextView quantityTextView;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.fab);

        databaseHandler = new DatabaseHandler(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        itemList = new ArrayList<>();
        
        //get items from db
        itemList = databaseHandler.getAllItems();
        
        for (Item item : itemList)
            Log.d(TAG, "onCreate: " + item.getProductName());

        recyclerViewAdapter = new RecyclerViewAdapter(this, itemList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPopDialog();
            }
        });
    }

    private void createPopDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();

        productNameTextView = view.findViewById(R.id.product_name_item);
        quantityTextView = view.findViewById(R.id.quantity_item);
        saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!productNameTextView.getText().toString().isEmpty() &&
                        !quantityTextView.getText().toString().isEmpty())
                    saveItem(view);
                else
                    Snackbar.make(view, "Fill all fields", Snackbar.LENGTH_LONG).show();

                Log.d("count", "onClick: " + databaseHandler.getCount());
            }
        });
    }

    private void saveItem(View view) {
        databaseHandler.addItem(new Item(
                productNameTextView.getText().toString().trim(),
                Integer.parseInt(quantityTextView.getText().toString().trim())
        ));

        Snackbar.make(view, "Item saved", Snackbar.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();

                startActivity(new Intent(ListActivity.this, ListActivity.class));
                finish();
            }
        }, 500);
    }
}