package com.example.proyectoandroid_luis_ruben;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Videos extends AppCompatActivity {
    private ImageView imagenVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_videos);
        imagenVolver = findViewById(R.id.actividadAnterior);

        VideoView video2 = findViewById(R.id.videoView13);
        VideoView video3 = findViewById(R.id.videoView12);





        String videoPath2 = "android.resource://" + getPackageName() + "/" + R.raw.carrera2;
        Uri uri2 = Uri.parse(videoPath2);
        video2.setVideoURI(uri2);

        MediaController mediaController2 = new MediaController(this);
        video2.setMediaController(mediaController2);
        mediaController2.setAnchorView(video2);
        video2.requestFocus();

        String videoPath3 = "android.resource://" + getPackageName() + "/" + R.raw.carrera3;
        Uri uri3 = Uri.parse(videoPath3);
        video3.setVideoURI(uri3);

        MediaController mediaController3 = new MediaController(this);
        video3.setMediaController(mediaController3);
        mediaController2.setAnchorView(video3);
        video3.requestFocus();


        video2.start();
        video3.start();

        imagenVolver.setOnClickListener(v -> {
            Intent intent = new Intent(Videos.this, Informacion.class);
            startActivity(intent);
            finish();
        });

    }
}