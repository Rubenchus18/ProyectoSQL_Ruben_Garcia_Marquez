package com.example.proyectoandroid_luis_ruben;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES_PUNTOS_TOTALES); // Crear tabla Puntos Totales

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES_USUARIO);
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES_PILOTO);
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES_COPA);
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES_PUNTOS_TOTALES); // Eliminar tabla Puntos Totales
        onCreate(db);
    }


    // Método para agregar una copa
    public void insertarCopa(Copa copa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.Copa.COLUMN_NAME_NOMBRE, copa.getNombre());
        values.put(EstructuraBBDD.Copa.COLUMN_NAME_DISTANCIA, copa.getDistancia());
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
    public Cursor obtenerCopas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(EstructuraBBDD.Copa.TABLE_NAME_PLAYLIST, null, null, null, null, null, null);
    }

    // Método para eliminar un piloto
    public boolean eliminarPiloto(String nombrePiloto) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(EstructuraBBDD.Piloto.TABLE_NAME_PLAYLIST, EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE + "=?", new String[]{nombrePiloto});
        db.close();
        return rowsAffected > 0;
    }

    // Método para editar un piloto
    public boolean editarPiloto(String nombreAntiguo, String nuevoNombre, String nuevoCoche) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE, nuevoNombre);
        values.put(EstructuraBBDD.Piloto.COLUMN_NAME_COCHE, nuevoCoche);
        int rowsAffected = db.update(EstructuraBBDD.Piloto.TABLE_NAME_PLAYLIST, values, EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE + "=?", new String[]{nombreAntiguo});
        db.close();
        return rowsAffected > 0;
    }

    // Método para obtener todos los pilotos
    public Cursor obtenerPilotos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(EstructuraBBDD.Piloto.TABLE_NAME_PLAYLIST, null, null, null, null, null, null);
    }

    // Método para verificar si un piloto existe
    public boolean pilotoExiste(String nombrePiloto) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(EstructuraBBDD.Piloto.TABLE_NAME_PLAYLIST, null, EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE + "=?", new String[]{nombrePiloto}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    // Método para insertar un piloto
    public void insertarPiloto(Piloto piloto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.Piloto.COLUMN_NAME_NOMBRE, piloto.getNombrepiloto());
        values.put(EstructuraBBDD.Piloto.COLUMN_NAME_COCHE, piloto.getCoche());
        db.insert(EstructuraBBDD.Piloto.TABLE_NAME_PLAYLIST, null, values);
        db.close();
    }


    public void insertarPuntosTotales(String nombrePiloto, String coche, int puntos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EstructuraBBDD.PuntosTotales.COLUMN_NAME_PILOTO, nombrePiloto);
        values.put(EstructuraBBDD.PuntosTotales.COLUMN_NAME_COCHE, coche);
        values.put(EstructuraBBDD.PuntosTotales.COLUMN_NAME_PUNTOS, puntos);

        long newRowId = db.insert(EstructuraBBDD.PuntosTotales.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Log.e("SQLiteHelper", "Error al insertar en PuntosTotales");
        } else {
            Log.d("SQLiteHelper", "Registro insertado con ID: " + newRowId);
        }

        db.close();
    }

    public Cursor obtenerPuntosTotales() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " +
                "ROWID AS _id, " +
                EstructuraBBDD.PuntosTotales.COLUMN_NAME_PILOTO + ", " +
                EstructuraBBDD.PuntosTotales.COLUMN_NAME_COCHE + ", " +
                "SUM(" + EstructuraBBDD.PuntosTotales.COLUMN_NAME_PUNTOS + ") AS total_puntos " +
                "FROM " + EstructuraBBDD.PuntosTotales.TABLE_NAME + " " +
                "GROUP BY " + EstructuraBBDD.PuntosTotales.COLUMN_NAME_PILOTO + ", " + EstructuraBBDD.PuntosTotales.COLUMN_NAME_COCHE;

        return db.rawQuery(query, null);
    }
}