package com.example.proyectoandroid_luis_ruben;

public class Copa {
    public String nombre;
    public String distancia;


    public Copa(String nombre, String distancia) {
        this.nombre = nombre;
        this.distancia = distancia;
    }


    public String getNombre() {
        return nombre;
    }


    public String getDistancia() {
        return distancia;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }


    @Override
    public String toString() {
        return nombre + " - Distancia: " + distancia + " m";
    }
}