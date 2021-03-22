package com.example.quizv2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button showGuess;
    private EditText enterGuess;
    private final int REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showGuess = findViewById(R.id.button_guess);
        enterGuess = findViewById(R.id.guess_field);

        showGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = enterGuess.getText().toString().trim();
                if (!guess.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, ShowGuess.class);
                    intent.putExtra("guess", guess);
                    intent.putExtra("name", "Wojtek");
                    intent.putExtra("age", 21);
//                    startActivity(intent);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else
                    Toast.makeText(MainActivity.this, "enter something :(", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                assert data != null;
                String message = data.getStringExtra("message_back");
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    }
}