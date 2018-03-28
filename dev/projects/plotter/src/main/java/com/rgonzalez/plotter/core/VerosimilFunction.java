/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.plotter.core;

import java.util.List;

/**
 *
 * @author Administrador
 */
public class VerosimilFunction implements IFunction {

    private IFunction function;
    private List<Data> listData;

    public VerosimilFunction() {
        this.function = new GaussianFunction(0, 1);
        this.listData = Util.getUniformRandomData(-Math.PI, Math.PI, 2);
    }

    public VerosimilFunction(List<Data> listData) {
        this.function = new GaussianFunction(0, 1);
        this.listData = listData;
    }

    public VerosimilFunction(IFunction function, List<Data> listData) {
        this.function = function;
        this.listData = listData;
    }

    public String getName() {
        return "Verosimil";
    }

    public double getValue(double x) {

        double value = 1;
        for (int i = 0; i < listData.size(); i++) {
            value *= function.getValue( listData.get(i).getValue() - x )*10;
        }
        return value;
    }
}
