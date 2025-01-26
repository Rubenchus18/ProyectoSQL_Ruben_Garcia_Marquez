package com.example.proyectoandroid_luis_ruben;

public class PuntosTotales {
    private String nombrePiloto;
    private String coche;
    private int puntos;

    public PuntosTotales(String nombrePiloto, String coche, int puntos) {
        this.nombrePiloto = nombrePiloto;
        this.coche = coche;
        this.puntos = puntos;
    }

    public String getNombrePiloto() {
        return nombrePiloto;
    }

    public String getCoche() {
        return coche;
    }

    public int getPuntos() {
        return puntos;
    }
}