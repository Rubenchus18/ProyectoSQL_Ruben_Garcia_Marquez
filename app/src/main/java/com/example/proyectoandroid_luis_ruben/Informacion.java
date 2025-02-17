package com.example.proyectoandroid_luis_ruben;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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



        toolbar = findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);

        listaCopas = findViewById(R.id.listacopas);
        dbHelper = new SQLiteHelper(this);

        adapartorCopas = new ArrayAdapter<>(this, R.layout.itemcopa, R.id.nombreCopa, copas);
        listaCopas.setAdapter(adapartorCopas);

        mostrarCopas();

        // AÑADIR
        añadircopa = findViewById(R.id.nuevaCopa);
        añadir = findViewById(R.id.newCupFoto);
        // ELIMINAR
        eliminarCopaEditText = findViewById(R.id.eliminarCopa);
        eliminar = findViewById(R.id.deleteCupFoto);
        // EDITAR
        editarCopa = findViewById(R.id.editarCopa);
        editar = findViewById(R.id.EditCupFoto);

        añadir.setOnClickListener(v -> añadirCopa());

        eliminar.setOnClickListener(v -> eliminarCopa());

        listaCopas.setOnItemClickListener((parent, view, position, id) -> {
            Copa copaSeleccionada = copas.get(position);
            edicion = findViewById(R.id.textoEditarCopa);
            edicion.setText(copaSeleccionada.getNombre());
            editar.setVisibility(View.VISIBLE);
        });

        editar.setOnClickListener(v -> editarCopa());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
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
        } else if (item.getItemId() == R.id.Mostrar_Resultado) {
            Intent l = new Intent(this, Puntos_Totales.class);
            startActivity(l);
        }else if (item.getItemId() == R.id.Fotos) {
            Intent m = new Intent(this, Fotos.class);
            startActivity(m);
        }else if (item.getItemId() == R.id.Grabar_Voz) {
            Intent m = new Intent(this, GrabacionVoz.class);
            startActivity(m);
        }else if (item.getItemId() == R.id.Videos) {
            Intent m = new Intent(this, Videos.class);
            startActivity(m);
        }
        return super.onOptionsItemSelected(item);
    }

    public void añadirCopa() {
        String nombreCopa = añadircopa.getText().toString().trim();
        if (!nombreCopa.isEmpty() && !copaExiste(nombreCopa)) {
            Random random = new Random();
            int distanciaAleatoria = random.nextInt(901) + 100; // Genera un número aleatorio entre 100 y 1000
            Copa nuevaCopa = new Copa(nombreCopa, String.valueOf(distanciaAleatoria));
            dbHelper.insertarCopa(nuevaCopa); // Insertar en la base de datos
            copas.add(nuevaCopa);
            adapartorCopas.notifyDataSetChanged();
            añadircopa.setText("");
            Toast.makeText(this, "Copa añadida", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "La copa ya existe o el nombre está vacío", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean copaExiste(String nombre) {
        for (Copa copa : copas) {
            if (copa.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    public void eliminarCopa() {
        String nombreCopa = eliminarCopaEditText.getText().toString().trim();
        if (copaExiste(nombreCopa)) {
            dbHelper.eliminarCopa(nombreCopa);
            copas.removeIf(copa -> copa.getNombre().equalsIgnoreCase(nombreCopa));
            adapartorCopas.notifyDataSetChanged();
            eliminarCopaEditText.setText("");
            Toast.makeText(this, "Copa eliminada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "La copa no existe", Toast.LENGTH_SHORT).show();
        }
    }

    public void editarCopa() {
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

    public void mostrarCopas() {
        Cursor cursor = dbHelper.obtenerCopas();
        if (cursor != null && cursor.getCount() > 0) {
            copas.clear();
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(EstructuraBBDD.Copa.COLUMN_NAME_NOMBRE));
                @SuppressLint("Range") String distancia = cursor.getString(cursor.getColumnIndex(EstructuraBBDD.Copa.COLUMN_NAME_DISTANCIA));
                Copa copa = new Copa(nombre, distancia);
                copas.add(copa);
            }
            adapartorCopas.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No se encontraron copas en la base de datos.", Toast.LENGTH_SHORT).show();
        }
    }
    public ArrayList<String> trasladarArrayList() {
        ArrayList<String> listaNombres = new ArrayList<>();
        for (Copa copa : copas) {
            listaNombres.add(copa.getNombre());
        }
        return listaNombres;
    }
}