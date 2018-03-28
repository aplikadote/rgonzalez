/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.utilities;

import com.gonzalez.ann.dataset.RegresionInstance;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class FoldContainer {

    private Fold[] arrayFold;
    private List<RegresionInstance> trainData;
    private int k;

    public FoldContainer(List<RegresionInstance> trainData, int k) {
        this.k = k;
        this.trainData = trainData;
        setArrayFold(trainData, k);
    }

    private void setArrayFold(List<RegresionInstance> trainData, int k) {
        int trainSize = trainData.size();
        int foldSize = (int) trainData.size() / k;

        //Se crean las bolsas y se llenan con instancias correlativamente
        arrayFold = new Fold[k];
        int shift = 0;
        int cont = 0;
        for (int i = 0; i < k; i++) {
            shift = i * foldSize;
            arrayFold[i] = new Fold();
            for (int j = 0; j < foldSize; j++) {
                arrayFold[i].add(trainData.get(j + shift));
                cont++;
            }
        }

        //Si sobran instancias, se reparten de forma homogenea
        if (cont < trainSize) {
            int dif = trainSize - cont;
            int j = 0;
            for (int i = 0; i < dif; i++) {
                arrayFold[j].add(trainData.get(i + cont));
                j++;

                if (j == k) {
                    j = 0;
                }
            }
        }
    }

    /**
     * Se obtiene todos las bolsas excepto la i-esima
     * y se construye una lista de instancias con el resto
     * @param i
     * @return
     */
    public List<RegresionInstance> getTrainFoldData(int i) {
        ArrayList<RegresionInstance> testFold = new ArrayList<RegresionInstance>();
        for (int j = 0; j < k; j++) {
            if (j != i) {
                for (int m = 0; m < arrayFold[j].size(); m++) {
                    testFold.add(arrayFold[j].get(m));
                }
            }
        }
        return testFold;
    }

    /**
     * Se obtiene la bolsa i-esima
     * @param i
     * @return
     */
    public List<RegresionInstance> getValidationFoldData(int i) {
        return this.arrayFold[i].getData();
    }


    public Fold[] getArrayFold() {
        return arrayFold;
    }

    /**
     * El numero de bolsas
     * @return
     */
    public int getK() {
        return k;
    }

    /**
     * Las instancias reales con que se reparten
     * despues en las bolsas.
     * @return
     */
    public List<RegresionInstance> getTrainData() {
        return trainData;
    }
}
