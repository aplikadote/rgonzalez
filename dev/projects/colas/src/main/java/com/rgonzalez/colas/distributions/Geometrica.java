/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.colas.distributions;

import com.rgonzalez.colas.exceptions.ParametersException;
import java.util.Hashtable;

/**
 *
 * @author Administrador
 */
public class Geometrica extends AbstractDistribution {
    
    private final String PROBABILIDAD = "Probabilidad de Exito";
    private double p;

    public Geometrica(){
        this.p = 0.5;
        this.parameters.put(PROBABILIDAD, p);
        this.parametersClass.put(PROBABILIDAD, Double.class);
        this.keys.add(PROBABILIDAD);
    }
    
    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
        this.parameters.put(PROBABILIDAD, p);
    }
    
    public String getName() {
        return "Geometrica";
    }

    public double getValue() {
        return Math.ceil(Math.log(1-Generator.random())/Math.log(1-p));
    }

    public void checkParameters(Hashtable<String, Double> par) throws ParametersException {
        double pTest = par.get(PROBABILIDAD).doubleValue();
        
        if( pTest<=0 || pTest>=1)
            throw new ParametersException("La probabilidad debe ser mayor que 0 y menor que 1");
    }

}
