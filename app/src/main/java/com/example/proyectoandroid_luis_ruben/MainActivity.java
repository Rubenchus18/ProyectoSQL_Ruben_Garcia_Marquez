package com.example.proyectoandroid_luis_ruben;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //VARIABLES
    EditText nombreUsuario, contrasenaUsuario;
    Button inicioSesion, registro;
    String nombre, contraseña;
    CheckBox acuerdos;
    boolean comprobar=false;
    //ARRAYLIST USUARIOS
    ArrayList<Usuario>listaUsuarios=new ArrayList<Usuario>();
    //USUARIOS POR DEFECTO
    Usuario usuario1=new Usuario("Yolanda", "1234");
    Usuario usuario2=new Usuario("Ruben", "abcd");
    Usuario usuario3=new Usuario("Luis", "Luis");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //ANIDACION DE USUARIOS A LA LISTA DE USUARIOS
        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);
        listaUsuarios.add(usuario3);
        //ATRIBUTOS
        nombreUsuario=findViewById(R.id.edittextusuario);
        contrasenaUsuario=findViewById(R.id.editTextTextContraseña);
        inicioSesion=findViewById(R.id.buttonInciarSesion);
        registro=findViewById(R.id.buttonRegistarse);
        acuerdos=findViewById(R.id.checkBox);
        //CLICK LISTENERS DE LOS BOTONES
        //INICIO DE SESION
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerValores();
                comprobar=comprobarNulos(view);
                if(comprobar){
                    registrarUsuario(view, nombre, contraseña);
                }//if
            }//onClickInicioSesion
        });
        //REGISTRO
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerValores();
                comprobar=comprobarNulos(view);
               if(comprobar){
                   crearUsuario(view);
               }//if
            }//onClickInicioSesion.

        });;;

    }//onCreate

    public boolean comprobarNulos(View view){

        boolean entrar=false;
        if(nombre.isEmpty() && contraseña.isEmpty()){
            entrar=false;
            Toast.makeText(getApplicationContext(), "INTRODUZCA UN INICIO DE SESIÓN", Toast.LENGTH_LONG).show();
        }else if(nombre.isEmpty() || contraseña.isEmpty()){
            entrar=false;
            if(nombre.isEmpty()){
                Toast.makeText(getApplicationContext(), "CAMPO DE USUARIO OBLIGATORIO", Toast.LENGTH_LONG).show();
            }else if(contraseña.isEmpty()){
                Toast.makeText(getApplicationContext(), "CAMPO DE CONTRASEÑA OBLIGATORIO", Toast.LENGTH_LONG).show();
            }//else if
        }else{
            entrar=true;
        }//else
        return entrar;
    }//comprobarNulos

    public void crearUsuario(View view){
        Usuario usuario=new Usuario();
        boolean existe=false;

        for(int i=0; i<listaUsuarios.size(); i++) {
            if (nombre.equals(listaUsuarios.get(i).getNombre()) && contraseña.equals(listaUsuarios.get(i).getContraseña())) {
                existe = true;
            }//if
        }//for
        if(existe){
                    Toast.makeText(getApplicationContext(), "ESTE USUARIO YA EXISTE", Toast.LENGTH_LONG).show();
        }else if(existe==false){
                    //CREAMOS EL USUARIO SI PASO TODO EL RECONOMIENTO PREVIO
                    String nombre=String.valueOf(nombreUsuario.getText());
                    String contraseña=String.valueOf(contrasenaUsuario.getText());
                    usuario=new Usuario(nombre, contraseña);
                    listaUsuarios.add(usuario);
                    nombreUsuario.setText("");
                    contrasenaUsuario.setText("");
                    //SE LO DECIMOS AL USUARIO TAMBIEN
                    Toast.makeText(getApplicationContext(), "USUARIO CREADO CORRECTAMENTE", Toast.LENGTH_LONG).show();
                }//else

    }//crearUsuario

    public void registrarUsuario(View view, String nombre, String contraseña){

        boolean registro=false;
        String texto=null;
        for(int i=0; i<listaUsuarios.size(); i++){
            if(listaUsuarios.get(i).getNombre().equals(nombre) && listaUsuarios.get(i).getContraseña().equals(contraseña)){
                registro=true;
            }else{
                if(!listaUsuarios.get(i).getNombre().equals(nombre) && !listaUsuarios.get(i).getContraseña().equals(contraseña)){
                   texto="INICIO DE SESION INCORRECTO";
                }else if(!listaUsuarios.get(i).getNombre().equals(nombre)){
                    texto="USUARIO INCORRECTO";
                }else if(!listaUsuarios.get(i).getContraseña().equals(contraseña)){
                    texto="CONTRASEÑA INCORRECTA";;
                }//else if
            }//else
        }//for
        if(registro){
            if(!acuerdos.isChecked()) {
                Toast.makeText(getApplicationContext(), "ACEPTE LOS ACUERDOS DE USUARIO", Toast.LENGTH_LONG).show();
            }else{
                siguienteActividad(view);
                Toast.makeText(getApplicationContext(), "INICIANDO SESIÓN", Toast.LENGTH_LONG).show();
            }//else
        }else{
            Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG).show();
        }//else

    }//registrarUsuario

    public void obtenerValores(){
        nombre=nombreUsuario.getText().toString().trim();
        contraseña=contrasenaUsuario.getText().toString().trim();
        //Sin este metodo la app da error, puesto que al intetar recuperar los valores antes de los metodos on click por ende antes de inicializarlos puede provocar nulos
    }//obtener valores

    public void siguienteActividad(View view){
        Intent siguienteActividad = new Intent(this, Informacion.class);
        siguienteActividad.putExtra("usuario", nombre);  // Pasar el nombre de usuario
        siguienteActividad.putExtra("contraseña", contraseña); // Pasar la contraseña
        guardarDatosUsuario(nombre, contraseña);
        startActivity(siguienteActividad);
    }//siguiente actividad
    public void guardarDatosUsuario(String usuario, String contraseña) {
        SharedPreferences sharedPreferences = getSharedPreferences("DatosUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usuario", usuario);
        editor.putString("contraseña", contraseña);
        editor.apply();
    }
}//MainActivity
