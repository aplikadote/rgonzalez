/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.colas.distributions;

/**
 * Esta clase simplemente genera un numero aleatorio. Esto se implementa en el
 * metodo random.
 *
 * @author Administrador
 */
public final class Generator {

    public static double semilla = 1000;

    /**
     * Devuelve un numero aleatorio entre 0 y 1.
     *
     * @return un numero aleatorio entre 0 y 1.
     */
    public static final double random() {
        semilla = ((Math.pow(7, 5) * semilla) % ((Math.pow(2, 31) - 1)));
        return (semilla / (Math.pow(2, 31) - 1));
    }
}
