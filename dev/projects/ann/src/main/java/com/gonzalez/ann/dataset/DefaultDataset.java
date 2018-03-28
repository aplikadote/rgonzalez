/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.dataset;

import com.gonzalez.ann.dataset.RegresionInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author rene
 */
public class DefaultDataset implements Dataset {

    protected List<RegresionInstance> dataList;
    protected int dataCount;
    protected int xDataSize;

    public List<RegresionInstance> getDataList() {
        return this.dataList;
    }

    public int getXDataSize() {
        return xDataSize;
    }

    public int getYSizeData() {
        return 1;
    }

    public int getDataCount() {
        return this.dataCount;
    }

     public void setDataList(List<RegresionInstance> dataList){
         this.dataList = dataList;
     }

    public void setDataCount(int dataCount){
        this.dataCount = dataCount;
    }

    public void setXDataSize(int xDataSize){
        this.xDataSize = xDataSize;
    }

    public void unorderData() {
        List<RegresionInstance> unorderList = new ArrayList<RegresionInstance>(dataCount);
        Random r = new Random();

        while (!this.dataList.isEmpty()) {
            int p = r.nextInt(this.dataList.size());
            RegresionInstance instance = this.dataList.get(p);
            unorderList.add(instance);
            this.dataList.remove(instance);
        }

        this.dataList = unorderList;
    }
}
