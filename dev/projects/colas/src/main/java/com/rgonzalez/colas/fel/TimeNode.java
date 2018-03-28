/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.colas.fel;

/**
 * Clase que sirve para almacenar los datos asociados
 * a cada suceso en el sistema. Solo contiene los atributos
 * y sus respectivos getters y setters.
 * @author Administrador
 */
public class TimeNode {

    private double tiempoEntreLlegadas;
    private double tiempoDeLlegada;
    private double tiempoDeServicio;
    private double tiempoEnElSistema;
    private double tiempoDeSalida;
    private double tiempoEnCola;

    public double getTiempoEnCola() {
        return tiempoEnCola;
    }

    public void setTiempoEnCola(double tiempoEnCola) {
        this.tiempoEnCola = tiempoEnCola;
    }

    public double getTiempoEntreLlegadas() {
        return tiempoEntreLlegadas;
    }

    public void setTiempoEntreLlegadas(double tiempoEntreLlegadas) {
        this.tiempoEntreLlegadas = tiempoEntreLlegadas;
    }

    public double getTiempoDeLlegada() {
        return tiempoDeLlegada;
    }

    public void setTiempoDeLlegada(double tiempoDeLlegada) {
        this.tiempoDeLlegada = tiempoDeLlegada;
    }

    public double getTiempoDeSalida() {
        return tiempoDeSalida;
    }

    public void setTiempoDeSalida(double tiempoDeSalida) {
        this.tiempoDeSalida = tiempoDeSalida;
    }

    public double getTiempoDeServicio() {
        return tiempoDeServicio;
    }

    public void setTiempoDeServicio(double tiempoDeServicio) {
        this.tiempoDeServicio = tiempoDeServicio;
    }

    public double getTiempoEnElSistema() {
        return tiempoEnElSistema;
    }

    public void setTiempoEnElSistema(double tiempoEnElSistema) {
        this.tiempoEnElSistema = tiempoEnElSistema;
    }
}
