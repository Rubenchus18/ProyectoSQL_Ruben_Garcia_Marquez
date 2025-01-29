package com.example.proyectoandroid_luis_ruben;

public class Piloto {
    private String nombrepiloto; // Nombre del piloto
    private String coche;         // Coche del piloto

    // Constructor
    public Piloto(String nombrepiloto, String coche) {
        this.nombrepiloto = nombrepiloto;
        this.coche = coche;
    }

    // Getter para el nombre del piloto
    public String getNombrepiloto() {
        return nombrepiloto;
    }

    // Setter para el nombre del piloto
    public void setNombrepiloto(String nombrepiloto) {
        this.nombrepiloto = nombrepiloto;
    }

    // Getter para el coche del piloto
    public String getCoche() {
        return coche;
    }

    // Setter para el coche del piloto
    public void setCoche(String coche) {
        this.coche = coche;
    }
}