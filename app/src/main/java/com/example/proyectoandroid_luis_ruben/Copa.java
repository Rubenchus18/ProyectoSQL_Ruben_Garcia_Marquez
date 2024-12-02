package com.example.proyectoandroid_luis_ruben;

public class Copa {

    private String nombre;
    private int id, distancia;

    public Copa(String nombre){
        this.nombre=nombre;
        this.id=0;
        this.distancia=(int)(Math.random()*1000);
    }//Copa

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        return id + "  - " + nombre + " | " + distancia + " M";
    }
}
