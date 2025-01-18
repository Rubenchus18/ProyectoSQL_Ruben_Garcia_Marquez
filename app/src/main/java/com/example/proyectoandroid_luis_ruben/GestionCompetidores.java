package com.example.proyectoandroid_luis_ruben;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Random;

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
    String informacionPiloto;
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

        // Inicializar SQLiteHelper
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
        // Inicializar el adaptador antes de cargar los pilotos
        pilotosArrayAdapter = new ArrayAdapter<>(this, R.layout.itemcopa, R.id.nombreCopa, listaPilotos);
        listViewPilotos.setAdapter(pilotosArrayAdapter);

        // Cargar pilotos desde la base de datos
        cargarPilotos();

        // Botón de añadir acción clic listener
        Button buttonInsertar = findViewById(R.id.buttonAgregar);
        buttonInsertar.setOnClickListener(v -> insertarpilotos());

        // Botón de eliminar acción clic listener
        eliminar = findViewById(R.id.imageVieweliminar);
        eliminar.setOnClickListener(view -> eliminarPiloto());

        // Obtener los valores del item que seleccionemos
        informmacionPiloto();

        // Botón de editar acción clic listener
        editar = findViewById(R.id.fotoEditar);
        editar.setVisibility(View.INVISIBLE);
        editar.setOnClickListener(view -> editarPiloto());

        // Botón de retroceder ir a la actividad anterior
        retrocedemos = findViewById(R.id .imageView7);
        retrocedemos.setOnClickListener(v -> {
            Intent i = new Intent(GestionCompetidores.this, CircuitosDisponibles.class);
            startActivity(i);
        });

        // Botón para ver resultados
        buttonVerResultados = findViewById(R.id.buttonverresultado);
        buttonVerResultados.setOnClickListener(v -> {
            Intent intent = new Intent(GestionCompetidores.this, Mostrar_resultado_carrera.class);
            startActivity(intent);
        });
    }

    // Funcionalidad añadir piloto a la carrera
    private void insertarpilotos() {
        String nombrePiloto = nombrepiloto.getText().toString().trim();
        String nombreCoche = nombrecoche.getText().toString().trim();
        if (!nombrePiloto.isEmpty() && !nombreCoche.isEmpty()) {
            Piloto piloto = new Piloto(nombrePiloto, nombreCoche);
            if (!dbHelper.pilotoExiste(nombrePiloto)) {
                dbHelper.insertarPiloto(piloto); // Insertar en la base de datos
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

    // Funcionalidad eliminar piloto de la carrera
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

    // Funcionalidad obtener datos del piloto
    public void informmacionPiloto() {
        listViewPilotos.setOnItemClickListener((parent, view, position, id) -> {
            informacionPiloto = (String) parent.getItemAtPosition(position).toString().trim();
            imprimirInformacion.setText(informacionPiloto);
            editarCoche.setVisibility(View.VISIBLE);
            editarPiloto.setVisibility(View.VISIBLE);
            editar.setVisibility(View.VISIBLE);
        });
    }

    // Funcionalidad editar piloto de la carrera
    public void editarPiloto() {
        String coche = editarCoche.getText().toString().trim();
        String piloto = editarPiloto.getText().toString().trim();
        if (!coche.isEmpty() || !piloto.isEmpty()) {
            boolean encontrado = false;
            for (Piloto p : listaPilotos) {
                if (p.getNombrepiloto().equalsIgnoreCase(informacionPiloto) || p.getCoche().equalsIgnoreCase(informacionPiloto)) {
                    if (!piloto.isEmpty()) {
                        p.setNombrepiloto(piloto);
                    }
                    if (!coche.isEmpty()) {
                        p.setCoche(coche);
                    }
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {
                dbHelper.editarPiloto(informacionPiloto, piloto, coche); // Actualizar en la base de datos
                pilotosArrayAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Piloto editado", Toast.LENGTH_SHORT).show();
                imprimirInformacion.setText("");
                editarCoche.setVisibility(View.INVISIBLE);
                editarPiloto.setVisibility(View.INVISIBLE);
                editar.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(this, "Piloto no encontrado", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor, completa al menos un campo.", Toast.LENGTH_SHORT).show();
        }
    }

    // Cargar pilotos desde la base de datos
    private void cargarPilotos() {
        listaPilotos.clear();
        ArrayList<Piloto> pilotosDesdeDB = dbHelper.obtenerPilotos(); // Obtener pilotos de la base de datos
        if (pilotosDesdeDB != null && !pilotosDesdeDB.isEmpty()) {
            listaPilotos.addAll(pilotosDesdeDB); // Agregar los pilotos a la lista
        } else {
            Toast.makeText (this, "No se encontraron pilotos en la base de datos.", Toast.LENGTH_SHORT).show();
        }
        pilotosArrayAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
    }
}