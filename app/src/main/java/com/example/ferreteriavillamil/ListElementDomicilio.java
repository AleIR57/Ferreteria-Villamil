package com.example.ferreteriavillamil;

import java.io.Serializable;

public class ListElementDomicilio implements Serializable {

    public int idDomicilio;
    public int idUsuario;
    public int idFactura;
    public String ciudad;
    public String barrio;
    public String direccion;
    public String latitud;
    public String longitud;
    public String estado;
    public String color2;

    public ListElementDomicilio(int idDomicilio, int idUsuario, int idFactura, String ciudad, String barrio, String direccion, String latitud, String longitud, String estado, String color2) {
        this.idDomicilio = idDomicilio;
        this.idUsuario = idUsuario;
        this.idFactura = idFactura;
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
        this.color2 = color2;
    }

    public int getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(int idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }




}
