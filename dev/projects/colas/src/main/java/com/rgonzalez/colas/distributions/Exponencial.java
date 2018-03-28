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
public class Exponencial extends AbstractDistribution{

    private final String LAMBDA = "Lambda";
    private double lambda;
    
    public Exponencial(){
        this.lambda = 1;
        this.parameters.put(LAMBDA,lambda);
        this.parametersClass.put(LAMBDA, Double.class);
        this.keys.add(LAMBDA);
    }
    
    public double getLambda(){
        return this.lambda;
    }
    
    public void setLambda(double lambda){
        this.lambda = lambda;
        this.parameters.put(LAMBDA,lambda);
    }
    
    public String getName() {
        return "Exponencial";
    }

    public double getValue() {
        return (-1/lambda)*Math.log(1-Generator.random());
    }

    public void checkParameters(Hashtable<String,Double> par) throws ParametersException {
        double lambdaTest = par.get(LAMBDA);
        
        if(lambdaTest<=0)
            throw new ParametersException("Lambda debe ser un real positivo");
    }

}
