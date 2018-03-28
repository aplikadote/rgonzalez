/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.aida.dataset;

/**
 *
 * @author Administrador
 */
public interface Instance extends Cloneable{

    public double[] getX();
    
    public int getSizeX();

    public Instance clone();
    
    public void print();
    
    public int getId();
    
}
