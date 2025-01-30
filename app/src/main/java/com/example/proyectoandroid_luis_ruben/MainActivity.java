package com.example.proyectoandroid_luis_ruben;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    // VARIABLES
    SQLiteDatabase db;
    EditText nombreUsuario, contrasenaUsuario;
    Button inicioSesion, registro;
    String nombre, contraseña;
    SQLiteHelper helper;
    CheckBox acuerdos;
    MediaPlayer mediaPlayer; // Variable para el MediaPlayer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        helper = new SQLiteHelper(this);
        db = helper.getWritableDatabase();

        // Inicializar el MediaPlayer y reproducir la música
        mediaPlayer = MediaPlayer.create(this, R.raw.musicafondo);
        mediaPlayer.setLooping(true); // Repetir la música
        mediaPlayer.start();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ATRIBUTOS
        nombreUsuario = findViewById(R.id.edittextusuario);
        contrasenaUsuario = findViewById(R.id.editTextTextContraseña);
        inicioSesion = findViewById(R.id.buttonInciarSesion);
        registro = findViewById(R.id.buttonRegistarse);
        acuerdos = findViewById(R.id.checkBox);

        inicioSesion.setOnClickListener(view -> {
            obtenerValores();
            if (comprobarNulos()) {
                registrarUsuario(view, nombre, contraseña);
            }
        });

        // REGISTRO
        registro.setOnClickListener(view -> {
            obtenerValores();
            if (comprobarNulos()) {
                crearUsuario(view);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Detener y liberar el MediaPlayer al destruir la actividad
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void inserta(String nombre, String contraseña) {
        ContentValues values = new ContentValues();
        values.put("Nombre", nombre);
        values.put("Contraseña", contraseña);
        db.insert("Usuario", null, values);
    }

    public boolean comprobarNulos() {
        if (nombre.isEmpty() && contraseña.isEmpty()) {
            Toast.makeText(getApplicationContext(), "INTRODUZCA UN INICIO DE SESIÓN", Toast.LENGTH_LONG).show();
            return false;
        } else if (nombre.isEmpty() || contraseña.isEmpty()) {
            if (nombre.isEmpty()) {
                Toast.makeText(getApplicationContext(), "CAMPO DE USUARIO OBLIGATORIO", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "CAMPO DE CONTRASEÑA OBLIGATORIO", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }

    public void crearUsuario(View view) {
        try {
            if (nombre.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos.", Toast.LENGTH_LONG).show();
                return;
            }
            Cursor cursor = db.rawQuery("SELECT * FROM Usuario WHERE contraseña = ?", new String[]{contraseña});
            if (cursor.getCount() > 0) {
                Toast.makeText(getApplicationContext(), "ESTE USUARIO YA EXISTE", Toast.LENGTH_LONG).show();
            } else {
                inserta(nombre, contraseña);
                nombreUsuario.setText("");
                contrasenaUsuario.setText("");
                Toast.makeText(getApplicationContext(), "USUARIO CREADO CORRECTAMENTE", Toast.LENGTH_LONG).show();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(), "Error de base de datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void registrarUsuario(View view, String nombre, String contraseña) {
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM Usuario WHERE nombre = ? AND contraseña = ?", new String[]{nombre, contraseña});
            if (cursor.getCount() > 0) {
                if (!acuerdos.isChecked()) {
                    Toast.makeText(getApplicationContext(), "ACEPTE LOS ACUERDOS DE USUARIO", Toast.LENGTH_LONG).show();
                } else {
                    siguienteActividad(view);
                    Toast.makeText(getApplicationContext(), "INICIANDO SESIÓN", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "INICIO DE SESION INCORRECTO", Toast.LENGTH_LONG).show();
            }
            cursor.close();
        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(), "Error de base de datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void obtenerValores() {
        nombre = nombreUsuario.getText().toString().trim();
        contraseña = contrasenaUsuario.getText().toString().trim();
    }

    public void siguienteActividad(View view) {
        Intent siguienteActividad = new Intent(this, Informacion.class);
        startActivity(siguienteActividad);
    }
}