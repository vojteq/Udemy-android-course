package com.example.playmediav4;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.playButton);
        seekBar = findViewById(R.id.seekBarId);

        mediaPlayer = new MediaPlayer();
//        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.ava_max_rumors);
        try {
            mediaPlayer.setDataSource("https://buildappswithpaulo.com/music/watch_me.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                float duration = mp.getDuration();
                Toast.makeText(MainActivity.this, String.valueOf(duration / 1000 / 60), Toast.LENGTH_LONG).show();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {

                Toast.makeText(MainActivity.this, "the mp3 is ready to play", Toast.LENGTH_LONG).show();

                seekBar.setMax(mediaPlayer.getDuration());

                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("PLAYING", "onClick: "  + mediaPlayer.isPlaying());
                        switchMusic(mp);
                    }
                });
            }
        });
        mediaPlayer.prepareAsync();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void switchMusic(MediaPlayer mp) {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.pause();
                playButton.setText(R.string.play_text);
            }
            else {
                mp.start();
                playButton.setText(R.string.pause_text);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}