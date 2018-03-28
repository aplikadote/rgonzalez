/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.colas.distributions;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Se implementan algunos metodos de la interfaz IDistribution comunes para
 * todas las distribuciones.
 *
 * @author Administrador
 */
public abstract class AbstractDistribution implements IDistribution {

    public Hashtable<String, Double> parameters = new Hashtable<String, Double>();
    public Hashtable<String, Class> parametersClass = new Hashtable<String, Class>();
    public ArrayList<String> keys = new ArrayList<String>();

    public Hashtable<String, Double> getParameters() {
        return this.parameters;
    }

    public Hashtable<String, Class> getParametersClass() {
        return this.parametersClass;
    }

    public void setParameters(Hashtable<String, Double> parameters) {
        this.parameters = parameters;
    }

    public String toString() {
        return this.getName();
    }

    public String getStringParameters() {
        String aux = "( ";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value;

            if (this.parametersClass.get(key) == Double.class) {
                value = this.parameters.get(key).toString();
            } else {
                value = Integer.toString((int) this.parameters.get(key).doubleValue());
            }

            aux = aux + value;

            if (i < keys.size() - 1) {
                aux = aux + " , ";
            }
        }
        return aux + " )";
    }

    public ArrayList<String> getKeys() {
        return this.keys;
    }
}
