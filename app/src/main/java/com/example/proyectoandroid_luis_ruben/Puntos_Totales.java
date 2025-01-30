package com.example.proyectoandroid_luis_ruben;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Puntos_Totales extends AppCompatActivity {

    public ListView listViewPuntos;
    public SQLiteHelper dbHelper;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_puntos_totales);
        mediaPlayer = MediaPlayer.create(this, R.raw.musicafondo);
        mediaPlayer.setLooping(true); // Repetir la música
        mediaPlayer.start();
        listViewPuntos = findViewById(R.id.listviewmostrarfinal);
        dbHelper = new SQLiteHelper(this);

        ImageView imageView7 = findViewById(R.id.imageView7);
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mostrarPuntosTotales();
    }

    public void mostrarPuntosTotales() {
        ArrayList<PuntosTotales> puntosTotalesList = dbHelper.obtenerPuntosTotales();
        ArrayList<String> resultados = new ArrayList<>();

        if (puntosTotalesList.isEmpty()) {
            Toast.makeText(this, "No hay todavía resultados", Toast.LENGTH_SHORT).show();
        } else {
            for (PuntosTotales puntos : puntosTotalesList) {
                String resultado = "Piloto: " + puntos.getNombrePiloto() + " - Coche: " + puntos.getCoche() + " - Puntos: " + puntos.getPuntos();
                resultados.add(resultado);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultados);
        listViewPuntos.setAdapter(adapter);
    }
}