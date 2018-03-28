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
public class Gamma extends AbstractDistribution {

    private final String ALPHA = "Alpha";
    private final String BETA = "Beta";
    private double alpha;
    private double beta;

    public Gamma() {
        this.alpha = 1;
        this.beta = 1;
        this.parameters.put(ALPHA, alpha);
        this.parameters.put(BETA, beta);
        this.parametersClass.put(ALPHA, Double.class);
        this.parametersClass.put(BETA, Double.class);
        this.keys.add(ALPHA);
        this.keys.add(BETA);
    }

    public double getAlpha() {
        return this.alpha;
    }

    public double getBeta() {
        return this.beta;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
        this.parameters.put(ALPHA, alpha);
    }

    public void setBeta(double beta) {
        this.beta = beta;
        this.parameters.put(BETA, beta);
    }

    public String getName() {
        return "Gamma";
    }

    public double getValue() {
        double prod = 1;
        for (int i=0; i < beta; i++)
            prod *= Generator.random();
        
        return -Math.log(prod);
    }

    public void checkParameters(Hashtable<String,Double> par) throws ParametersException {
        double alphaTest = par.get(ALPHA).doubleValue();
        double betaTest = par.get(BETA).doubleValue();
        
        if (alphaTest <= 0) {
            throw new ParametersException("Alpha debe ser un real positivo");
        }
        if (betaTest <= 0) {
            throw new ParametersException("Beta debe ser un real positivo");
        }
    }
}
