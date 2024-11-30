package com.example.proyectoandroid_luis_ruben;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Informacion extends AppCompatActivity {

    Toolbar toolbar;
    ListView listaCopas;
    ArrayList<Copa> copas = new ArrayList<>();
    ArrayAdapter<Copa> adapartorCopas = null;
    EditText añadircopa;
    ImageView añadir;
    EditText eliminarCopaEditText; // Cambiado el nombre para evitar confusiones
    ImageView eliminar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informacion); // Asegúrate de que este sea tu layout

        // MENU TOOLBAR INTEGRACION
        toolbar = findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);

        // LIGAMOS LA LISTVIEW LISTA COPAS DE JAVA CON LA DEL LAYOUT
        listaCopas = findViewById(R.id.listacopas);

        // EN EL ARRAYLIST DE LAS COPAS AÑADIMOS ALGUNAS
        insertarDatosLista(copas);

        // EN EL ADAPTADOR INTRODUCIMOS EL LAYOUT Y LA LISTA
        adapartorCopas = new ArrayAdapter<>(this, R.layout.itemcopa, R.id.nombreCopa, copas);

        // AHORA LIGAMOS LA LISTA CON EL ADAPTADOR RELLENO
        listaCopas.setAdapter(adapartorCopas);

        añadircopa = findViewById(R.id.editText5);
        añadir = findViewById(R.id.imageView4);

        eliminarCopaEditText = findViewById(R.id.editText2); // Asegúrate de que el ID sea correcto
        eliminar = findViewById(R.id.imageView11);

        // Configuramos el listener para el ImageView de añadir
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
        } else if (item.getItemId() == R.id.Usuario) {
            Intent d = new Intent(this, DatosUsuario.class);
            startActivity(d);
        }
        return false;
    }

    public void insertarDatosLista(ArrayList<Copa> copas) {
        copas.add(new Copa("Copa caparazón"));
        copas.add(new Copa("Copa Estrella"));
        copas.add(new Copa("Copa Bala"));
        copas.add(new Copa("Copa Platano"));
        copas.add(new Copa("Copa Mario"));
        // CAMBIAR ID
        for (int i = 1; i < copas.size(); i++) {
            copas.get(i).setId(i);
        }
    }


    // Método para añadir una nueva copa
    public void añadirCopa() {
        String nombreCopa = añadircopa.getText().toString().trim();

        if (!nombreCopa.isEmpty()) {
            for (Copa copa : copas) {
                if (copa.getNombre().equalsIgnoreCase(nombreCopa)) {
                    Toast.makeText(this, "La copa ya existe: " + nombreCopa, Toast.LENGTH_SHORT).show();

                    return;
                }
            }
            Copa nuevaCopa = new Copa(nombreCopa);
            copas.add(nuevaCopa);
            for (int i = 1; i < copas.size(); i++) {
                copas.get(i).setId(i);
            }
            adapartorCopas.notifyDataSetChanged();
            añadircopa.setText("");
            Toast.makeText(this, "Copa añadida: " + nombreCopa, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Por favor, introduce un nombre para la copa", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para eliminar una copa
    public void eliminarCopa() {
        String nombreCopa = eliminarCopaEditText.getText().toString().trim();
        if (!nombreCopa.isEmpty()) {
            boolean removed = false;
            for (int i = 0; i < copas.size(); i++) {
                Copa copa = copas.get(i);
                if (copa.getNombre().equalsIgnoreCase(nombreCopa)) {
                    copas.remove(i);
                    removed = true;
                    break;
                }
            }
            if (removed) {
                adapartorCopas.notifyDataSetChanged();
                eliminarCopaEditText.setText("");
                Toast.makeText(this, "Copa eliminada: " + nombreCopa, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copa no encontrada", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor, introduce un nombre para la copa", Toast.LENGTH_SHORT).show();
        }
    }
}