package com.example.ferreteriavillamil;

import java.io.Serializable;

public class ListElementCategoria implements Serializable {

    public String name;
    public int idCategoria;
    public String imagen;
    public String color2;

    public ListElementCategoria(String name, int idCategoria, String imagen, String color2) {

        this.name = name;
        this.idCategoria = idCategoria;
        this.imagen = imagen;
        this.color2 = color2;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }





}
