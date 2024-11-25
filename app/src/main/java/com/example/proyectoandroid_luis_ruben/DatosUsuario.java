package com.example.proyectoandroid_luis_ruben;

import android.content.Intent;
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
        //CONEXION IDS
        textoDatos=findViewById(R.id.textoDatos);
        nombreUsuario = findViewById(R.id.nombreSesion);
        contraseñaUsuario = findViewById(R.id.contraseñaSesion);
        // Recibir datos del Intent
        Bundle recibirInformarcion=getIntent().getExtras();
        usuario = recibirInformarcion.getString("usuario");
        contraseña = recibirInformarcion.getString("contraseña");
        // Mostrar los datos en los TextView
        nombreUsuario.setText(usuario);
        contraseñaUsuario.setText(contraseña);
        imagenRetroceder=findViewById(R.id.retroceder3);
        //CLICK LISTENER RETROCESO PAGINA ANTERIOR
        imagenRetroceder.setOnClickListener(this);

        //MOSTRAR TEXTOS ADICIONALES
        mostrarTexto();;

    }//onCreate

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,Informacion.class);
        startActivity(i);
    }//onClick

    public void mostrarTexto(){
        textoDatos.setText("En esta seccion podras ver tu datos de sesion cuando lo desees:");
    }//mostrarTexto

}//DatosUsuario