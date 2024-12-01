package com.example.proyectoandroid_luis_ruben;

public class Pilotos {
   private  String coche;
    private  String nombrepiloto;

    public Pilotos(String nombrepiloto) {
        this.nombrepiloto = nombrepiloto;
    }

    public String getCoche() {
        return coche;
    }

    public void setCoche(String coche) {
        this.coche = coche;
    }

    public String getNombrepiloto() {
        return nombrepiloto;
    }

    public void setNombrepiloto(String nombrepiloto) {
        this.nombrepiloto = nombrepiloto;
    }

    @Override
    public String toString() {
        return "Pilotos{" +
                "coche='" + coche + '\'' +
                ", nombrepiloto='" + nombrepiloto + '\'' +
                '}';
    }
}
