/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.main;

import com.gonzalez.ann.core.CrossValidationStrategy;
import com.gonzalez.ann.core.InitValueType;
import com.gonzalez.ann.core.IterationMethod;
import com.gonzalez.ann.core.MomentumMLWMFactory;
import com.gonzalez.ann.core.NewtonMLWMFactory;
import com.gonzalez.ann.core.RandomInitValue;
import com.gonzalez.ann.core.SuperSABMLWMFactory;
import com.gonzalez.ann.core.TrainingMethod;
import com.gonzalez.ann.core.TrainingStrategy;
import com.gonzalez.ann.core.WeightMatrixFactory;
import com.gonzalez.ann.dataset.CrossValidationPartition;
import com.gonzalez.ann.dataset.Dataset;
import com.gonzalez.ann.dataset.Normalization;
import com.gonzalez.ann.dataset.RegresionDataset;
import com.gonzalez.ann.dataset.StandardNormalization;
import com.gonzalez.ann.neural.NeuralNetwork;
import com.gonzalez.ann.neural.RegresionNeuralNetwork;

/**
 *
 * @author rene
 */
public class MainCore {

    public static void main(String[] args) {
//        Dataset dataset = new VeryEasyDataset();
        Dataset dataset = new RegresionDataset("boston.data");
//        Dataset dataset = new MesDataset("harnero2.csv");

//        dataset.unorderData();
        int xDataSize = dataset.getXDataSize();

        Normalization normalization = new StandardNormalization(dataset);
//        Normalization normalization = new MinMaxNormalization(dataset);
//        Normalization normalization = new OnlyOutputMaxNormalization(dataset);

        int nNeurons = 3;
        int layers = 3;

        //        InitValueType init = new FixedInitWeight(0.5);
        InitValueType initValueWeight = new RandomInitValue(0, 0.7);

        double minError = Double.POSITIVE_INFINITY;

        int maxIt = 1000;
        double[] newton = new double[maxIt];
        double[] momentum = new double[maxIt];
        double[] supersab = new double[maxIt];

//        int minIteraciones=0;
//        double minCf = 0;
//        double minAlpha = 0;
//        double minI = 0;
//        double minD = 0;
        int iteraciones = 710;
        int tipo = 3;
        double eta = 0.21;
        double cf = 0.07;
        double alpha = 0.24;
        double I = 1.6;
        double D = 0.74;

//        for (int tipo = 1; tipo <= 3; tipo++) {
//            for (int i = 0; i < maxIt; i++) {

//            I += 0.1;
//            D = 0;
//            for (int j = 1; j <= 100; j++) {
//                D += 0.01;
        TrainingMethod method = new IterationMethod(iteraciones);
//        TrainingMethod method = new EarlyDetentionMethod(dataset.getDataCount());

        WeightMatrixFactory factory = null;
        switch (tipo) {
            case 1:
                factory = new NewtonMLWMFactory(eta, initValueWeight);
                break;
            case 2:
                factory = new MomentumMLWMFactory(eta, initValueWeight, cf, alpha);
                break;
            case 3:
                factory = new SuperSABMLWMFactory(eta, initValueWeight, cf, alpha, I, D);
                break;
            default:
                break;
        }

//        NeuralNetwork ann = new BostonNeuralNetwork(xDataSize, nNeurons, method, eta, initValueWeight);
        NeuralNetwork ann = new RegresionNeuralNetwork(xDataSize, layers, nNeurons, method, factory);

        Dataset normalizedDataset = normalization.getNormalizedDataset();
//        DatasetPartition partition = new DatasetPartition(normalizedDataset, 0.70, 0.10);
        CrossValidationPartition partition = new CrossValidationPartition(normalizedDataset, 0.8, 10);

//        TrainingStrategy strategy = new OverTrainingStrategy(normalizedDataset);
//        TrainingStrategy strategy = new NormalStrategy(partition);
        TrainingStrategy strategy = new CrossValidationStrategy(partition);
        strategy.runTraining(ann, false);
//         ann.train(partition.getTrainingData(), partition.getValidationData(), false);

        double predictError = ann.predict(partition.getTestingData(), normalization, true);
        System.out.println("Predict Error: " + predictError);
//                System.out.println("Predict Error: " + predictError + "  tipo" + tipo + "  iteraciones: " + i);
//                System.out.println(i + " " + predictError + "  " + tipo);
//                System.out.println("Predict Error: " + predictError + "   cf:" + cf + "   alpha: " + alpha);
//                System.out.println("Predict Error: " + predictError + "   I:" + I + "   D: " + D);

//                switch (tipo) {
//                    case 1:
//                        newton[i] = predictError;
//                        break;
//                    case 2:
//                        momentum[i] = predictError;
//                        break;
//                    case 3:
//                        supersab[i] = predictError;
//                        break;
//                    default:
//                        break;
//                }

//        if (predictError < minError) {
//            minError = predictError;
//                    minIteraciones = i;
//                    minCf = cf;
//                    minAlpha = alpha;
//            minI = I;
//            minD = D;
//        }

//            }
//            }
//        }


//        System.out.println("Predict Error: " + minError + "   iteraciones:" + minIteraciones );
//            System.out.println("Predict Error: " + minError + "   cf:" + minCf + "   alpha: " + minAlpha);
//            System.out.println("Predict Error: " + minError + "   I:" + minI + "   D: " + minD);

//        }
    }
}
