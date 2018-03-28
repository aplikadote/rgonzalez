
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
public class Poisson extends AbstractDistribution {

    private final String LAMBDA = "Lambda";
    private double lambda;

    public Poisson() {
        this.lambda = 1;
        this.parameters.put(LAMBDA, lambda);
        this.parametersClass.put(LAMBDA, Double.class);
        this.keys.add(LAMBDA);
    }

    public String getName() {
        return "Poisson";
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
        this.parameters.put(LAMBDA, lambda);
    }

    public double getValue() {
        int cont = 0;
        double prod = 1;
        double lim = Math.pow(Math.E, -lambda);

        while (prod >= lim) {
            prod *= Generator.random();
            cont++;
        }
        return cont;
    }
    
    public void checkParameters(Hashtable<String, Double> par) throws ParametersException {
        double lambdaTest = par.get(LAMBDA);
        
        if( lambdaTest<=0 ){
            throw new ParametersException("Lambda debe ser mayor que cero");
        }
    }
}
