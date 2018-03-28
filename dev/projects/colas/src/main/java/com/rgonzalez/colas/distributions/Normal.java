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
public class Normal extends AbstractDistribution {

    private final String MEDIA = "Media";
    private final String DESVIACION = "Desviacion Estandar";
    private double mu;
    private double sigma;

    public Normal() {
        this.mu = 1;
        this.sigma = 1;
        this.parameters.put(MEDIA, mu);
        this.parameters.put(DESVIACION, sigma);
        this.parametersClass.put(MEDIA, Double.class);
        this.parametersClass.put(DESVIACION, Double.class);
        this.keys.add(MEDIA);
        this.keys.add(DESVIACION);
    }

    public double getMu() {
        return this.mu;
    }

    public double getSigma() {
        return this.sigma;
    }

    public void setMu(double mu) {
        this.mu = mu;
        this.parameters.put(MEDIA, mu);
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
        this.parameters.put(DESVIACION, sigma);
    }

    public String getName() {
        return "Normal";
    }

    public double getValue() {
        double suma = 0;
        for (int i = 0; i < 12; i++)
            suma += Generator.random();
        
        return Math.abs(mu + sigma * (suma - 6));
    }

    public void checkParameters(Hashtable<String, Double> par) throws ParametersException {
        double muTest = par.get(MEDIA);
        double sigmaTest = par.get(DESVIACION);
        
        if (muTest <= 0) {
            throw new ParametersException("La Media debe ser un real positivo");
        }
        if (sigmaTest <= 0) {
            throw new ParametersException("La Desviacion Estandar debe ser un real positivo");
        }

    }
}
