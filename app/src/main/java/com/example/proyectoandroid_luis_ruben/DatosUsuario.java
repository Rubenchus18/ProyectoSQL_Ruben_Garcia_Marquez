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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_usuario);

        // CONEXION IDS
        textoDatos = findViewById(R.id.textoDatos);
        nombreUsuario = findViewById(R.id.nombreSesion);
        contraseñaUsuario = findViewById(R.id.contraseñaSesion);
        imagenRetroceder = findViewById(R.id.retroceder3);

        // CLICK LISTENER RETROCESO PAGINA ANTERIOR
        imagenRetroceder.setOnClickListener(this);

        // CARGAR DATOS DEL USUARIO
        cargarDatosUsuario();

        // MOSTRAR TEXTOS ADICIONALES
        mostrarTexto();
    } // onCreate

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, Informacion.class);
        startActivity(i);
    } // onClick

    public void mostrarTexto() {
        textoDatos.setText("En esta sección podrás ver tus datos de sesión cuando lo desees:");
    } // mostrarTexto

    private void cargarDatosUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "Usuario no disponible");
        String contraseña = sharedPreferences.getString("contraseña", "Contraseña no disponible");
        // Mostrar los datos en los TextView
        nombreUsuario.setText(usuario);
        contraseñaUsuario.setText(contraseña);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosUsuario(); // Cargar datos cada vez que se vuelve a la actividad
    }
} // DatosUsuario