package com.example.proyectoandroid_luis_ruben;

import androidx.annotation.NonNull;

public class Usuario {

    private String nombre, contraseña;

    public Usuario(String nombre, String contraseña){
        this.nombre=nombre;
        this.contraseña=contraseña;
    }//Usuario

    public Usuario(){

    }//Usuario

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }//toString

}//Usuario