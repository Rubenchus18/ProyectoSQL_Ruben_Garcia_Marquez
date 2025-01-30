package com.example.proyectoandroid_luis_ruben;

public class Piloto {
    private String nombrepiloto;
    private String coche;


    public Piloto(String nombrepiloto, String coche) {
        this.nombrepiloto = nombrepiloto;
        this.coche = coche;
    }


    public String getNombrepiloto() {
        return nombrepiloto;
    }


    public void setNombrepiloto(String nombrepiloto) {
        this.nombrepiloto = nombrepiloto;
    }


    public String getCoche() {
        return coche;
    }


    public void setCoche(String coche) {
        this.coche = coche;
    }


    @Override
    public String toString() {
        return nombrepiloto + " - " + coche;
    }
}