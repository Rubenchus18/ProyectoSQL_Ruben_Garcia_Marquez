package com.example.proyectoandroid_luis_ruben;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bdCarrera.db";
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES);
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIE);
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRI);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRIES);
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTRI);
        db.execSQL(EstructuraBBDD.SQL_DELETE_ENTR);
        //Se crea la nueva versión de la tabla
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIES);
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRIE);
        db.execSQL(EstructuraBBDD.SQL_CREATE_ENTRI);
    }

}

