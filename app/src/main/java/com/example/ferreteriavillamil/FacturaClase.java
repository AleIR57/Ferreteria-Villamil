package com.example.ferreteriavillamil;

import org.json.JSONException;
import org.json.JSONObject;

public class FacturaClase {

    String nombreUsuario, nombreProducto, fecha, marca, precio, cantidad, precioTotal, cantidadTotal;
    int idFactura, idVenta;

    public FacturaClase(JSONObject objetoJSON) throws JSONException {
        this.nombreUsuario = objetoJSON.getString("nombreUsuario");
        this.nombreProducto = objetoJSON.getString("nombreProducto");
        this.fecha = objetoJSON.getString("fecha");
        this.marca = objetoJSON.getString("marca");
        this.precio = objetoJSON.getString("precio");
        this.cantidad = objetoJSON.getString("cantidad");
        this.cantidadTotal = objetoJSON.getString("cantidadTotal");
        this.precioTotal = objetoJSON.getString("precioTotal");
        this.idFactura = objetoJSON.getInt("idFactura");
        this.idVenta = objetoJSON.getInt("idVenta");
    }



    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(String precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(String cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }



}
