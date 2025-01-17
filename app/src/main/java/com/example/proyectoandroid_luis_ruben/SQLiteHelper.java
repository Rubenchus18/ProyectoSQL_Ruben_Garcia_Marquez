package com.example.proyectoandroid_luis_ruben;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bdCarrera.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear las tablas
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES_USUARIO);
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES_PILOTO);
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES_COPA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar las tablas si existen
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES_USUARIO);
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES_PILOTO);
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES_COPA);
        onCreate(db);
    }

    // Método para agregar una copa
    public void insertarCopa(Copa copa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.Copa.COLUMN_NAME_NOMBRE, copa.getNombre());
        values.put(EstructuraBBDD.Copa.COLUMN_NAME_DISTANCIA, copa.getDistancia()); // Asegúrate de que la distancia se esté guardando
        db.insert(EstructuraBBDD.Copa.TABLE_NAME_PLAYLIST, null, values);
        db.close();
    }

    // Método para eliminar una copa
    public void eliminarCopa(String nombreCopa) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EstructuraBBDD.Copa.TABLE_NAME_PLAYLIST, EstructuraBBDD.Copa.COLUMN_NAME_NOMBRE + "=?", new String[]{nombreCopa});
        db.close();
    }

    // Método para editar una copa
    public void editarCopa(String nombreAntiguo, String nuevoNombre) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.Copa.COLUMN_NAME_NOMBRE, nuevoNombre);
        db.update(EstructuraBBDD.Copa.TABLE_NAME_PLAYLIST, values, EstructuraBBDD.Copa.COLUMN_NAME_NOMBRE + "=?", new String[]{nombreAntiguo});
        db.close();
    }

    // Método para obtener todas las copas
    public ArrayList<Copa> obtenerCopas() {
        ArrayList<Copa> copas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(EstructuraBBDD.Copa.TABLE_NAME_PLAYLIST, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(EstructuraBBDD.Copa.COLUMN_NAME_NOMBRE));
                @SuppressLint("Range") String distancia = cursor.getString(cursor.getColumnIndex(EstructuraBBDD.Copa.COLUMN_NAME_DISTANCIA));
                copas.add(new Copa(nombre, distancia));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return copas;
    }
    public boolean eliminarPiloto(String nombrePiloto) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(EstructuraBBDD.Piloto.TABLE_NAME_PLAYLIST, EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE + "=?", new String[]{nombrePiloto});
        db.close();
        return rowsAffected > 0;
    }
    public boolean editarPiloto(String nombreAntiguo, String nuevoNombre, String nuevoCoche) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE, nuevoNombre);
        values.put(EstructuraBBDD.Piloto.COLUMN_NAME_COCHE, nuevoCoche);
        int rowsAffected = db.update(EstructuraBBDD.Piloto.TABLE_NAME_PLAYLIST, values, EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE + "=?", new String[]{nombreAntiguo});
        db.close();
        return rowsAffected > 0;
    }
    public ArrayList<Piloto> obtenerPilotos() {
        ArrayList<Piloto> pilotos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(EstructuraBBDD.Piloto.TABLE_NAME_PLAYLIST, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex(EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE));
                @SuppressLint("Range") String coche = cursor.getString(cursor.getColumnIndex(EstructuraBBDD.Piloto.COLUMN_NAME_COCHE));
                pilotos.add(new Piloto(nombre, coche));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pilotos;
    }
    public boolean pilotoExiste(String nombrePiloto) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(EstructuraBBDD.Piloto.TABLE_NAME_PLAYLIST, null, EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE + "=?", new String[]{nombrePiloto}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }
    public void insertarPiloto(Piloto piloto) {
        SQLiteDatabase db = this.getWritableDatabase(); // Abrir la base de datos en modo escritura
        ContentValues values = new ContentValues(); // Crear un objeto ContentValues para almacenar los valores a insertar
        values.put(EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE, piloto.getNombrepiloto()); // Agregar el nombre del piloto
        values.put(EstructuraBBDD.Piloto.COLUMN_NAME_COCHE, piloto.getCoche()); // Agregar el nombre del coche
        db.insert(EstructuraBBDD.Piloto.TABLE_NAME_PLAYLIST, null, values); // Insertar los valores en la tabla
        db.close(); // Cerrar la base de datos
    }
}