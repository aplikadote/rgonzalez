/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.dataset;

import com.gonzalez.ann.dataset.RegresionInstance;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rene
 */
public class VeryEasyDataset extends DefaultDataset {

    public VeryEasyDataset(){
        this.dataList = new ArrayList<RegresionInstance>();
        this.dataCount = 1000;
        this.xDataSize = 3;
        for (int i = 0; i < dataCount; i++) {
            double[] x = new double[xDataSize];
            for (int j = 0; j < xDataSize; j++) {
                x[j] = i;
            }
            double y = i;
            this.dataList.add(new RegresionInstance(x, y));
        }
    }

}
