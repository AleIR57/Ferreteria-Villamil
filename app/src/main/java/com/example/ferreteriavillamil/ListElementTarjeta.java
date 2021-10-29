package com.example.ferreteriavillamil;

import java.io.Serializable;

public class ListElementTarjeta implements Serializable {



    public String nombreTitular;
    String imagen;
    public int idTarjeta;
    public String numeroTarjeta;
    public String codigoCVC;
    public String fechaExpiracion;
    public String nombreEntidadBancaria;


    public ListElementTarjeta(String nombreTitular, String imagen, int idTarjeta, String numeroTarjeta, String codigoCVC, String fechaExpiracion, String nombreEntidadBancaria) {
        this.nombreTitular = nombreTitular;
        this.imagen = imagen;
        this.idTarjeta = idTarjeta;
        this.numeroTarjeta = numeroTarjeta;
        this.codigoCVC = codigoCVC;
        this.fechaExpiracion = fechaExpiracion;
        this.nombreEntidadBancaria = nombreEntidadBancaria;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCodigoCVC() {
        return codigoCVC;
    }

    public void setCodigoCVC(String codigoCVC) {
        this.codigoCVC = codigoCVC;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getNombreEntidadBancaria() {
        return nombreEntidadBancaria;
    }

    public void setNombreEntidadBancaria(String nombreEntidadBancaria) {
        this.nombreEntidadBancaria = nombreEntidadBancaria;
    }






}
