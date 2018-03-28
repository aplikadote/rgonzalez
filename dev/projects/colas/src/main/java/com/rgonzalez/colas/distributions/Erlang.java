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
public class Erlang extends AbstractDistribution {

    private final String FORMA = "Forma";
    private final String RAZON = "Razon";
    private int k;
    private double lambda;

    public Erlang() {
        this.k = 1;
        this.lambda = 1;
        this.parameters.put(FORMA, (double) k);
        this.parameters.put(RAZON, lambda);
        this.parametersClass.put(FORMA, Integer.class);
        this.parametersClass.put(RAZON, Double.class);
        this.keys.add(FORMA);
        this.keys.add(RAZON);
    }

    public String getName() {
        return "Erlang";
    }

    public int getK() {
        return k;
    }

    public double getLambda() {
        return lambda;
    }

    public void setK(int k) {
        this.k = k;
        this.parameters.put(FORMA, (double) k);
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
        this.parameters.put(RAZON, lambda);
    }

    public double getValue() {
        double prod = 1;
        //realiza la extracction de lo m primeros numeros aleatorios, para ejecutar la productoria
        for (int i = 0; i < k; i++) {
            prod *= Generator.random();
        }

        return (-1 / (1 / lambda)) * Math.log(prod);	//metodo inverso
    }

    public void checkParameters(Hashtable<String, Double> par) throws ParametersException {
        int kTest = (int) par.get(FORMA).doubleValue();
        double lambdaTest = par.get(RAZON);

        if (kTest <= 0) {
            throw new ParametersException("La Forma debe ser un entero positivo");
        }
        if (lambdaTest <= 0) {
            throw new ParametersException("La Razon debe ser un real positivo");
        }
    }
}
