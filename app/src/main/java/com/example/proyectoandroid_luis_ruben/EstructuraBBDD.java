package com.example.proyectoandroid_luis_ruben;

import android.provider.BaseColumns;

public final class EstructuraBBDD {
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS "+
                    Piloto.TABLE_NAME_PLAYLIST +
                    "(" + Piloto._ID + " integer PRIMARYKEY, " + Piloto.COLUMN_NAME_NOMBRE + " text," + Piloto.COLUMN_NAME_COCHE + " text);";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " +
                    Piloto.TABLE_NAME_PLAYLIST;

    public static final String SQL_CREATE_ENTRIE =
            "CREATE TABLE IF NOT EXISTS "+
                    Usuario.TABLE_NAME_PLAYLIST +
                    "(" + Usuario._ID + " integer PRIMARYKEY, " + Usuario.COLUMN_NAME_NOMBRE + " text," + Usuario.COLUMN_NAME_GRUPO + " text);";
    public static final String SQL_DELETE_ENTRI =
            "DROP TABLE IF EXISTS " +
                    Usuario.TABLE_NAME_PLAYLIST;

    public static final String SQL_CREATE_ENTRI =
            "CREATE TABLE IF NOT EXISTS "+
                    Copa.TABLE_NAME_PLAYLIST +
                    "(" + Copa._ID + " integer PRIMARYKEY, " + Copa.COLUMN_NAME_NOMBRE + " text," + Copa.COLUMN_NAME_GRUPO + " text);";
    public static final String SQL_DELETE_ENTR =
            "DROP TABLE IF EXISTS " +
                    Copa.TABLE_NAME_PLAYLIST;
    private EstructuraBBDD() {}
    /* Clase interna que define la estructura de la tabla de playlist
     */
    public static class Piloto implements BaseColumns {
        public static final String TABLE_NAME_PLAYLIST = "Piloto";
        public static final String COLUMN_NAME_NOMBRE= "Nombre";
        public static final String COLUMN_NAME_COCHE = "Coche";
    }
    public static class Usuario implements BaseColumns {
        public static final String TABLE_NAME_PLAYLIST = "Usuarios";
        public static final String COLUMN_NAME_NOMBRE = "Nombre";
        public static final String COLUMN_NAME_GRUPO = "Contraseña";
    }
    public static class Copa implements BaseColumns {
        public static final String TABLE_NAME_PLAYLIST = "Copa";
        public static final String COLUMN_NAME_NOMBRE = "Nombre";
        public static final String COLUMN_NAME_GRUPO = "Distancia";
    }
}