package com.example.ferreteriavillamil;

import java.io.Serializable;

public class ListElementProducto implements Serializable {

    public String name;
    public String precio;
    public int idProducto;
    public int idCategoria;
    public int cantidad;
    public String imagen;
    public String color2;


    public ListElementProducto(String name, String precio, int idProducto, int idCategoria, int cantidad, String imagen, String color2) {
        this.name = name;
        this.precio = precio;
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.cantidad = cantidad;
        this.imagen = imagen;
        this.color2 = color2;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
