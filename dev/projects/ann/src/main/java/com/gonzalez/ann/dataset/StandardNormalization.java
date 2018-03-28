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
public class StandardNormalization implements Normalization {

    private Dataset realDataset;
    private Dataset normalizedDataset;
    private double[] muX;
    private double[] sigma2X;
    private double muY;
    private double sigma2Y;

    public StandardNormalization(Dataset realDataset) {
        this.realDataset = realDataset;
        int dataCount = realDataset.getDataCount();
        int xDataSize = realDataset.getXDataSize();
        this.muX = new double[xDataSize];
        this.sigma2X = new double[xDataSize];

        List<RegresionInstance> normalizedDataList = new ArrayList<RegresionInstance>(dataCount);
        List<RegresionInstance> realDataList = realDataset.getDataList();

        for (int i = 0; i < dataCount; i++) {
            RegresionInstance instance = realDataList.get(i);
            double[] xInstace = instance.getX();
            double[] x = new double[xInstace.length];
            for (int j = 0; j < xInstace.length; j++) {
                x[j] = xInstace[j];
            }
            double y = instance.getY();

            normalizedDataList.add(new RegresionInstance(x, y));
        }

        for (int j = 0; j < xDataSize; j++) {
            muX[j] = 0;
            for (int i = 0; i < dataCount; i++) {
                muX[j] += realDataList.get(i).getX()[j];
            }
            muX[j] /= dataCount;

            for (int i = 0; i < dataCount; i++) {
                sigma2X[j] += Math.pow(realDataList.get(i).getX()[j] - muX[j], 2);
            }
            sigma2X[j] /= dataCount;

            for (int i = 0; i < dataCount; i++) {
                if (sigma2X[j] == 0) {
                    normalizedDataList.get(i).getX()[j] = 0;
                } else {
                    normalizedDataList.get(i).getX()[j] = (normalizedDataList.get(i).getX()[j] - muX[j]) / (Math.pow(sigma2X[j], 0.5));
                }
            }
        }

        this.muY = 0;
        this.sigma2Y = 0;
        for (int i = 0; i < dataCount; i++) {
            muY += normalizedDataList.get(i).getY();
        }
        muY /= dataCount;

        for (int i = 0; i < dataCount; i++) {
            sigma2Y += Math.pow(normalizedDataList.get(i).getY() - muY, 2);
        }
        sigma2Y /= dataCount;

        for (int i = 0; i < dataCount; i++) {
            if (sigma2Y == 0) {
                normalizedDataList.get(i).setY(0);
            } else {
                normalizedDataList.get(i).setY((normalizedDataList.get(i).getY() - muY) / (Math.pow(sigma2Y, 0.5)));
            }
        }

        this.normalizedDataset = new DefaultDataset();
        this.normalizedDataset.setDataList(normalizedDataList);
        this.normalizedDataset.setDataCount(dataCount);
        this.normalizedDataset.setXDataSize(xDataSize);

    }

    public Dataset getNormalizedDataset() {
        return this.normalizedDataset;
    }

    public RegresionInstance normalice(RegresionInstance instance) {
        double[] xInstance = instance.getX();
        double[] x = new double[xInstance.length];
        double yInstance = instance.getY();

        for (int i = 0; i < x.length; i++) {
            x[i] = (xInstance[i] - muX[i]) / (Math.pow(sigma2X[i], 0.5));
        }
        double y = (yInstance - muY) / (Math.pow(sigma2Y, 0.5));

        return new RegresionInstance(x, y);
    }

    public double desnormalice(double normalicedY) {
        return normalicedY * Math.pow(sigma2Y, 0.5) + muY;
    }
}
