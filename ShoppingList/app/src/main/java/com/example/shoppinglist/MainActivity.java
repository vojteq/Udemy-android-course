package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;

import com.example.shoppinglist.data.DatabaseHandler;
import com.example.shoppinglist.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler databaseHandler;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private Button saveButton;
    private EditText productNameTextView;
    private EditText quantityTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(MainActivity.this);

        byPassActivity();

        //check if item was saved
        Log.d("MAIN", "COUNT: " + databaseHandler.getCount());
        List<Item> items = databaseHandler.getAllItems();
        for (Item item : items)
            Log.d("MAIN", "ITEM: " + item);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                createPopupDialog();
            }
        });
    }

    private void byPassActivity() {
        if (databaseHandler.getCount() > 0) {
            startActivity(new Intent(MainActivity.this, ListActivity.class));
            finish();
        }
    }

    private void createPopupDialog() {
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
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

        builder.setView(view);
        alertDialog = builder.create();     //creating dialog object
        alertDialog.show();
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

                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        }, 500);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}