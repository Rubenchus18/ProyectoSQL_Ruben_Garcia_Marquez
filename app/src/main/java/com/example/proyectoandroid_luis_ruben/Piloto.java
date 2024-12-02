package com.example.proyectoandroid_luis_ruben;

public class Piloto {
   private  String coche;
    private  String nombrepiloto;

    public Piloto(String nombrepiloto, String coche) {
        this.nombrepiloto = nombrepiloto;
        this.coche=coche;
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
        return nombrepiloto + " - " + coche;
    }
}
