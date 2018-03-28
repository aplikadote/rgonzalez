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
public class FakeDataset extends DefaultDataset {

    public FakeDataset(){
        this.dataList = new ArrayList<RegresionInstance>();
        double[] x = {0.1,0.2,0.3};
        dataList.add(new RegresionInstance(x,0.15));
        double[] x2 = {0.1,0.1,0.1};
        dataList.add(new RegresionInstance(x2,0.12));
        double[] x3 = {0.1,0.2,0.1};
        dataList.add(new RegresionInstance(x3,0.11));
    }

}
