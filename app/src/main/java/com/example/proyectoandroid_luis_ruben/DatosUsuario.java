package com.example.proyectoandroid_luis_ruben;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DatosUsuario extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewNombreUsuario;
    private TextView textViewContraseña;
    ImageView imagenRetroceder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_usuario); // Asegúrate de que este sea tu layout

        textViewNombreUsuario = findViewById(R.id.textViewNombreUsuario);
        textViewContraseña = findViewById(R.id.textViewContraseña);

        // Recibir datos del Intent
        Intent intent = getIntent();
        String usuario = intent.getStringExtra("usuario");
        String contraseña = intent.getStringExtra("contraseña");

        // Mostrar los datos en los TextView
        textViewNombreUsuario.setText(usuario);
        textViewContraseña.setText(contraseña);
        imagenRetroceder=findViewById(R.id.retroceder3);
        //CLICK LISTENER RETROCESO PAGINA ANTERIOR
        imagenRetroceder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,Informacion.class);
        startActivity(i);
    }
}