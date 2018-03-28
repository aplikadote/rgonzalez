/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.ann.dataset;

/**
 *
 * @author rene
 */
public interface Normalization {

    public Dataset getNormalizedDataset();

    public RegresionInstance normalice(RegresionInstance instance);

    public double desnormalice(double normalicedY);
}
