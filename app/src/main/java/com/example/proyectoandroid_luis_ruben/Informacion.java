package com.example.proyectoandroid_luis_ruben;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Random;

public class Informacion extends AppCompatActivity {

    Toolbar toolbar;
    ListView listaCopas;
    ArrayList<Copa> copas = new ArrayList<>();
    ArrayAdapter<Copa> adapartorCopas;
    EditText añadircopa, eliminarCopaEditText, editarCopa;
    ImageView añadir, eliminar, editar;
    TextView edicion;
    SQLiteHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informacion);

        // MENU TOOLBAR INTEGRACION
        toolbar = findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);


        listaCopas = findViewById(R.id.listacopas);

        // Inicializamos la base de datos
        dbHelper = new SQLiteHelper(this);


        adapartorCopas = new ArrayAdapter<>(this, R.layout.itemcopa, R.id.nombreCopa, copas);
        listaCopas.setAdapter(adapartorCopas);

        // Cargar copas desde la base de datos
        cargarCopas();

        // AÑADIR
        añadircopa = findViewById(R.id.nuevaCopa);
        añadir = findViewById(R.id.newCupFoto);
        // ELIMINAR
        eliminarCopaEditText = findViewById(R.id.eliminarCopa);
        eliminar = findViewById(R.id.deleteCupFoto);
        // EDITAR
        editarCopa = findViewById(R.id.editarCopa);
        editar = findViewById(R.id.EditCupFoto);


        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadirCopa();
            }
        });

        // Configuramos el listener para el ImageView de eliminar
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarCopa();
            }
        });


        listaCopas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Copa copaSeleccionada = copas.get(position);
                edicion = findViewById(R.id.textoEditarCopa);
                edicion.setText(copaSeleccionada.getNombre());
                editar.setVisibility(View.VISIBLE);
            }
        });


        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarCopa();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Desconectarse) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.SobreNosotros1) {
            Intent j = new Intent(this, SobreNosotros.class);
            startActivity(j);
        } else if (item.getItemId() == R.id.pilotos) {
            Intent k = new Intent(this, CircuitosDisponibles.class);
            ArrayList<String> listaCopas = trasladarArrayList();
            k.putStringArrayListExtra("lista Copas", listaCopas);
            startActivity(k);
        }
        return super.onOptionsItemSelected(item);
    }

    private void añadirCopa() {
        String nombreCopa = añadircopa.getText().toString().trim();
        if (!nombreCopa.isEmpty() && !copaExiste(nombreCopa)) {
            Random random = new Random();
            int distanciaAleatoria = random.nextInt(901) + 100; // Genera un número aleatorio entre 100 y 1000
            Copa nuevaCopa = new Copa(nombreCopa, String.valueOf(distanciaAleatoria)); // Cambia "0" por la distancia aleatoria
            dbHelper.insertarCopa(nuevaCopa); // Insertar en la base de datos
            copas.add(nuevaCopa);
            adapartorCopas.notifyDataSetChanged();
            añadircopa.setText("");
            Toast.makeText(this, "Copa añadida", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "La copa ya existe o el nombre está vacío", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean copaExiste(String nombre) {
        for (Copa copa : copas) {
            if (copa.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    private void eliminarCopa() {
        String nombreCopa = eliminarCopaEditText.getText().toString().trim();
        if (copaExiste(nombreCopa)) {
            dbHelper.eliminarCopa(nombreCopa); // Eliminar de la base de datos
            copas.removeIf(copa -> copa.getNombre().equalsIgnoreCase(nombreCopa));
            adapartorCopas.notifyDataSetChanged();
            eliminarCopaEditText.setText("");
            Toast.makeText(this, "Copa eliminada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "La copa no existe", Toast.LENGTH_SHORT).show();
        }
    }

    private void editarCopa() {
        String nuevoNombre = editarCopa.getText().toString().trim();
        String nombreAntiguo = edicion.getText().toString().trim();
        if (!nuevoNombre.isEmpty() && copaExiste(nombreAntiguo)) {
            dbHelper.editarCopa(nombreAntiguo, nuevoNombre); // Editar en la base de datos
            for (Copa copa : copas) {
                if (copa.getNombre().equalsIgnoreCase(nombreAntiguo)) {
                    copa.setNombre(nuevoNombre);
                    break;
                }
            }
            adapartorCopas.notifyDataSetChanged();
            editarCopa.setText("");
            edicion.setText("");
            Toast.makeText(this, "Copa editada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "El nombre nuevo está vacío o la copa no existe", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarCopas() {
        copas.clear(); // Limpiar la lista antes de cargar
        ArrayList<Copa> copasDesdeDB = dbHelper.obtenerCopas(); // Obtener copas de la base de datos
        if (copasDesdeDB != null && !copasDesdeDB.isEmpty()) {
            copas.addAll(copasDesdeDB); // Agregar las copas a la lista
            adapartorCopas.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
        } else {
            Toast.makeText(this, "No se encontraron copas en la base de datos", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<String> trasladarArrayList() {
        ArrayList<String> listaNombres = new ArrayList<>();
        for (Copa copa : copas) {
            listaNombres.add(copa.getNombre());
        }
        return listaNombres;
    }
}