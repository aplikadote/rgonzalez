/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.svm.core;

import libsvm.svm_node;
import libsvm.svm_problem;

/**
 *
 * @author Administrador
 */
public class MyProblem extends svm_problem {

    public MyProblem(int l, double[] y, svm_node[][] x) {
        this.l = l;
        this.y = y;
        this.x = x;
    }

    public void printY() {
        for (int i = 0; i < y.length; i++) {
            System.out.println(y[i]);
        }
    }

    public void printX() {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[i].length; j++) {
                System.out.print(x[i][j].index + ":" + x[i][j].value + "  ");
            }
            System.out.println();
        }
    }
    
    public void printProblem(){
        System.out.println("DATA SIZE: " + l);
        for (int i = 0; i < x.length; i++) {
            System.out.print( y[i] + "   ");
            for (int j = 0; j < x[i].length; j++) {
                System.out.print(x[i][j].index + ":" + x[i][j].value + "  ");
            }
            System.out.println();
        }
    }
    
    public int size(){
        return l;
    }
}
