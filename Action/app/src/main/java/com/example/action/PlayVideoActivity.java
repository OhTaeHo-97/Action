package com.example.action;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class PlayVideoActivity extends AppCompatActivity {

    String videoPath;

    VideoView video;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        Intent intent = getIntent();
        if(intent!=null){
            videoPath = intent.getStringExtra("video_path");
        }

        video = findViewById(R.id.video);

        MediaController mc = new MediaController(this);
        video.setMediaController(mc);
        video.setVideoPath(videoPath);

        video.requestFocus();
        video.start();

        // Back Button
        backBtn = (Button)findViewById(R.id.play_video_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });


    }
}