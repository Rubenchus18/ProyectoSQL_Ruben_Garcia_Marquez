package com.example.proyectoandroid_luis_ruben;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CircuitosDisponibles extends AppCompatActivity {

    public ListView lista;
    public SQLiteHelper dbHelper;
    public String copaElegida;
    public ImageView atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circuitos_disponibles);

        lista = findViewById(R.id.listaOps);
        dbHelper = new SQLiteHelper(this);

        cargarCopas();
        valoresCopa();
        atras = findViewById(R.id.actividadAnterior);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverActividad();
            }
        });
    }

    public void moverActividad() {
        Intent i = new Intent(this, Informacion.class);
        startActivity(i);
    }

    public void cargarCopas() {
        Cursor cursor = dbHelper.obtenerCopas();
        if (cursor != null && cursor.getCount() > 0) {
            String[] fromColumns = {
                    EstructuraBBDD.Copa.COLUMN_NAME_NOMBRE,
                    EstructuraBBDD.Copa.COLUMN_NAME_DISTANCIA
            };
            int[] toViews = {
                    R.id.textViewNombreCopa,
            };
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    R.layout.item_copa,
                    cursor,
                    fromColumns,
                    toViews,
                    0
            );
            lista.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No se encontraron copas en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }

    public void valoresCopa() {
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    String nombreCopa = cursor.getString(cursor.getColumnIndexOrThrow(EstructuraBBDD.Copa.COLUMN_NAME_NOMBRE));
                    copaElegida = nombreCopa;
                    siguienteActividad();
                } else {
                    Toast.makeText(CircuitosDisponibles.this, "Error al obtener los datos de la copa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void siguienteActividad() {
        Intent intent = new Intent(CircuitosDisponibles.this, GestionCompetidores.class);
        intent.putExtra("copaElegida", copaElegida);
        startActivity(intent);
    }
}