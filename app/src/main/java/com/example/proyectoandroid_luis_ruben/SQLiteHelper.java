package com.example.proyectoandroid_luis_ruben;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
}