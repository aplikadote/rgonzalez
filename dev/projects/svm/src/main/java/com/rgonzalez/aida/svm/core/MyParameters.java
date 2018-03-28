/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.svm.core;

import libsvm.svm_parameter;

/**
 *
 * @author Administrador
 */
public class MyParameters extends svm_parameter {

    /**
     * ATRIBUTOS
     * int svm_type:        tipo de svm
     * int kernel_type:     tipo de kernel
     * int degree :         para poly
     * double gamma:        para poly/rbf/sigmoid
     * double coef0:        for poly/sigmoid
     *
     * These are for training only 
     * double cache_size:   en MB
     * double eps:          criterio de parada;
     * double C:            para C_SVC, EPSILON_SVR, and NU_SVR
     * int nr_weight:       para C_SVC
     * int weight_label[]:  para C_SVC
     * double weight[]:     para C_SVC
     * double nu:           para NU_SVC, ONE_CLASS, and NU_SVR
     * double p:            para EPSILON_SVR
     * int shrinking:       usar heuristica de disminucion (shrinking)
     * int probability;     hacer estimaciones de probabilidad
     * 
     * DETALLES
     * svm_type can be one of C_SVC, NU_SVC, ONE_CLASS, EPSILON_SVR, NU_SVR.
     * C_SVC:		C-SVM classification
     * NU_SVC:		nu-SVM classification
     * ONE_CLASS:		one-class-SVM
     * EPSILON_SVR:	epsilon-SVM regression
     * NU_SVR:		nu-SVM regression
     * 
     * kernel_type can be one of LINEAR, POLY, RBF, SIGMOID.
     * 
     * LINEAR:	u'*v
     * POLY:	(gamma*u'*v + coef0)^degree
     * RBF:	exp(-gamma*|u-v|^2)
     * SIGMOID:	tanh(gamma*u'*v + coef0)
     * PRECOMPUTED: kernel values in training_set_file
     * 
     * cache_size is the size of the kernel cache, specified in megabytes.
     * C is the cost of constraints violation. 
     * eps is the stopping criterion. (we usually use 0.00001 in nu-SVC, 0.001 in others).
     * nu is the parameter in nu-SVM, nu-SVR, and one-class-SVM.
     * p is the epsilon in epsilon-insensitive loss functionof epsilon-SVM regression.
     * shrinking = 1 means shrinking is conducted; = 0 otherwise.
     * probability = 1 means model with probability information is obtained; = 0 otherwise.
     * 
     * nr_weight, weight_label, and weight are used to change the penalty
     * for some classes (If the weight for a class is not changed, it is
     * set to 1). This is useful for training classifier using unbalanced
     * input data or with asymmetric misclassification cost.
     * 
     * nr_weight is the number of elements in the array weight_label and
     * weight. Each weight[i] corresponds to weight_label[i], meaning that
     * the penalty of class weight_label[i] is scaled by a factor of weight[i].
     * 
     * If you do not want to change penalty for any of the classes,
     * just set nr_weight to 0.
     * 
     * *NOTE* Because svm_model contains pointers to svm_problem, you can
     * not free the memory used by svm_problem if you are still using the
     * svm_model produced by svm_train(). 
     * 
     * *NOTE* To avoid wrong parameters, svm_check_parameter() should be
     * called before svm_train().
     * 
     */ 
        
    public MyParameters(double CParameter, double gammaParameter) {
        gamma = gammaParameter;
        C=CParameter;        
                
        //Parametros por defecto
        svm_type = svm_parameter.C_SVC;
        kernel_type = svm_parameter.RBF;
        cache_size = 100;
        eps = 0.001;
        nr_weight=0;
        p = 0.1;
    }
    
    public double getC(){
        return C;
    }

    public double getGamma(){
        return gamma;
    }
    
    public void print() {
        System.out.println("PARAMETERS VALUES");
        System.out.println("svm_type " + svm_type);
        System.out.println("kernel_type " + kernel_type);
        System.out.println("degree " + degree);
        System.out.println("gamma " + gamma);
        System.out.println("coef0 " + coef0);

        // these are for training only
        System.out.println("cache_size " + cache_size);
        System.out.println("eps " + eps);
        System.out.println("C " + C);
        System.out.println("nr_weight " + nr_weight);
        System.out.println("weight_label " + weight_label);
        System.out.println("weight " + weight);
        System.out.println("nu " + nu);
        System.out.println("p " + p);
        System.out.println("shrinking " + shrinking);
        System.out.println("probability " + probability);
        System.out.println();
    }

    public static void print(svm_parameter par) {
        System.out.println("PARAMETERS VALUES");
        System.out.println("svm_type " + par.svm_type);
        System.out.println("kernel_type " + par.kernel_type);
        System.out.println("degree " + par.degree);
        System.out.println("gamma " + par.gamma);
        System.out.println("coef0 " + par.coef0);

        // these are for training only
        System.out.println("cache_size " + par.cache_size);
        System.out.println("eps " + par.eps);
        System.out.println("C " + par.C);
        System.out.println("nr_weight " + par.nr_weight);
        System.out.println("weight_label " + par.weight_label);
        System.out.println("weight " + par.weight);
        System.out.println("nu " + par.nu);
        System.out.println("p " + par.p);
        System.out.println("shrinking " + par.shrinking);
        System.out.println("probability " + par.probability);
        System.out.println();
    }
}
