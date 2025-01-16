package com.example.proyectoandroid_luis_ruben;

import android.provider.BaseColumns;

public final class EstructuraBBDD {
    // SQL para crear la tabla Piloto
    public static final String SQL_CREATE_ENTRIES_PILOTO =
            "CREATE TABLE IF NOT EXISTS " +
                    Piloto.TABLE_NAME_PLAYLIST +
                    " (" + Piloto._ID + " INTEGER PRIMARY KEY, " +
                    Piloto.COLUMN_NAME_NOMBRE + " TEXT, " +
                    Piloto.COLUMN_NAME_COCHE + " TEXT);";

    // SQL para eliminar la tabla Piloto
    public static final String SQL_DELETE_ENTRIES_PILOTO =
            "DROP TABLE IF EXISTS " +
                    Piloto.TABLE_NAME_PLAYLIST;

    // SQL para crear la tabla Usuario
    public static final String SQL_CREATE_ENTRIES_USUARIO =
            "CREATE TABLE IF NOT EXISTS " +
                    Usuario.TABLE_NAME_PLAYLIST +
                    " (" + Usuario._ID + " INTEGER PRIMARY KEY, " +
                    Usuario.COLUMN_NAME_NOMBRE + " TEXT, " +
                    Usuario.COLUMN_NAME_CONTRASENA + " TEXT);";

    // SQL para eliminar la tabla Usuario
    public static final String SQL_DELETE_ENTRIES_USUARIO =
            "DROP TABLE IF EXISTS " +
                    Usuario.TABLE_NAME_PLAYLIST;

    // SQL para crear la tabla Copa
    public static final String SQL_CREATE_ENTRIES_COPA =
            "CREATE TABLE IF NOT EXISTS " +
                    Copa.TABLE_NAME_PLAYLIST +
                    " (" + Copa._ID + " INTEGER PRIMARY KEY, " +
                    Copa.COLUMN_NAME_NOMBRE + " TEXT, " +
                    Copa.COLUMN_NAME_DISTANCIA + " TEXT);";

    // SQL para eliminar la tabla Copa
    public static final String SQL_DELETE_ENTRIES_COPA =
            "DROP TABLE IF EXISTS " +
                    Copa.TABLE_NAME_PLAYLIST;

    private EstructuraBBDD() {}

    /* Clase interna que define la estructura de la tabla de Piloto */
    public static class Piloto implements BaseColumns {
        public static final String TABLE_NAME_PLAYLIST = "Piloto";
        public static final String COLUMN_NAME_NOMBRE = "Nombre";
        public static final String COLUMN_NAME_COCHE = "Coche";
    }

    /* Clase interna que define la estructura de la tabla de Usuario */
    public static class Usuario implements BaseColumns {
        public static final String TABLE_NAME_PLAYLIST = "Usuario";
        public static final String COLUMN_NAME_NOMBRE = "Nombre";
        public static final String COLUMN_NAME_CONTRASENA = "Contraseña";
    }

    /* Clase interna que define la estructura de la tabla de Copa */
    public static class Copa implements BaseColumns {
        public static final String TABLE_NAME_PLAYLIST = "Copa";
        public static final String COLUMN_NAME_NOMBRE = "Nombre";
        public static final String COLUMN_NAME_DISTANCIA = "Distancia"; // Cambiado a DISTANCIA
    }
}