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
public class Hipergeometrica extends AbstractDistribution {

    private final String PIEZAS_LOTE = "Piezas del lote";
    private final String PIEZAS_DEFECTUOSAS = "Piezas defectuosas";
    private final String MUESTRA = "Tamaño de la muestra";
    private int n;
    private int m;
    private int t;

    public Hipergeometrica() {
        this.n = 4;
        this.m = 3;
        this.t = 2;
        this.parameters.put(PIEZAS_LOTE, (double) n);
        this.parameters.put(PIEZAS_DEFECTUOSAS, (double) m);
        this.parameters.put(MUESTRA, (double) t);
        this.parametersClass.put(PIEZAS_LOTE, Integer.class);
        this.parametersClass.put(PIEZAS_DEFECTUOSAS, Integer.class);
        this.parametersClass.put(MUESTRA, Integer.class);
        this.keys.add(PIEZAS_LOTE);
        this.keys.add(PIEZAS_DEFECTUOSAS);
        this.keys.add(MUESTRA);
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getT() {
        return t;
    }

    public void setN(int n) {
        this.n = n;
        this.parameters.put(PIEZAS_LOTE,(double) n);
    }

    public void setM(int m) {
        this.m = m;
        this.parameters.put(PIEZAS_DEFECTUOSAS,(double) m);
    }

    public void setT(int t) {
        this.t = t;
        this.parameters.put(MUESTRA, (double) t);
    }

    public String getName() {
        return "Hipergeometrica";
    }

    public double getValue() {
        int cont = 0;
        int p = n;
        int l = m;
        for(int i=0; i < n; i++) {
            if (Generator.random() <= (l/p)) {
                cont++;
                p--;
                l--;
            } else {
                p--;
            }
        }
        return cont;
    }

    public void checkParameters(Hashtable<String, Double> par) throws ParametersException {
        int nTest = (int) par.get(PIEZAS_LOTE).doubleValue();
        int mTest = (int) par.get(PIEZAS_DEFECTUOSAS).doubleValue();
        int tTest = (int) par.get(MUESTRA).doubleValue();
        
        if (nTest <= 0) {
            throw new ParametersException("La cantidad de piezas del lote debe ser mayor que 0");
        }
        if (mTest <= 0) {
            throw new ParametersException("La cantidad de piezas defectuosas debe ser mayor que 0");
        }
        if (mTest >= nTest) {
            throw new ParametersException("La cantidad de piezas defectuosas debe ser menor que la cantidad de piezas del lote");
        }
        if (tTest <= 0) {
            throw new ParametersException("El tamaño de la muestra debe ser mayor que 0");
        }
        if (tTest >= nTest) {
            throw new ParametersException("El tamaño de la muestra debe ser debe ser menor que la cantidad de piezas del lote");
        }
    }
}
