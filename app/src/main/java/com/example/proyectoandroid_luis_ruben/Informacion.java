package com.example.proyectoandroid_luis_ruben;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Informacion extends AppCompatActivity {

    String usuario;
    String contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informacion); // Asegúrate de que este sea tu layout
        // Recibir datos del Intent
        Intent intent = getIntent();
        usuario = intent.getStringExtra("usuario");
        contraseña = intent.getStringExtra("contraseña");
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
            d.putExtra("usuario",usuario);
            d.putExtra("contraseña",contraseña);
            startActivity(d);
        }//else if
        return false;
    }


}