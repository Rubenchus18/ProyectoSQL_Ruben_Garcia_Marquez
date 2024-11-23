package com.example.proyectoandroid_luis_ruben;

import android.content.Intent;
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
        //INSERTAR TEXTO
        insertarTexto();
        imagenRetroceder=findViewById(R.id.retroceder);
        //CLICK LISTENER RETROCESO PAGINA ANTERIOR
        imagenRetroceder.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        Intent i=new Intent(this,Informacion.class);
        startActivity(i);
    }
    public void insertarTexto(){
        texto1.setText("En Drafter nuestra misión es llevar la emocion de las carreras al siguiente nivel. Somos una empresa apasaionada por la tecnologia y los deportes de motor, especializada en el desarrollo de simuladores de carreras");
        texto2.setText("Nuestro objetivo es ofrecer una experiencia de conduccion realiasta y accesible tanto para aficionados como para profesionales del automovilismo");
    }//insertarTexto

}//SobreNosotros