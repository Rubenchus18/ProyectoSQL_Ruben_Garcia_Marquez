package com.example.proyectoandroid_luis_ruben;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DatosUsuario extends AppCompatActivity implements View.OnClickListener {

    // VARIABLES
    TextView nombreUsuario;
    TextView contraseñaUsuario;
    TextView textoDatos;
    ImageView imagenRetroceder;
    String usuario, contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_usuario);

        textoDatos = findViewById(R.id.textoDatos);
        nombreUsuario = findViewById(R.id.nombreSesion);
        contraseñaUsuario = findViewById(R.id.contraseñaSesion);
        imagenRetroceder = findViewById(R.id.retroceder3);

        // Intent y SharedPreferences
        Bundle recibirInformacion = getIntent().getExtras();
        if (recibirInformacion != null) {
            usuario = recibirInformacion.getString("usuario", "Usuario no definido");
            contraseña = recibirInformacion.getString("contraseña", "Contraseña no definida");
        } else {
            recuperarDatosUsuario();
        }

        // Mostrar los datos
        nombreUsuario.setText(usuario);
        contraseñaUsuario.setText(contraseña);

        // Click listener
        imagenRetroceder.setOnClickListener(this);

        // Mostrar texto adicional
        mostrarTexto();
    }
//onCreate

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,Informacion.class);
        startActivity(i);
    }//onClick

    public void mostrarTexto(){
        textoDatos.setText("En esta seccion podras ver tu datos de sesion cuando lo desees:");
    }//mostrarTexto

    public void recuperarDatosUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        usuario = sharedPreferences.getString("usuario", "Usuario no definido");
        contraseña = sharedPreferences.getString("contraseña", "Contraseña no definida");
    }
}//DatosUsuario