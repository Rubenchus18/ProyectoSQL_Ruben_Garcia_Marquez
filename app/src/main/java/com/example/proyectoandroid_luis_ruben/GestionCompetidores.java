package com.example.proyectoandroid_luis_ruben;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class GestionCompetidores extends AppCompatActivity {
    EditText nombrecoche;
    EditText nombrepiloto;
    EditText nombreeliminar;
    EditText nombreeditar;
    String datosCopa;
    ArrayAdapter<String> pilotosArrayAdapter;
    ArrayList<String> listaPilotos;
    TextView carreraElegida;
    ListView listViewPilotos;
    ImageView retocedemos;
    ImageView eliminar;
    ImageView editar;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestion_competidores);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        datosCopa = getIntent().getStringExtra("circuito");
        carreraElegida = findViewById(R.id.imprimirCarrera);
        carreraElegida.setText(datosCopa);

        nombrecoche = findViewById(R.id.editTextnombrecoche);
        nombrepiloto = findViewById(R.id.editTextnombrepiloto);
        nombreeliminar = findViewById(R.id.editTextnombreliminar);
        nombreeditar = findViewById(R.id.editTexteditarnombre); // Asegúrate de tener este EditText en tu XML
        listViewPilotos = findViewById(R.id.listviewpilotos);

        listaPilotos = new ArrayList<>();
        pilotosArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPilotos);
        listViewPilotos.setAdapter(pilotosArrayAdapter);

        Button buttonInsertar = findViewById(R.id.buttonAgregar);
        buttonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarpilotos();
            }
        });

        eliminar = findViewById(R.id.imageVieweliminar);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPiloto();
            }
        });

        editar = findViewById(R.id.imageVieweditar);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarPiloto();
            }
        });

        retocedemos = findViewById(R.id.imageView7);
        retocedemos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionCompetidores.this, CircuitosDisponibles.class);
                startActivity(i);
            }
        });
    }

    private void insertarpilotos() {
        String nombrePiloto = nombrepiloto.getText().toString();
        String nombreCoche = nombrecoche.getText().toString();

        if (!nombrePiloto.isEmpty() && !nombreCoche.isEmpty()) {
            String pilotoCompleto = "Piloto: " + nombrePiloto + ", Coche: " + nombreCoche;
            listaPilotos.add(pilotoCompleto);
            pilotosArrayAdapter.notifyDataSetChanged();
            nombrepiloto.setText("");
            nombrecoche.setText("");
        } else {
            Toast.makeText(this, "Por favor, completa ambos campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarPiloto() {
        String nombrePilotoAEliminar = nombreeliminar.getText().toString();

        if (!nombrePilotoAEliminar.isEmpty()) {
            boolean eliminado = false;
            for (int i = 0; i < listaPilotos.size(); i++) {
                if (listaPilotos.get(i).contains(nombrePilotoAEliminar)) {
                    listaPilotos.remove(i);
                    eliminado = true;
                    break;
                }
            }

            if (eliminado) {
                pilotosArrayAdapter.notifyDataSetChanged();
                nombreeliminar.setText("");
                Toast.makeText(this, "Piloto eliminado.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Piloto no encontrado.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor, ingresa el nombre del piloto a eliminar.", Toast.LENGTH_SHORT).show();
        }
    }

    public  void editarPiloto() {

    }


}