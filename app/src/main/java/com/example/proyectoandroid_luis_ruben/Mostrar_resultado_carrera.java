package com.example.proyectoandroid_luis_ruben;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class Mostrar_resultado_carrera extends AppCompatActivity {

    public ListView listViewResultados;
    public SQLiteHelper dbHelper;
    public ImageView retrocedemos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mostrar_resultado_carrera);

        listViewResultados = findViewById(R.id.listviewmostrarresultado);
        dbHelper = new SQLiteHelper(this);

        mostrarResultados();

        retrocedemos = findViewById(R.id.imageView7);
        retrocedemos.setOnClickListener(v -> {
            Intent intent = new Intent(Mostrar_resultado_carrera.this, GestionCompetidores.class);
            startActivity(intent);
        });
    }

    public void mostrarResultados() {
        Cursor cursor = dbHelper.obtenerPilotos();
        if (cursor != null && cursor.getCount() > 0) {
            String[] resultados = new String[cursor.getCount()];
            ArrayList<Integer> posiciones = new ArrayList<>();
            for (int i = 0; i < cursor.getCount(); i++) {
                posiciones.add(i);
            }
            Collections.shuffle(posiciones);
            for (int i = 0; i < posiciones.size(); i++) {
                cursor.moveToPosition(posiciones.get(i));
                @SuppressLint("Range") String nombrePiloto = cursor.getString(cursor.getColumnIndex(EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE));
                @SuppressLint("Range") String coche = cursor.getString(cursor.getColumnIndex(EstructuraBBDD.Piloto.COLUMN_NAME_COCHE));


                String resultado = "Posición: " + (i + 1) + " - Nombre: " + nombrePiloto + " - Coche: " + coche;
                resultados[i] = resultado;

                int puntos = 0;
                if (i == 0) {
                    puntos = 3; // 1er lugar
                } else if (i == 1) {
                    puntos = 2; // 2do lugar
                } else if (i == 2) {
                    puntos = 1; // 3er lugar
                }

                dbHelper.insertarPuntosTotales(nombrePiloto, coche, puntos);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultados);
            listViewResultados.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No se encontraron pilotos en la base de datos.", Toast.LENGTH_SHORT).show();
        }
        if (cursor != null) {
            cursor.close();
        }
    }
}