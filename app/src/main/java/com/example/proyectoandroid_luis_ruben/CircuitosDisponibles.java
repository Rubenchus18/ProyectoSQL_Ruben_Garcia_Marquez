package com.example.proyectoandroid_luis_ruben;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class CircuitosDisponibles extends AppCompatActivity {


    ArrayAdapter<String>adaptadorListaCopa=null;
    ListView lista;
    ImageView atras;
    ArrayList<String> circuitos=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_circuitos_disponibles);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //RECUPERAMOS EL ARRAYLIST DE LA ANTERIOR ACTIVIDAD
        circuitos =getIntent().getStringArrayListExtra("lista");
        //IR A LA ACTIVIDAD ANTERIOR
            //ID DE LA IMAGEN
            atras=findViewById(R.id.actividadAnterior);
            //LE DAMOS FUNCIONALIDAD CON SU CLICK LISTENER
            atras.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moverActividad();
                }//onClick
            });
            for(int i=0; i<circuitos.size(); i++){
                System.out.println(circuitos.get(i));

            }//for
        //ASIGNACION DE LISTVIEW
        asignacionList();

    }//onCreate

    public void moverActividad(){
        Intent i=new Intent(this, Informacion.class);
        startActivity(i);
    }//moverActividad

    public void asignacionList(){
        //LA LISTVIEW DE ESTA CLASE LA LIGAMOS A LA DEL LAYOUT
        lista=findViewById(R.id.listaOps);
        //COMPLETAMOS EL ADAPTADOR CON EL LAYOUT Y SU ARRAYLIST
        adaptadorListaCopa=new ArrayAdapter<>(this, R.layout.itemcopa, R.id.nombreCopa, circuitos);
        //AHORA LA LISTVIEW DE LA CLASE SERA RELLENADO CON EL ADAPTADOR COMPLETADO
        lista.setAdapter(adaptadorListaCopa);
    }


}//CircuitosDisponibles