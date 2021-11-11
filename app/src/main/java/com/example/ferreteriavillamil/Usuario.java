
package com.example.ferreteriavillamil;
import org.json.JSONException;
import org.json.JSONObject;


public class Usuario {


    String nombre, correo, contrasena, direccion, imagen;
    int idUsuario, idRol, telefono, identificacion;

    public Usuario(JSONObject objetoJSON) throws JSONException {
        this.nombre = objetoJSON.getString("nombre");
        this.correo = objetoJSON.getString("correo");
        this.contrasena = objetoJSON.getString("contrasena");
        this.direccion = objetoJSON.getString("direccion");
        this.idUsuario = objetoJSON.getInt("idUsuario");
        this.idRol = objetoJSON.getInt("idRol");
        this.telefono = objetoJSON.getInt("telefono");
        this.identificacion = objetoJSON.getInt("identificacion");
        this.imagen = objetoJSON.getString("imagen");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contraseña) {
        this.contrasena = contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }


    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
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



}
