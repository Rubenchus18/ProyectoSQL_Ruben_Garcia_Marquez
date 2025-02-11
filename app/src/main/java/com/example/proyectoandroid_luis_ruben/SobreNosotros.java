package com.example.proyectoandroid_luis_ruben;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SobreNosotros extends AppCompatActivity implements View.OnClickListener {
    ImageView imagenRetroceder;
    TextView texto1, texto2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sobre_nosotros);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //LLAMADAS
        texto1=findViewById(R.id.textoNosotros);
        texto2=findViewById(R.id.textoNosotros2);
        //INSERTAR TEXTOO
        imagenRetroceder=findViewById(R.id.retroceder);
        //CLICK LISTENER RETROCESO PAGINA ANTERIOR
        imagenRetroceder.setOnClickListener(this);
    }//onCreate

    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,Informacion.class);
        startActivity(i);
    }
}//SobreNosotros