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
public class UniformeContinua extends AbstractDistribution {
    
    private final String INFERIOR = "Limite Inferior";
    private final String SUPERIOR = "Limite Superior";
    private double a;
    private double b;
    
    public UniformeContinua(){
        this.a = 0;
        this.b = 3;
        this.parameters.put(INFERIOR, a);
        this.parameters.put(SUPERIOR, b);
        this.parametersClass.put(INFERIOR, Double.class);
        this.parametersClass.put(SUPERIOR, Double.class);
        this.keys.add(INFERIOR);
        this.keys.add(SUPERIOR);
    }

    public String getName() {
        return "Uniforme Continua";
    }
    
    public double getA(){
        return this.a;
    }
    
    public double getB(){
        return this.b;
    }
    
    public void setA(double a){
        this.a = a;
        this.parameters.put(INFERIOR, a);
    }
    
    public void setB(double b){
        this.b = b;
        this.parameters.put(SUPERIOR, b);
    }

    public double getValue() {
        return a+((b-a)*Generator.random());
    }

    public void checkParameters(Hashtable<String, Double> par) throws ParametersException {
        double aTest = par.get(INFERIOR);
        double bTest = par.get(SUPERIOR);
        
        if( aTest<= 0)
            throw new ParametersException("El limite inferior debe ser mayor que 0");
        
        if( bTest<=aTest)
            throw new ParametersException("El limite superior debe ser mayor que el limite inferior");
    }
}
