package com.example.proyectoandroid_luis_ruben;

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

public class Informacion extends AppCompatActivity {

    Toolbar toolbar;
    ListView listaCopas;
    ArrayList<Copa> copas = new ArrayList<>();
    ArrayAdapter<Copa> adapartorCopas = null;
    EditText añadircopa, eliminarCopaEditText, editarCopa;
    ImageView añadir, eliminar, editar;
    TextView edicion;
    String auxiliar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informacion);

        // MENU TOOLBAR INTEGRACION
        toolbar = findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);

        // LIGAMOS LA LISTVIEW LISTA COPAS DE JAVA CON LA DEL LAYOUT
        listaCopas = findViewById(R.id.listacopas);

        // EN EL ARRAYLIST DE LAS COPAS AÑADIMOS ALGUNAS
        insertarDatosLista(copas);

        // EN EL ADAPTADOR INTRODUCIMOS EL LAYOUT Y LA LISTA
        adapartorCopas = new ArrayAdapter<>(this, R.layout.itemcopa, R.id.nombreCopa, copas);

        //AHORA LIGAMOS LA LISTA CON EL ADAPTADOR RELLENO
        listaCopas.setAdapter(adapartorCopas);

        //LAS OPERACIONES SOBRE LAS COPAS SERAN AÑADIR, ELIMINAR Y EDITAR
        //AÑADIR
        añadircopa = findViewById(R.id.nuevaCopa);
        añadir = findViewById(R.id.newCupFoto);
        //ELIMINAR
        eliminarCopaEditText = findViewById(R.id.eliminarCopa);
        eliminar = findViewById(R.id.deleteCupFoto);
        //EDITAR
        editarCopa=findViewById(R.id.editarCopa);
        editar=findViewById(R.id.EditCupFoto);

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
       //OBTENEMOS LOS DATOS DEL ITEM SELECCIONADO
        listaCopas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombreCopaEditar=(String)parent.getItemAtPosition(position).toString().trim();
                edicion=findViewById(R.id.textoEditarCopa);
                auxiliar=edicion.getText().toString().trim();
                edicion.setText(nombreCopaEditar);
                if(edicion!=null){
                    editar.setVisibility(View.VISIBLE);
                }
            }//onItemClick
        });
        //Configuramos el listener para el ImageView de editar
        editar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {editarCopa();
            }//oncClickEditar
        });//clickListenerEditar
        //


    }//onCreate

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
        }else if(item.getItemId()== R.id.pilotos){
            Intent k = new Intent(this, CircuitosDisponibles.class);
            ArrayList<String>listaCopas=trasladarArrayList();
            k.putStringArrayListExtra("lista", listaCopas);
            startActivity(k);
        }//else if
        return false;
    }

    public void insertarDatosLista(ArrayList<Copa> copas) {
        copas.add(new Copa("Copa caparazon"));
        copas.add(new Copa("Copa Estrella"));
        copas.add(new Copa("Copa Bala"));
        copas.add(new Copa("Copa Platano"));
        copas.add(new Copa("Copa Mario"));
        // CAMBIAR ID
        for (int i = 1; i < copas.size(); i++) {
            copas.get(i).setId(i);
        }//for
    }//insertarDatosLista

    // Método para añadir una nueva copa
    public void añadirCopa() {

        boolean encontrar=false;
        String nombreCopa = añadircopa.getText().toString().trim();

        if (!nombreCopa.isEmpty()) {
            for (Copa copa : copas) {
                if (copa.getNombre().equalsIgnoreCase(nombreCopa)) {
                    Toast.makeText(this, "La copa ya existe: " + nombreCopa, Toast.LENGTH_SHORT).show();
                    encontrar=true;
                }//if
            }//for

            if(!encontrar){
                Copa nuevaCopa = new Copa(nombreCopa);
                copas.add(nuevaCopa);
                for (int i = 1; i < copas.size(); i++) {
                    copas.get(i).setId(i);
                }
                adapartorCopas.notifyDataSetChanged();
                Toast.makeText(this, "Copa añadida: " + nombreCopa, Toast.LENGTH_SHORT).show();
            }//if
        } else {
            Toast.makeText(this, "Por favor, introduce un nombre para la copa", Toast.LENGTH_SHORT).show();
        }//else
    }//añadirCopa

    // Método para eliminar una copa
    public void eliminarCopa() {

        String nombreCopa = eliminarCopaEditText.getText().toString().trim();
        boolean removed = false;

        if (!nombreCopa.isEmpty()) {
            for (int i = 0; i < copas.size(); i++) {
                if (copas.get(i).getNombre().equalsIgnoreCase(nombreCopa)) {
                    copas.remove(i);
                    removed = true;
                }//if
            }//for
            if (removed) {
                adapartorCopas.notifyDataSetChanged();
                Toast.makeText(this, "Copa eliminada: " + nombreCopa, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copa no encontrada", Toast.LENGTH_SHORT).show();
            }//else
        } else {
            Toast.makeText(this, "Por favor, introduce un nombre para la copa", Toast.LENGTH_SHORT).show();
        }//else
    }//eliminarCopa

    public void editarCopa(){
        String copa=editarCopa.getText().toString().trim();
        boolean encontrar=false;
        boolean cambiar=false;
        //
        for(int i=0; i<copas.size(); i++){
            if(copas.get(i).getNombre().equals(copa)){
                encontrar=true;
            }//if
        }//for
        if(!encontrar){
            for(int i=0; i<copas.size(); i++){
                if(copas.get(i).toString().equals(edicion.getText().toString())){
                    copas.get(i).setNombre(copa);
                    adapartorCopas.notifyDataSetChanged();
                    edicion.setText("");
                    editar.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "COPA EDITADA CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                }//if
            }//for
        }else{
            Toast.makeText(this, "EDICION FALLIDA, COPA YA EXISTENTE", Toast.LENGTH_SHORT).show();
        }//else

    }//editarCopa

    public ArrayList<String> trasladarArrayList(){

        ArrayList<String>lista=new ArrayList<String>();
        //
        for(int i=0; i<copas.size(); i++){
            lista.add(copas.get(i).toString());
        }//for
        return lista;
    }//trasladarArrayList

}//Informacion




