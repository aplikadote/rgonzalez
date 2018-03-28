/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.relpro.objectdb.test;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author rgonzalez
 */
@Entity
public class Tuple {

    @Id @GeneratedValue
    private long id;
    
    private Date inicio;
    private double duracion;
    private String tipo;
    private String gplan;
    private double costo;
    private String equipo;
    private String sintoma;
    private String modo;
    private String causa;
    private String orden;
    private double impacto;
    private boolean detSys;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGplan() {
        return gplan;
    }

    public void setGplan(String gplan) {
        this.gplan = gplan;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getSintoma() {
        return sintoma;
    }

    public void setSintoma(String sintoma) {
        this.sintoma = sintoma;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public double getImpacto() {
        return impacto;
    }

    public void setImpacto(double impacto) {
        this.impacto = impacto;
    }

    public boolean isDetSys() {
        return detSys;
    }

    public void setDetSys(boolean detSys) {
        this.detSys = detSys;
    }
    
    
}
