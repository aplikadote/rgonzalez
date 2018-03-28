/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.aida.ann;

import com.gonzalez.aida.dataset.ClassificationDataset;
import com.gonzalez.aida.dataset.ClassificationInstance;
import com.gonzalez.aida.dataset.Instance;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Administrador
 */
public class ClassificationNN {

    private double[] hidden; //los nodos ocultos, j

    private double[] output; //los nodos de salida, k

    private double[] hiddenBias; //bias de los nodos ocultos

    private double[] outputBias; // bias de los nodos de salida

    private double[][] betaWeights; //pesos capa oculta-entrada

    private double[][] alphaWeights; //pesos capa salida-oculta

    private double emsembleAlpha;
    private double nu;
    private int numberOfInputs;
    private int numberOfHiddens;
    private int numberOfOutputs;

    public ClassificationNN(int inputNodes, int hiddenNodes, int outputNodes, double nu) {
        Random r = new Random(System.currentTimeMillis());
        this.nu = nu;
        numberOfInputs = inputNodes;
        numberOfHiddens = hiddenNodes;
        numberOfOutputs = outputNodes;

        hidden = new double[numberOfHiddens];
        output = new double[numberOfOutputs];
        hiddenBias = new double[numberOfHiddens];
        outputBias = new double[numberOfOutputs];
        alphaWeights = new double[numberOfHiddens][numberOfInputs];
        betaWeights = new double[numberOfOutputs][numberOfHiddens];

        for (int j = 0; j < numberOfHiddens; j++) {
            hiddenBias[j] = 2 * r.nextDouble() - 1;
            for (int i = 0; i < numberOfInputs; i++) {
                alphaWeights[j][i] = 2 * r.nextDouble() - 1;
            }
        }

        for (int k = 0; k < numberOfOutputs; k++) {
            outputBias[k] = 2 * r.nextDouble() - 1;
            for (int j = 0; j < numberOfHiddens; j++) {
                betaWeights[k][j] = 2 * r.nextDouble() - 1;
            }
        }

    }

    public void forwardPass(double x[]) {

        for (int j = 0; j < numberOfHiddens; j++) {
            hidden[j] = 0;
            for (int i = 0; i < numberOfInputs; i++) {
                hidden[j] += alphaWeights[j][i] * x[i];
            }
            hidden[j] += hiddenBias[j];
            hidden[j] = 1.0 / (1.0 + Math.exp(-hidden[j])); //sigmoidal

        }

        double sum = 0;
        for (int k = 0; k < numberOfOutputs; k++) {
            output[k] = 0;
            for (int j = 0; j < numberOfHiddens; j++) {
                output[k] += betaWeights[k][j] * hidden[j];
            }
            output[k] += outputBias[k];
            output[k] = Math.exp(output[k]);
            sum += output[k];
        }

        //softmax
        for (int k = 0; k < numberOfOutputs; k++) {
            output[k] = output[k] / sum;
        }
    }

    public void backwardPass(double x[], int[] y) {

        //Primero los alpha, porque usan el valor de beta sin actualizacion
        for (int j = 0; j < numberOfHiddens; j++) {
            double sum = 0;
            for (int k = 0; k < numberOfOutputs; k++) {
                sum += -y[k] * (1 - output[k]) * betaWeights[k][j] * hidden[j] * (1 - hidden[j]);
            }

            for (int i = 0; i < numberOfInputs; i++) {
                alphaWeights[j][i] = alphaWeights[j][i] - nu * sum * x[i];
            }
            hiddenBias[j] = hiddenBias[j] - nu * sum;
        }

        for (int k = 0; k < numberOfOutputs; k++) {
            for (int j = 0; j < numberOfHiddens; j++) {
                betaWeights[k][j] = betaWeights[k][j] - nu * -y[k] * (1 - output[k]) * hidden[j];
            }
            outputBias[k] = outputBias[k] - nu * -y[k] * (1 - output[k]);
        }
    }

    public double[] trainWith(int iteraciones, List<Instance> trainData) {
        int i = 0;

        double[] error = new double[iteraciones];
        double minError = Double.POSITIVE_INFINITY;
        double[][] bestAlphaWeights = new double[numberOfHiddens][numberOfInputs];
        double[][] bestBetaWeights = new double[numberOfOutputs][numberOfHiddens];
        for (int it = 0; it < iteraciones; it++) {
            ClassificationInstance instance = (ClassificationInstance) trainData.get(i);
            double[] x = instance.getX();
            int y[] = instance.getCodifiedY();

            forwardPass(x);
            backwardPass(x, y);
            if (i == trainData.size() - 1) {
                i = 0;
            } else {
                i++;
            }
            error[it] = calculateError(trainData);
            if (error[it] < minError) {
                minError = error[it];
                for (int j = 0; j < numberOfHiddens; j++) {
                    for (int k = 0; k < numberOfInputs; k++) {
                        bestAlphaWeights[j][k] = alphaWeights[j][k];
                    }
                }

                for (int k = 0; k < numberOfOutputs; k++) {
                    for (int j = 0; j < numberOfHiddens; j++) {
                        bestBetaWeights[k][j] = betaWeights[k][j];
                    }
                }
            }
        }

        System.out.println("  ENTRENAMIENTO MAQUINA Nº NEURONAS OCULTAS " + numberOfHiddens);
        System.out.println("  Mal clasificados: " + minError + " de " + trainData.size());
        
        for (int j = 0; j < numberOfHiddens; j++) {
            for (int k = 0; k < numberOfInputs; k++) {
                alphaWeights[j][k] = bestAlphaWeights[j][k];
            }
        }

        for (int k = 0; k < numberOfOutputs; k++) {
            for (int j = 0; j < numberOfHiddens; j++) {
                betaWeights[k][j] = bestBetaWeights[k][j];
            }
        }
        
        return error;
    }

    private double calculateError(List<Instance> list) {
        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            ClassificationInstance instance = (ClassificationInstance) list.get(i);
            double[] x = instance.getX();
            int y = instance.getTarget();
            forwardPass(x);
            int predictedY = translate();
            if (y != predictedY) {
                sum++;
            }
        }
        return sum;
    }

    public double calculateEnsambleError(List<Instance> list) {
        double sumError = 0;
        double sumWeight = 0;
        for (int i = 0; i < list.size(); i++) {
            ClassificationInstance instance = (ClassificationInstance) list.get(i);
            double[] x = instance.getX();
            int y = instance.getTarget();
            forwardPass(x);
            int predictedY = translate();
            if (y != predictedY) {
                sumError += instance.getWeight();
            }
            sumWeight += instance.getWeight();
        }
        return sumError / sumWeight;
    }

    private int translate() {
        if (numberOfOutputs == 2) {
            if(output[0] > output[1]){
                return 1;
            }
            else{
                return -1;
            }
        } else {
            double max = Double.NEGATIVE_INFINITY;
            int nodo = -1;
            for (int i = 0; i < output.length; i++) {
                if (output[i] > max) {
                    max = output[i];
                    nodo = i;
                }
            }
            return nodo + 1;
        }
    }

    public int predict(double[] x) {
        forwardPass(x);
        int predictedY = translate();
        return predictedY;
    }

    public double test(List<Instance> testData) {
        double sum = 0;
        for (int i = 0; i < testData.size(); i++) {
            ClassificationInstance instance = (ClassificationInstance) testData.get(i);
            double[] x = instance.getX();
            int y = instance.getTarget();
            forwardPass(x);
            int predictedY = translate();
            if (y != predictedY) {
                sum++;
            }

//            if (y == predictedY) {
//                System.out.println(y + "  " + predictedY + "  " + " BIEN");
//            } else {
//                System.out.println(y + "  " + predictedY + "  " + " MAL");
//            }
        }
        System.out.println("TESTEO");
        System.out.println("Mal clasificados: " + sum + " de " + testData.size());
        return sum;
    }

    public double getEmsembleAlpha() {
        return emsembleAlpha;
    }

    public void setEmsembleAlpha(double emsembleAlpha) {
        this.emsembleAlpha = emsembleAlpha;
    }
}
