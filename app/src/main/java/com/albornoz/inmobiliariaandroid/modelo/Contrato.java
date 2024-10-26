package com.albornoz.inmobiliariaandroid.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Contrato implements Serializable {

    private int id;
    private Date desde;
    private Date hasta;
    private double monto;
    private Inquilino inquilino;
    private Inmueble inmueble;
    private String nombreGarante;
    private String dniGarante;
    private String telefonoGarante;
    private String emailGarante;


    public Contrato() {
    }

    public Contrato(int id, Date desde, Date hasta, double monto, Inquilino inquilino, Inmueble inmueble, String nombreGarante, String dniGarante, String telefonoGarante, String emailGarante) {
        this.id = id;
        this.desde = desde;
        this.hasta = hasta;
        this.monto = monto;
        this.inquilino = inquilino;
        this.inmueble = inmueble;
        this.nombreGarante = nombreGarante;
        this.dniGarante = dniGarante;
        this.telefonoGarante = telefonoGarante;
        this.emailGarante = emailGarante;
    }

    public String getNombreGarante() {
        return nombreGarante;
    }

    public void setNombreGarante(String nombreGarante) {
        this.nombreGarante = nombreGarante;
    }

    public String getDniGarante() {
        return dniGarante;
    }

    public void setDniGarante(String dniGarante) {
        this.dniGarante = dniGarante;
    }

    public String getTelefonoGarante() {
        return telefonoGarante;
    }

    public void setTelefonoGarante(String telefonoGarante) {
        this.telefonoGarante = telefonoGarante;
    }

    public String getEmailGarante() {
        return emailGarante;
    }

    public void setEmailGarante(String emailGarante) {
        this.emailGarante = emailGarante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }


    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return id == contrato.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
