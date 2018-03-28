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
public class BinomialNegativa extends AbstractDistribution {

    private final String MUESTRAS = "Numero de Muestras";
    private final String PROBABILIDAD = "Probabilidad de Exito";
    private int n;
    private double p;

    public BinomialNegativa() {
        this.n = 1;
        this.p = 0.5;
        this.parameters.put(MUESTRAS, (double) n);
        this.parameters.put(PROBABILIDAD, p);
        this.parametersClass.put(MUESTRAS, Integer.class);
        this.parametersClass.put(PROBABILIDAD, Double.class);
        this.keys.add(MUESTRAS);
        this.keys.add(PROBABILIDAD);
    }

    public String getName() {
        return "Binomial Negativa";
    }

    public int getN() {
        return this.n;
    }

    public double getP() {
        return this.p;
    }

    public void setN(double n) throws ParametersException {
        this.parameters.put(MUESTRAS, n);
        this.n = (int) n;
    }

    public void setP(double p){
        this.parameters.put(PROBABILIDAD, p);
        this.p = p;
    }

    public double getValue() {
        int cont = 0;
        int j = 0;
        do {
            if ( Generator.random() <= p) {
                j++;
            } else {
                cont++;
            }
        } while (j < n);
        return cont;
    }

    public void checkParameters(Hashtable<String, Double> par) throws ParametersException {
        int nTest = (int) par.get(MUESTRAS).doubleValue();
        double pTest = par.get(PROBABILIDAD);
        
        if( nTest<=0 )
            throw new ParametersException("El numero de muestras debe ser mayor que 0");
        
        if( pTest<=0 || pTest>=1)
            throw new ParametersException("La probabilidad debe ser mayor que 0 y menor que 1");
        
    }
}
