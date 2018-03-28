/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.plotter.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrador
 */
public final class Util {

    public static List<Data> getUniformRandomData(double lower, double upper, int count) {
        List<Data> listData = new ArrayList<Data>();
        Random r = new Random(System.currentTimeMillis());

        for (int i = 0; i < count; i++) {
            double value = (upper - lower) * r.nextDouble() + lower;
            listData.add(new Data(value));
        }
        return listData;
    }

    public static List<Data> getNormalRandomData(double media, double sd, int count) {
        List<Data> listData = new ArrayList<Data>();
        Random r = new Random(System.currentTimeMillis());

        for (int i = 0; i < count; i++) {
            double value = sd*r.nextGaussian() + media;
            listData.add(new Data(value));
        }
        return listData;
    }

}
