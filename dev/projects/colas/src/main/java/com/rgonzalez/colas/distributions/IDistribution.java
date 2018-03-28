/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.colas.distributions;

import com.rgonzalez.colas.exceptions.ParametersException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Interfaz que especifica el comportamiento
 * de cada distribucion de probabilidad a 
 * implementar.
 * @author Administrador
 */
public interface IDistribution {
   
    /**
     * El nombre de la distribucion.
     * @return El nombre de la distribucion.
     */
    public String getName();
    
    /**
     * Este es el valor de la distribucion
     * asociado a un numero aleatorio entre 0 y 1.
     * @return Un numero dentro el rango de la distribucion
     */
    public double getValue();
    
    /**
     * Los parametros de la distribucion.
     * @return Los parametros de la distribucion.
     */
    public Hashtable<String, Double> getParameters();
    
    /**
     * Devuelve el tipo de numero asociado a cada parametro.
     * Solo se implementaron dos tipos: numeros enteros (Integer)
     * o numeros reales (Double).
     * @return La hashtable con los tipos numeros de cada parametro.
     */
    public Hashtable<String, Class> getParametersClass();
    
    /**
     * Seteador de los parametros
     * @param parameters la hashtable de parametros.
     */
    public void setParameters(Hashtable<String, Double> parameters);
        
    /**
     * Una representacion en String de los parametros.
     * @return Una representacion en String de los parametros.
     */
    public String getStringParameters();
    
    /**
     * Retorna la lista de palabras claves para la hashtable
     * de parametros.
     * @return La lista de palabras claves.
     */
    public ArrayList<String> getKeys();
    
    /**
     * Chequea si la hashtable de parametros es valida
     * segun las condiciones de cada distribucion.
     * @param par La hashtable de parametros a testear.
     * @throws exceptions.ParametersException En caso de que se encuentren errores en los parametros.
     */
    public void checkParameters(Hashtable<String, Double> par) throws ParametersException;
}
