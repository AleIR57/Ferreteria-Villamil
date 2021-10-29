package com.example.ferreteriavillamil;

import org.json.JSONException;
import org.json.JSONObject;

public class Producto {

    String nombre, marca, descripcion, precio, imagen;
    int idProducto, idCategoria, cantidad;

    public Producto(JSONObject objetoJSON) throws JSONException {
        this.nombre = objetoJSON.getString("nombre");
        this.marca = objetoJSON.getString("marca");
        this.descripcion = objetoJSON.getString("descripcion");
        this.imagen = objetoJSON.getString("imagen");
        this.idProducto = objetoJSON.getInt("idProducto");
        this.idCategoria = objetoJSON.getInt("idCategoria");
        this.cantidad = objetoJSON.getInt("cantidad");
        this.precio = objetoJSON.getString("precio");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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



}
