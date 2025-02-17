package com.example.proyectoandroid_luis_ruben;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class GrabacionVoz extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String fileName;

    private Button buttonIniciarGrabacion;
    private Button buttonDetenerGrabacion;
    private Button buttonEscucharGrabacion;
    private ImageView actividadAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabacion_voz);

        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/grabacion.3gp";

        buttonIniciarGrabacion = findViewById(R.id.buttonIniciarGrabacion);
        buttonDetenerGrabacion = findViewById(R.id.buttonDetenerGrabacion);
        buttonEscucharGrabacion = findViewById(R.id.buttonEscucharGrabacion);
        actividadAnterior = findViewById(R.id.actividadAnterior);

        // Verificar permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_MEDIA_AUDIO
            }, 0);
        }

        buttonIniciarGrabacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarGrabacion();
            }
        });

        buttonDetenerGrabacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detenerGrabacion();
            }
        });

        buttonEscucharGrabacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escucharGrabacion();
            }
        });

        actividadAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void iniciarGrabacion() {
        if (mediaRecorder == null) {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(fileName);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(this, "Grabación iniciada", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al iniciar la grabación", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ya se está grabando", Toast.LENGTH_SHORT).show();
        }
    }

    public void detenerGrabacion() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            Toast.makeText(this, "Grabación detenida", Toast.LENGTH_SHORT).show();

            escucharGrabacion();
        } else {
            Toast.makeText(this, "No hay grabación en curso", Toast.LENGTH_SHORT).show();
        }
    }

    public void escucharGrabacion() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(fileName);
                mediaPlayer.prepare();
                mediaPlayer.start();
                Toast.makeText(this, "Reproduciendo grabación", Toast.LENGTH_SHORT).show();


                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                        mediaPlayer = null;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al reproducir la grabación", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ya se está reproduciendo", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0) {
                boolean audioPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean writePermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (audioPermissionGranted && writePermissionGranted) {
                    Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permisos denegados. La aplicación no funcionará correctamente.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}