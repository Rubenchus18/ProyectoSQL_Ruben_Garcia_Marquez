package com.example.proyectoandroid_luis_ruben;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    TextView nombreeditar;
    EditText editarPiloto, editarCoche;
    String datosCopa;
    ArrayAdapter<Piloto> pilotosArrayAdapter;
    ArrayList<Piloto> listaPilotos;
    TextView carreraElegida;
    ListView listViewPilotos;
    ImageView retrocedemos;
    ImageView eliminar;
    ImageView editar;
    TextView imprimirInformacion;
    Piloto pilotoSeleccionado;
    SQLiteHelper dbHelper;
    Button buttonSimular;
    Button buttonVerResultados;

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
        dbHelper = new SQLiteHelper(this);

        datosCopa = getIntent().getStringExtra("circuito");
        carreraElegida = findViewById(R.id.imprimirCarrera);
        carreraElegida.setText(datosCopa);

        nombrecoche = findViewById(R.id.editTextnombrecoche);
        nombrepiloto = findViewById(R.id.editTextnombrepiloto);
        nombreeliminar = findViewById(R.id.editTextnombreliminar);
        listViewPilotos = findViewById(R.id.listviewmostrarresultado);
        imprimirInformacion = findViewById(R.id.infoPiloto);
        editarPiloto = findViewById(R.id.editarPiloto);
        editarCoche = findViewById(R.id.editarCoche);
        editarPiloto.setVisibility(View.INVISIBLE);
        editarCoche.setVisibility(View.INVISIBLE);

        listaPilotos = new ArrayList<>();
        pilotosArrayAdapter = new ArrayAdapter<>(this, R.layout.itemcopa, R.id.nombreCopa, listaPilotos);
        listViewPilotos.setAdapter(pilotosArrayAdapter);

        cargarPilotos();

        Button buttonInsertar = findViewById(R.id.buttonAgregar);
        buttonInsertar.setOnClickListener(v -> insertarpilotos());

        eliminar = findViewById(R.id.imageVieweliminar);
        eliminar.setOnClickListener(view -> eliminarPiloto());

        informmacionPiloto();

        editar = findViewById(R.id.fotoEditar);
        editar.setVisibility(View.INVISIBLE);
        editar.setOnClickListener(view -> editarPiloto());

        retrocedemos = findViewById(R.id.imageView7);
        retrocedemos.setOnClickListener(v -> {
            Intent i = new Intent(GestionCompetidores.this, CircuitosDisponibles.class);
            startActivity(i);
        });

        buttonVerResultados = findViewById(R.id.buttonverresultado);
        buttonVerResultados.setOnClickListener(v -> {
            Intent intent = new Intent(GestionCompetidores.this, Mostrar_resultado_carrera.class);
            startActivity(intent);
        });
    }

    private void insertarpilotos() {
        String nombrePiloto = nombrepiloto.getText().toString().trim();
        String nombreCoche = nombrecoche.getText().toString().trim();
        if (!nombrePiloto.isEmpty() && !nombreCoche.isEmpty()) {
            Piloto piloto = new Piloto(nombrePiloto, nombreCoche);
            if (!dbHelper.pilotoExiste(nombrePiloto)) {
                dbHelper.insertarPiloto(piloto);
                listaPilotos.add(piloto);
                pilotosArrayAdapter.notifyDataSetChanged();
                nombrepiloto.setText("");
                nombrecoche.setText("");
                Toast.makeText(this, "Piloto añadido correctamente.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Piloto ya existente.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor, completa ambos campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarPiloto() {
        String nombrePilotoAEliminar = nombreeliminar.getText().toString().trim();
        if (!nombrePilotoAEliminar.isEmpty()) {
            if (dbHelper.eliminarPiloto(nombrePilotoAEliminar)) {
                listaPilotos.removeIf(piloto -> piloto.getNombrepiloto().equalsIgnoreCase(nombrePilotoAEliminar));
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

    public void informmacionPiloto() {
        listViewPilotos.setOnItemClickListener((parent, view, position, id) -> {
            pilotoSeleccionado = (Piloto) parent.getItemAtPosition(position);
            imprimirInformacion.setText(pilotoSeleccionado.getNombrepiloto() + " - " + pilotoSeleccionado.getCoche()); // Mostrar nombre y coche
            editarCoche.setVisibility(View.VISIBLE);
            editarPiloto.setVisibility(View.VISIBLE);
            editar.setVisibility(View.VISIBLE);
        });
    }

    public void editarPiloto() {
        String coche = editarCoche.getText().toString().trim();
        String piloto = editarPiloto.getText().toString().trim();
        boolean encontrado = false;

        if (pilotoSeleccionado != null) {
            String cocheOriginal = pilotoSeleccionado.getCoche();
            String pilotoOriginal = pilotoSeleccionado.getNombrepiloto();

            Log.d("EditarPiloto", "Piloto encontrado: " + pilotoOriginal + ", Coche: " + cocheOriginal);
            Log.d("EditarPiloto", "Nuevo piloto: " + piloto + ", Nuevo coche: " + coche);

            if (!piloto.trim().isEmpty() && !piloto.trim().equalsIgnoreCase(pilotoOriginal.trim())) {
                pilotoSeleccionado.setNombrepiloto(piloto);
                encontrado = true;
                dbHelper.editarPiloto(pilotoOriginal, piloto, coche.isEmpty() ? cocheOriginal : coche);
                Toast.makeText(this, "Piloto editado", Toast.LENGTH_SHORT).show();
            }

            if (!coche.trim().isEmpty() && !coche.trim().equalsIgnoreCase(cocheOriginal.trim())) {
                pilotoSeleccionado.setCoche(coche);
                encontrado = true;
                dbHelper.editarPiloto(pilotoOriginal, piloto.isEmpty() ? pilotoOriginal : piloto, coche);
                Toast.makeText(this, "Coche editado", Toast.LENGTH_SHORT).show();
            }

            if (encontrado) {
                cargarPilotos();
            }
        } else {
            Toast.makeText(this, "No se ha seleccionado ningún piloto.", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarPilotos() {
        listaPilotos.clear();
        ArrayList<Piloto> pilotosDesdeDB = dbHelper.obtenerPilotos();
        if (pilotosDesdeDB != null && !pilotosDesdeDB.isEmpty()) {
            listaPilotos.addAll(pilotosDesdeDB);
        } else {
            Toast.makeText(this, "No se encontraron pilotos en la base de datos.", Toast.LENGTH_SHORT).show();
        }
        pilotosArrayAdapter.notifyDataSetChanged();
    }
}