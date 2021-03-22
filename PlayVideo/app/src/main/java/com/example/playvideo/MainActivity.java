package com.example.playvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.video);

        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.setKeepScreenOn(true);
//        surfaceView.setZOrderOnTop(true);

        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(400, 300);

        Button playButton = findViewById(R.id.buttonPlay);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
            }
        });

        Button pauseButton = findViewById(R.id.buttonPause);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        Button skipButton = findViewById(R.id.buttonSkip);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getDuration() / 2);
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}