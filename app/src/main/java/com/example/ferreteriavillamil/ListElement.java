package com.example.ferreteriavillamil;

import java.io.Serializable;

public class ListElement implements Serializable {


    public String color;
    public String name;
    public String correo;
    public int idUsuario;
    public int identificacion;
    public String imagen;
    public String color2;

    public ListElement(String color, String name, String correo, int idUsuario, int identificacion, String imagen, String color2) {
        this.color = color;
        this.name = name;
        this.correo = correo;
        this.idUsuario = idUsuario;
        this.identificacion = identificacion;
        this.imagen = imagen;
        this.color2 = color2;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getCorreo() {
        return correo;
    }


    public void setCorreo(String correo) {
        this.name = correo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
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
