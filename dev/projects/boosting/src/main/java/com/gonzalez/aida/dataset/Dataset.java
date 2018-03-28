/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.dataset;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrador
 */
public abstract class Dataset {

    protected List<Instance> data = new ArrayList<Instance>();
    protected List<Instance> trainData = new ArrayList<Instance>();
    protected List<Instance> testData = new ArrayList<Instance>();
    protected int dataSize;
    protected int testSize;
    protected int trainSize;
    protected int columns; //el numero de caracteristicas sin contar la columna Y

    protected double[] muX;
    protected double[] sigma2X;
    /** Se guarda una relacion entre la instancia REAL y la instancia ESTANDARIZADA */
    protected Hashtable<Instance, Instance> hashData = new Hashtable<Instance, Instance>();
    /** Se guarda una relacion entre la instancia ESTANDARIZADA y la instancia REAL */
    protected Hashtable<Instance, Instance> hashTrain = new Hashtable<Instance, Instance>();

    public abstract void parseAndFillData(String filename);
    /** Usado para dataset de clasificacion */
    protected int clusters;

    /**
     * Para Clasificacion, iris viene estandarizado
     * @param filename
     * @param columns
     * @param clusters
     * @param standarize
     */
    public Dataset(String filename, int columns, int clusters, boolean standarize) {
        this.columns = columns;
        this.clusters = clusters;
        init(filename, standarize);
    }

    /**
     * Para Regresion, lo datos no vienen estandarizados
     * @param filename
     */
    public Dataset(String filename) {
        init(filename, true);
    }

    private void init(String filename, boolean standarize) {
        parseAndFillData(filename);
        fillTrainAndTest();
        if (standarize) {
            standarizeTrainAndTestX();
        }

    }

    private void fillTrainAndTest() {
        if (data != null && data.size() > 0) {
            dataSize = data.size();
            trainSize = (int) (dataSize * 0.8);
            testSize = dataSize - trainSize;

            List<Instance> cloneData = new ArrayList<Instance>();
            for (int i = 0; i < dataSize; i++) {
                Instance newInstance = data.get(i).clone();
                cloneData.add(newInstance);
                hashData.put(data.get(i), newInstance);
                hashTrain.put(newInstance, data.get(i));
            }

            Random r = new Random();
            for (int i = 0; i < trainSize; i++) {
                int p = r.nextInt(cloneData.size());
                trainData.add(cloneData.get(p));
                cloneData.remove(p);
            }

            for (int i = 0; i < testSize; i++) {
                testData.add(cloneData.get(i));
            }

        } else {
            dataSize = 0;
            trainSize = 0;
            testSize = 0;
        }

    }

    private void standarizeTrainAndTestX() {
        //El numero de caracteristicas

        muX = new double[columns];
        sigma2X = new double[columns];
        for (int j = 0; j < columns; j++) {
            setMuTrain(j);
            setSigma2Train(j);
            standarizeX(trainData, j);
            standarizeX(testData, j);
        }
    }

    private void setMuTrain(int j) {
        muX[j] = 0;
        for (int i = 0; i < trainSize; i++) {
            muX[j] += trainData.get(i).getX()[j];
        }
        muX[j] /= trainSize;
    }

    private void setSigma2Train(int j) {
        sigma2X[j] = 0;
        for (int i = 0; i < trainSize; i++) {
            sigma2X[j] += Math.pow(trainData.get(i).getX()[j] - muX[j], 2);
        }
        sigma2X[j] /= trainSize;
    }

    private void standarizeX(List<Instance> stdData, int j) {
        for (int i = 0; i < stdData.size(); i++) {
            if (sigma2X[j] == 0) {
                stdData.get(i).getX()[j] = 0;
            } else {
                stdData.get(i).getX()[j] = (stdData.get(i).getX()[j] - muX[j]) / (Math.pow(sigma2X[j], 0.5));
            }
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getDataSize() {
        return dataSize;
    }

    public int getTestSize() {
        return testSize;
    }

    public List<Instance> getTestData() {
        return testData;
    }

    public int getTrainSize() {
        return trainSize;
    }

    public List<Instance> getTrainData() {
        return trainData;
    }

    public void printData() {
        print(data);
    }

    public void printTrain() {
        print(trainData);
    }

    public void printTest() {
        print(testData);
    }

    private void print(List<Instance> listData) {
        for (int i = 0; i < listData.size(); i++) {
            Instance instance = listData.get(i);
            instance.print();
        }
    }

    public double[] getMuX() {
        return muX;
    }

    public double[] getSigma2X() {
        return sigma2X;
    }

    public Hashtable<Instance, Instance> getHashData() {
        return hashData;
    }

    public List<Instance> getData() {
        return this.data;
    }

    public double[] desestandariceX(double[] valueX) {
        double[] desX = new double[columns];
        for (int i = 0; i < columns; i++) {
            desX[i] = valueX[i] * Math.sqrt(sigma2X[i]) + muX[i];
        }
        return desX;
    }

    public Instance search(List<Instance> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            Instance instance = list.get(i);
            if (instance.getId() == id) {
                return instance;
            }
        }
        return null;
    }
}
