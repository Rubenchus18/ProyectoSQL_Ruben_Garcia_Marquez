package com.example.proyectoandroid_luis_ruben;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class Informacion extends AppCompatActivity {

    Toolbar toolbar;
    ListView listaCopas;
    ArrayList<Copa>copas=new ArrayList<Copa>();
    ArrayAdapter<Copa>adapartorCopas=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informacion); // Asegúrate de que este sea tu layout
        //MENU TOOLBAR INTEGRACION
        toolbar=(Toolbar)findViewById(R.id.toolbarMenu);
        setSupportActionBar(toolbar);
        //LIGAMOS LA LISTVIEW LISTA COPAS DE JAVA CON LA DEL LAYOUT
        listaCopas=findViewById(R.id.listacopas);
        //EN EL ARRAYLIST DE LAS COPAS AÑADIMOS ALGUNAS
        insertarDatosLista(copas);
        //EN EL ADAPTADOR INTRODUCIMOS EL LAYOUT Y LA LISTA
        adapartorCopas=new ArrayAdapter<Copa>(this, R.layout.itemcopa, R.id.nombreCopa, copas);
        //AHORA LIGAMOS LA LISTA CON EL ADAPTADOR RELLENO
        listaCopas.setAdapter(adapartorCopas);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Desconectarse){
            Intent i=new Intent(this, MainActivity.class);
            startActivity(i);
        }else if(item.getItemId()==R.id.SobreNosotros1){
            Intent j=new Intent(this,SobreNosotros.class);
            startActivity(j);
        }else if(item.getItemId()==R.id.Usuario){
            Intent d=new Intent(this,DatosUsuario.class);
            startActivity(d);
        }//else if
        return false;
    }

    public void insertarDatosLista(ArrayList<Copa>copas){
        copas.add(new Copa("Copa caparazón"));
        copas.add(new Copa("Copa Estrella"));
        copas.add(new Copa("Copa Bala"));
        copas.add(new Copa("Copa Platano"));
        copas.add(new Copa("Copa Mario"));
        //CAMBIAR ID
        for(int i=1; i<copas.size(); i++){
           copas.get(i).setId(i);
        }//
    }//insertarDatosLista

}//Informacion