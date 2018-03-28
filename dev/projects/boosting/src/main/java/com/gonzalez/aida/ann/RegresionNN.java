/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.ann;

import com.gonzalez.aida.dataset.Instance;
import com.gonzalez.aida.dataset.RegresionDataset;
import com.gonzalez.aida.dataset.RegresionInstance;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrador
 */
public class RegresionNN {

    private double[][] alphaWeights;
    private double[] hiddenBias;
    private double[] hidden;
    private double[] betaWeights;
    private double outputBias;
    private double output;
    private double nu;
    private int numberOfInputs;
    private int numberOfHidden;

    public RegresionNN(int numberOfInputs, int numberOfHidden, double nu) {
        Random r = new Random(System.currentTimeMillis());
        this.nu = nu;
        this.numberOfInputs = numberOfInputs;
        this.numberOfHidden = numberOfHidden;

        hidden = new double[numberOfHidden];
        hiddenBias = new double[numberOfHidden];
        betaWeights = new double[numberOfHidden];
        alphaWeights = new double[numberOfHidden][numberOfInputs];

        for (int h = 0; h < numberOfHidden; h++) {
            hiddenBias[h] = 2 * r.nextDouble() - 1;

            for (int i = 0; i < numberOfInputs; i++) {
                alphaWeights[h][i] = 2 * r.nextDouble() - 1;
            }
            betaWeights[h] = 2 * r.nextDouble() - 1;
        }
        this.outputBias = 2 * r.nextDouble() - 1;

    }

    public void forwardPass(double x[]) {
        output = 0;

        for (int h = 0; h < hidden.length; h++) {
            hidden[h] = 0;

            for (int i = 0; i < numberOfInputs; i++) {
                hidden[h] += alphaWeights[h][i] * x[i];
            }
            hidden[h] += hiddenBias[h];
            hidden[h] = 1.0 / (1.0 + Math.exp(-hidden[h]));
            output += hidden[h] * betaWeights[h];
        }
        output += outputBias;
    }

    public void backwardPass(double x[], double target) {
        double outputDelta = (output - target);

        for (int h = 0; h < numberOfHidden; h++) {
            double hiddenDelta = outputDelta * betaWeights[h] * (hidden[h] * (1 - hidden[h]));

            for (int i = 0; i < numberOfInputs; i++) {
                alphaWeights[h][i] += -nu * hiddenDelta * x[i];
            }
            hiddenBias[h] += -nu * hiddenDelta;
            betaWeights[h] += -nu * outputDelta * hidden[h];
        }
        outputBias += -nu * outputDelta;
    }

    public double[][] trainWith(int iteraciones, RegresionDataset dataset) {
        List<Instance> trainData = dataset.getTrainData();
        int i = 0;
        double[][] error = new double[iteraciones][2];
        for (int it = 0; it < iteraciones; it++) {
            RegresionInstance instance = (RegresionInstance) trainData.get(i);
            double[] x = instance.getX();
            double y = instance.getY();

            forwardPass(x);
            backwardPass(x, y);
            if (i == trainData.size() - 1) {
                i = 0;
            } else {
                i++;
            }
            error[it][1] = calculateError(trainData, dataset);
            error[it][0] = it;
            
//            if(it > 4 ){
//                if(error[it] > error[it-1] && error[it-1] > error[it-2] && error[it-2] > error[it-3] && error[it-3] > error[it-4]){
//                    break;
//                }
//            }

        }
        return error;
    }
    
    public void trainForEnsembleWith(int iteraciones, List<Instance> trainData) {
        int i = 0;
//        double[][] error = new double[iteraciones][2];
        for (int it = 0; it < iteraciones; it++) {
            RegresionInstance instance = (RegresionInstance) trainData.get(i);
            double[] x = instance.getX();
            double y = instance.getResiduo();

            forwardPass(x);
            backwardPass(x, y);
            if (i == trainData.size() - 1) {
                i = 0;
            } else {
                i++;
            }
//            error[it][1] = calculateError(trainData, dataset);
//            error[it][0] = it;
            
//            if(it > 4 ){
//                if(error[it] > error[it-1] && error[it-1] > error[it-2] && error[it-2] > error[it-3] && error[it-3] > error[it-4]){
//                    break;
//                }
//            }

        }
//        return error;
    }

    private double calculateError(List<Instance> list, RegresionDataset dataset) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            RegresionInstance instance = (RegresionInstance) list.get(i);
            double[] x = instance.getX();
            double y = instance.getY();
            forwardPass(x);

            double stdOut = dataset.desestandariceY(output);
            double stdReal = dataset.desestandariceY(y);

            sum += Math.pow(stdOut - stdReal, 2);
        }
        return sum;
    }

    public double test(List<RegresionInstance> testData) {
        double sum = 0;
        for (int i = 0; i < testData.size(); i++) {
            double[] x = testData.get(i).getX();
            double y = testData.get(i).getY();
            forwardPass(x);
            sum += Math.pow(output - y, 2);
        }

        return (1 / (double) testData.size()) * sum;
    }

    public void predictAndPrint(RegresionInstance instance, RegresionDataset dataset) {
        double[] x = instance.getX();
        double y = instance.getY();

        forwardPass(x);

        double stdOut = dataset.desestandariceY(output);
        double stdReal = dataset.desestandariceY(y);

        System.out.println("ID instancia    : " + instance.getId());
        System.out.println("Valor Y         : " + stdReal);
        System.out.println("Valor Y predicho: " + stdOut);
        System.out.println("EC              : " + Math.pow(stdReal - stdOut, 2));
    }

    public double predict(RegresionInstance instance, RegresionDataset dataset) {
        double[] x = instance.getX();
        forwardPass(x);
        return dataset.desestandariceY(output);
    }
    
    public double predictForEnsemble(double[] x) {
        forwardPass(x);
        return output;
    }
}