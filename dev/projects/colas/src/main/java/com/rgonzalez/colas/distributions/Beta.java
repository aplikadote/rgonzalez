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
public class Beta extends AbstractDistribution {

    private final String ALPHA = "Alpha";
    private final String BETA = "Beta";
    private double alpha;
    private double beta;

    public Beta() {
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
        return "Beta";
    }

    public double getValue() {
        double rand1 = Generator.random();
        double rand2 = 0;
        for (int i=0; i< (alpha + beta + 1); i++) {
            rand2 = Generator.random();
            if (rand1 > rand2) {
                rand1 = rand2;
            }
        }
        return rand2;
    }

    public void checkParameters(Hashtable<String, Double> par) throws ParametersException {
        double alphaTest = par.get(ALPHA).doubleValue();
        double betaTest = par.get(BETA).doubleValue();
        
        if (alphaTest <= 0) 
            throw new ParametersException("Alpha debe ser un real positivo");
        
        if (betaTest <= 0)
            throw new ParametersException("Beta debe ser un real positivo");
        
    }

}
