package com.example.proyectoandroid_luis_ruben;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CircuitosDisponibles extends AppCompatActivity {

    ArrayAdapter<String> adaptadorListaCopa;
    ListView lista;
    ImageView atras;
    ArrayList<String> circuitos = new ArrayList<String>();
    String copaElegida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_circuitos_disponibles);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // RECUPERAMOS EL ARRAYLIST DE LA ANTERIOR ACTIVIDAD
        circuitos = getIntent().getStringArrayListExtra("lista");

        // IR A LA ACTIVIDAD ANTERIOR
        atras = findViewById(R.id.actividadAnterior);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moverActividad();
            }
        });

        // ASIGNACION DE LISTVIEW
        asignacionList();

        // OBTENEMOS LOS VALORES DE LA COPA QUE SELECCIONEMOS
        valoresCopa();
    }

    public void moverActividad() {
        Intent i = new Intent(this, Informacion.class);
        startActivity(i);
    }

    public void asignacionList() {
        lista = findViewById(R.id.listaOps);
        adaptadorListaCopa = new ArrayAdapter<>(this, R.layout.itemcopa, R.id.nombreCopa, circuitos);
        lista.setAdapter(adaptadorListaCopa);
    }

    public void valoresCopa() {
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                copaElegida = (String) parent.getItemAtPosition(position).toString().trim();
                siguienteActividad();
            }
        });
    }

    public void siguienteActividad() {
        Intent j = new Intent(this, GestionCompetidores.class);
        j.putExtra("circuito", copaElegida);
        startActivity(j);
    }
}