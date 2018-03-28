/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.dataset;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rene
 */
public class MinMaxNormalization implements Normalization {

    private Dataset realDataset;
    private Dataset normalizedDataset;
    private double[] minX;
    private double[] maxX;
    private double minY;
    private double maxY;

    public MinMaxNormalization(Dataset dataset) {
        this.realDataset = dataset;
        int dataCount = dataset.getDataCount();
        int xDataSize = dataset.getXDataSize();
        this.minX = new double[xDataSize];
        this.maxX = new double[xDataSize];

        List<RegresionInstance> normalizedDataList = new ArrayList<RegresionInstance>(dataCount);
        List<RegresionInstance> realDataList = dataset.getDataList();
        for (int i = 0; i < dataCount; i++) {
            RegresionInstance instance = realDataList.get(i);
            double[] xInstace = instance.getX();
            double[] x = new double[xInstace.length];
            for (int j = 0; j < xInstace.length; j++) {
                x[j] = xInstace[j];
            }
            double y = instance.getY();

            normalizedDataList.add(new RegresionInstance(x,y));
        }
        for (int j = 0; j < xDataSize; j++) {
            minX[j] = Double.POSITIVE_INFINITY;
            maxX[j] = Double.NEGATIVE_INFINITY;
            for (int i = 0; i < dataCount; i++) {
                double comp = normalizedDataList.get(i).getX()[j];
                if (Double.compare(comp, minX[j]) < 0) {
                    minX[j] = comp;
                }
                if (Double.compare(comp, minX[j]) > 0) {
                    maxX[j] = comp;
                }
            }
        }
        for (int j = 0; j < xDataSize; j++) {
            for (int i = 0; i < dataCount; i++) {
                if (Double.compare(maxX[j], minX[j]) == 0) {
                    normalizedDataList.get(i).getX()[j] = maxX[j];
                } else {
                    double comp = normalizedDataList.get(i).getX()[j];
                    normalizedDataList.get(i).getX()[j] = ((2 / (maxX[j] - minX[j])) * (comp - minX[j])) - 1;
                }
            }
        }
        minY = Double.POSITIVE_INFINITY;
        maxY = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < dataCount; i++) {
            double comp = normalizedDataList.get(i).getY();
            if (comp < minY) {
                minY = comp;
            }
            if (comp > maxY) {
                maxY = comp;
            }
        }
        for (int i = 0; i < dataCount; i++) {
            RegresionInstance sample = normalizedDataList.get(i);
            if (Double.compare(maxY, minY) == 0) {
                normalizedDataList.get(i).setY(maxY);
            } else {
                double comp = sample.getY();
                sample.setY(((2 / (maxY - minY)) * (comp - minY)) - 1);
            }
        }

        this.normalizedDataset = new DefaultDataset();
        this.normalizedDataset.setDataList(normalizedDataList);
        this.normalizedDataset.setDataCount(dataCount);
        this.normalizedDataset.setXDataSize(xDataSize);

    }

    public Dataset getNormalizedDataset() {
        return normalizedDataset;
    }

    public RegresionInstance normalice(RegresionInstance instance) {
        double[] xInstance = instance.getX();
        double[] x = new double[xInstance.length];
        double yInstance = instance.getY();

        for (int i = 0; i < x.length; i++) {
            x[i] = ((2 / (maxX[i] - minX[i])) * (xInstance[i] - minX[i])) - 1;
        }
        double y = ((2 / (maxY - minY)) * (yInstance - minY)) - 1;

        return new RegresionInstance(x, y);
    }

    public double desnormalice(double normalicedY) {
        return ((normalicedY + 1) / (2 / (maxY - minY))) + minY;
    }
}
