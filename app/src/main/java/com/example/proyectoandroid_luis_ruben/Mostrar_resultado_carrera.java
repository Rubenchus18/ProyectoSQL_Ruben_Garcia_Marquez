package com.example.proyectoandroid_luis_ruben;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class Mostrar_resultado_carrera extends AppCompatActivity {

    private ListView listViewResultados;
    private SQLiteHelper dbHelper;
    private ArrayList<Piloto> listaPilotos;
    private ImageView retrocedemos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mostrar_resultado_carrera);

        listViewResultados = findViewById(R.id.listviewmostrarresultado);
        dbHelper = new SQLiteHelper(this);
        listaPilotos = new ArrayList<>();

        cargarPilotos();
        mostrarResultados();

        retrocedemos = findViewById(R.id.imageView7);
        retrocedemos.setOnClickListener(v -> {
            Intent intent = new Intent(Mostrar_resultado_carrera.this, GestionCompetidores.class);
            startActivity(intent);
        });
    }

    private void cargarPilotos() {
        ArrayList<Piloto> pilotosDesdeDB = dbHelper.obtenerPilotos();
        if (pilotosDesdeDB != null && !pilotosDesdeDB.isEmpty()) {
            listaPilotos.addAll(pilotosDesdeDB);
        } else {
            Toast.makeText(this, "No se encontraron pilotos en la base de datos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarResultados() {
        Collections.shuffle(listaPilotos);

        ArrayList<String> resultados = new ArrayList<>();
        for (int i = 0; i < listaPilotos.size(); i++) {
            Piloto piloto = listaPilotos.get(i);
            String resultado = "Posición: " + (i + 1) + " - Nombre: " + piloto.getNombrepiloto() + " - Coche: " + piloto.getCoche();
            resultados.add(resultado);

            // Sumar puntos según la posición
            int puntos = 0;
            if (i == 0) {
                puntos = 3; // 1er lugar
            } else if (i == 1) {
                puntos = 2; // 2do lugar
            } else if (i == 2) {
                puntos = 1; // 3er lugar
            }

            // Guardar puntos en la tabla PuntosTotales
            dbHelper.insertarPuntosTotales(piloto.getNombrepiloto(), piloto.getCoche(), puntos);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultados);
        listViewResultados.setAdapter(adapter);
    }
}