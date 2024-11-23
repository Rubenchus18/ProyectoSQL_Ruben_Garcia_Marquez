package com.example.proyectoandroid_luis_ruben;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DatosUsuario extends AppCompatActivity {
    public TextView textViewUsuario;
    public TextView textViewContraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textViewUsuario = findViewById(R.id.textViewNombreUsuario);
        textViewContraseña = findViewById(R.id.textViewContraseña);

        // Recibir datos del Intent
        String usuario = getIntent().getStringExtra("nombre");
        String contraseña = getIntent().getStringExtra("contraseña");

        // Mostrar los datos en los TextView
        textViewUsuario.setText( usuario);
        textViewContraseña.setText( contraseña);
    }

}