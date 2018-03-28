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
public class OnlyOutputMaxNormalization implements Normalization {

    private Dataset realDataset;
    private Dataset normalizedDataset;
    private double maxY;

    public OnlyOutputMaxNormalization(Dataset dataset) {
        this.realDataset = dataset;
        int dataCount = dataset.getDataCount();
        int xDataSize = dataset.getXDataSize();

        List<RegresionInstance> normalizedDataList = new ArrayList<RegresionInstance>(dataCount);
        List<RegresionInstance> realDataList = dataset.getDataList();
        for (int i = 0; i < dataCount; i++) {
            RegresionInstance instance = realDataList.get(i);
            double[] xInstance = instance.getX();
            double[] x = new double[xInstance.length];
            double y = instance.getY();

            for (int j = 0; j < x.length; j++) {
                x[j] = xInstance[j];
            }

            normalizedDataList.add(new RegresionInstance(x, y));
        }

        maxY = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < dataCount; i++) {
            double comp = normalizedDataList.get(i).getY();
            if (comp > maxY) {
                maxY = comp;
            }
        }
        for (int i = 0; i < dataCount; i++) {
            RegresionInstance sample = normalizedDataList.get(i);
            if (maxY == 0) {
                normalizedDataList.get(i).setY(maxY);
            } else {
                double comp = sample.getY();
                sample.setY( comp/ maxY);
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
            x[i] = xInstance[i];
        }
        double y = yInstance/maxY;

        return new RegresionInstance(x, y);
    }

    public double desnormalice(double normalicedY) {
        return normalicedY *maxY;
    }
}
