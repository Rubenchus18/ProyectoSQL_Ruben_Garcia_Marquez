package com.example.proyectoandroid_luis_ruben;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;



public class Puntos_Totales extends AppCompatActivity {
    public ListView listViewPuntos;
    public SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_puntos_totales);

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
        Cursor cursor = dbHelper.obtenerPuntosTotales();
        if (cursor != null && cursor.getCount() > 0) {
            String[] fromColumns = {
                    EstructuraBBDD.PuntosTotales.COLUMN_NAME_PILOTO,
                    EstructuraBBDD.PuntosTotales.COLUMN_NAME_COCHE,
                    "total_puntos"
            };
            int[] toViews = {
                    R.id.textViewPiloto,
                    R.id.textViewCoche,
                    R.id.textViewPuntos
            };
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    R.layout.item_puntos_totales,
                    cursor,
                    fromColumns,
                    toViews,
                    0
            );
            listViewPuntos.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No hay todavía resultados", Toast.LENGTH_SHORT).show();
        }
    }
}