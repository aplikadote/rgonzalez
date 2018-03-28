/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.dataset;

import com.gonzalez.ann.dataset.RegresionInstance;
import java.util.List;

/**
 *
 * @author rene
 */
public interface Dataset {

    public List<RegresionInstance> getDataList();

    public int getDataCount();

    public int getXDataSize();
    
    public int getYSizeData();

    public void setDataList(List<RegresionInstance> dataList);

    public void setDataCount(int dataCount);

    public void setXDataSize(int xDataSize);

    public void unorderData();
}
