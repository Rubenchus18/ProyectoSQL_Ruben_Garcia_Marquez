package com.example.proyectoandroid_luis_ruben;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GestionCompetidores extends AppCompatActivity {

    String datosCopa;
    TextView carreraElegida;
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
        //COGEMOS LOS VALORES DE LA ANTERIOR ACTIVIDAD QUE NOS HEMOS PASADO
        datosCopa=getIntent().getStringExtra("circuito");
        //IMPRIMIMOS DICHA CARRERA
        carreraElegida=findViewById(R.id.imprimirCarrera);
        carreraElegida.setText(datosCopa);


    }
}