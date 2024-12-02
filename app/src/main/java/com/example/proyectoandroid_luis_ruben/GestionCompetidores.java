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

public class GestionCompetidores extends AppCompatActivity {
    EditText nombrecoche;
    EditText editarnombreCoche;
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

        //RECUPERAMOS DE LA ACTIVIDAD ANTERIOR EL CIRCUTIO QUE ELEJIMOS
        datosCopa = getIntent().getStringExtra("circuito");
        //LO IMPRIMIMMOS PARA HACERSELO SABER AL USUARIO
        carreraElegida = findViewById(R.id.imprimirCarrera);
        carreraElegida.setText(datosCopa);
        //LIGAMOS NUESTRAS VARIABLES A LOS ID DEL LAYOUT
        nombrecoche = findViewById(R.id.editTextnombrecoche);
        nombrepiloto = findViewById(R.id.editTextnombrepiloto);
        nombreeliminar = findViewById(R.id.editTextnombreliminar);
        listViewPilotos = findViewById(R.id.listviewpilotos);
        imprimirInformacion=findViewById(R.id.infoPiloto);
        //LA VISIBILIDAD DE ESTOS 2 COMPONENTES ES INVISIBLE HASTA QUE SE CUMPLAN CIERTAS CONDICIOENS MAS ADELANTE
        editarPiloto=findViewById(R.id.editarPiloto);
        editarCoche=findViewById(R.id.editarCoche);
        editarPiloto.setVisibility(View.INVISIBLE);
        editarCoche.setVisibility(View.INVISIBLE);
        //
        listaPilotos = new ArrayList<Piloto>();
        //REUTILIZAMOS EL ITEM DE LA LISTA DE COPAS
        pilotosArrayAdapter = new ArrayAdapter<>(this, R.layout.itemcopa, R.id.nombreCopa, listaPilotos);
        listViewPilotos.setAdapter(pilotosArrayAdapter);
        //BOTON DE AÑADIR ACCION CLIC LISTENER
        Button buttonInsertar = findViewById(R.id.buttonAgregar);
        buttonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarpilotos();
            }
        });
        //BOTON DE ELIMINAR ACCION CLIC LISTENER
        eliminar = findViewById(R.id.imageVieweliminar);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPiloto();
            }
        });

        //OBTENER LOS VALORES DEL ITEM QUE SELECCIONEMOS
        informmacionPiloto();

        //BOTON DE EDITAR ACCION CLIC LISTENER - ESTA IMAGEN SERA INVISIBLE HASTA QUE SE CUMPLA CIERTA CONDICION
        editar = findViewById(R.id.fotoEditar);
        editar.setVisibility(View.INVISIBLE);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarPiloto();
            }
        });
        //BOTON DE RETROCEDER IR A LA ACTIVIDAD ANTERIOR
        retrocedemos = findViewById(R.id.imageView7);
        retrocedemos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionCompetidores.this, CircuitosDisponibles.class);
                startActivity(i);
            }
        });

    }

    //FUNCIONALIDAD AÑADIR PILOTO A LA CARRERA
    private void insertarpilotos() {
        String nombrePiloto = nombrepiloto.getText().toString().trim();
        String nombreCoche = nombrecoche.getText().toString().trim();
        Piloto piloto= null;
        boolean encontrar=true;

        if (!nombrePiloto.isEmpty() && !nombreCoche.isEmpty()) {
            piloto=new Piloto(nombrePiloto, nombreCoche);
           for(int i=0; i<listaPilotos.size(); i++){
               if(listaPilotos.get(i).getNombrepiloto().equalsIgnoreCase(piloto.getNombrepiloto())){
                   encontrar=false;
               }//if
           }//for
            if(encontrar){
                listaPilotos.add(piloto);
                pilotosArrayAdapter.notifyDataSetChanged();
                nombrepiloto.setText("Piloto");
                nombrecoche.setText("Coche");
                Toast.makeText(this, "Piloto añadido correctamente.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Piloto ya existente.", Toast.LENGTH_SHORT).show();
            }//else
        } else {
            Toast.makeText(this, "Por favor, completa ambos campos.", Toast.LENGTH_SHORT).show();
        }//else

    }//insertarPilotos

    private void eliminarPiloto() {

        String nombrePilotoAEliminar = nombreeliminar.getText().toString();
        boolean eliminado = false;

        if (!nombrePilotoAEliminar.isEmpty()) {
            for (int i = 0; i < listaPilotos.size(); i++) {
                if (listaPilotos.get(i).getNombrepiloto().equalsIgnoreCase(nombrePilotoAEliminar)) {
                    listaPilotos.remove(i);
                    eliminado = true;
                    pilotosArrayAdapter.notifyDataSetChanged();
                    nombreeliminar.setText("Piloto");
                    Toast.makeText(this, "Piloto eliminado.", Toast.LENGTH_SHORT).show();
                }//if
            }//for
            if (!eliminado) {
                Toast.makeText(this, "Piloto no encontrado.", Toast.LENGTH_SHORT).show();
            }//if
        } else {
            Toast.makeText(this, "Por favor, ingresa el nombre del piloto a eliminar.", Toast.LENGTH_SHORT).show();
        }//else

    }//elimminarPiloto

    public void informmacionPiloto(){

        //OBTENEMOS LOS DATOS DEL ITEM SELECCIONADO
        listViewPilotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                informacionPiloto=(String)parent.getItemAtPosition(position).toString().trim();
                imprimirInformacion.setText(informacionPiloto);
                editarCoche.setVisibility(View.VISIBLE);
                editarPiloto.setVisibility(View.VISIBLE);
                editar.setVisibility(View.VISIBLE);
            }//onItemClick
        });
    }//informacionPiloto

    public  void editarPiloto() {

        String coche=editarCoche.getText().toString().trim();
        String piloto=editarPiloto.getText().toString().trim();
        boolean cambios=false;
        boolean encontrar=false;
        int indice=0;
        if(!coche.isEmpty() && !piloto.isEmpty()){
            for(int i=0; i<listaPilotos.size(); i++){
                if(listaPilotos.get(i).toString().equals(informacionPiloto)){
                    if(!listaPilotos.get(i).getCoche().equalsIgnoreCase(coche) || !listaPilotos.get(i).getNombrepiloto().equalsIgnoreCase(piloto)){
                       indice=i;
                       cambios=true;
                    }else{
                        Toast.makeText(this, "cambios inexistentes", Toast.LENGTH_SHORT).show();
                    }//else
                }//if
            }//for
        }else{
            Toast.makeText(this, "Piloto no encontrado", Toast.LENGTH_SHORT).show();
        }//else
        //
        if(cambios){
            for(int i=0; i<listaPilotos.size(); i++){
                if(listaPilotos.get(i).getNombrepiloto().equals(piloto)){
                    encontrar=true;
                }//if
            }//for
            if(!encontrar){
                listaPilotos.get(indice).setNombrepiloto(piloto);
                listaPilotos.get(indice).setCoche(coche);
                pilotosArrayAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Piloto editado", Toast.LENGTH_SHORT).show();
                imprimirInformacion.setText("");
                editarCoche.setVisibility(View.INVISIBLE);
                editarPiloto.setVisibility(View.INVISIBLE);
                editar.setVisibility(View.INVISIBLE);
            }else{
                Toast.makeText(this, "Piloto ya existente", Toast.LENGTH_SHORT).show();
            }
        }//for


    }//editarPiloto

}//GestionCompetidores