package com.albornoz.inmobiliariaandroid.modelo;

import java.io.Serializable;
import java.util.Date;

public class Pago implements Serializable {

    private int id;
    private int numero;
    private Contrato contrato;
    private double monto;
    //private String fecha;
    private Date fecha;

    public Pago() {}

    public Pago(int id, int numero, Contrato contrato, double monto, Date fecha) {
        this.id = id;
        this.numero = numero;
        this.contrato = contrato;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
