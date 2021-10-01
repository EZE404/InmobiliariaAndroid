package com.albornoz.inmobiliariaandroid.modelo;

import java.io.Serializable;

public class Inquilino implements Serializable {

    private int idInquilino;
    private Long DNI;
    private String nombre;
    private String apellido;
    private String lugarDeTrabajo;
    private String email;
    private String telefono;
    private String nombreGarante;
    private String telefonoGarante;

    public Inquilino() {}

    public Inquilino(int idInquilino, Long DNI, String nombre, String apellido, String lugarDeTrabajo, String email, String telefono, String nombreGarante, String telefonoGarante) {
        this.idInquilino = idInquilino;
        this.DNI = DNI;
        this.nombre = nombre;
        this.apellido = apellido;
        this.lugarDeTrabajo = lugarDeTrabajo;
        this.email = email;
        this.telefono = telefono;
        this.nombreGarante = nombreGarante;
        this.telefonoGarante = telefonoGarante;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public Long getDNI() {
        return DNI;
    }

    public void setDNI(Long DNI) {
        this.DNI = DNI;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getLugarDeTrabajo() {
        return lugarDeTrabajo;
    }

    public void setLugarDeTrabajo(String lugarDeTrabajo) {
        this.lugarDeTrabajo = lugarDeTrabajo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreGarante() {
        return nombreGarante;
    }

    public void setNombreGarante(String nombreGarante) {
        this.nombreGarante = nombreGarante;
    }

    public String getTelefonoGarante() {
        return telefonoGarante;
    }

    public void setTelefonoGarante(String telefonoGarante) {
        this.telefonoGarante = telefonoGarante;
    }
}
