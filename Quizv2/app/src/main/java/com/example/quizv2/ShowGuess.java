package com.example.quizv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShowGuess extends AppCompatActivity {

    TextView showGuessTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_guess);

        showGuessTextView = findViewById(R.id.received_text_view);

        showGuessTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.putExtra("message_back", "From Second Activity");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();

        String guess;
        if ((guess = bundle.getString("guess")) != null)
            showGuessTextView.setText(guess);
        else
            Toast.makeText(ShowGuess.this, "nullptr", Toast.LENGTH_SHORT).show();

        String name;
        int age;

        if ((name = bundle.getString("name")) != null && (age = bundle.getInt("age")) != 0)
            showGuessTextView.append("\ncreated by:\n" + name + " ("+ age + ")");
    }
}