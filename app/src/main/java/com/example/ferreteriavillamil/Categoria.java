package com.example.ferreteriavillamil;

import org.json.JSONException;
import org.json.JSONObject;

public class Categoria {

    String nombre;
    int idCategoria;
    String imagen;


    public Categoria(JSONObject objetoJSON) throws JSONException {
        this.nombre = objetoJSON.getString("nombre");
        this.idCategoria = objetoJSON.getInt("idCategoria");
        this.imagen = objetoJSON.getString("imagen");
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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















}
