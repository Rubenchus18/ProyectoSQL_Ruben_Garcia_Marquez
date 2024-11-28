package com.example.proyectoandroid_luis_ruben;

public class PonerListView {
    private String nombre;       // Nombre de la copa
    private String kilometros;   // Kilómetros (si es relevante)
    private int imagenResId;     // ID del recurso de la imagen

    // Constructor
    public PonerListView(String nombre, int imagenResId, String kilometros) {
        this.nombre = nombre;
        this.imagenResId = imagenResId;
        this.kilometros = kilometros;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getImagenResId() {
        return imagenResId;
    }

    public String getKilometros() {
        return kilometros;
    }
}
