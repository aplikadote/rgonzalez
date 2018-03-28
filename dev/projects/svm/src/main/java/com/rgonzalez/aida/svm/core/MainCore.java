/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.svm.core;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import libsvm.svm;
import libsvm.svm_model;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Administrador
 */
public class MainCore {

    private MyProblem problem;
    private svm_model model;
    private MyParameters parameters;
    private ArrayList<Data> arrayData;

    public MainCore() {
//        String file = "iris.data";
        String file = "noticiasForSVM.data";
//        String file = "vehicule.data";

        FileParser parser = new FileParser(file);
        problem = new MyProblem(parser.getTrainSize(), parser.getTrainY(), parser.getTrainX());
        Range rangeC = new Range(9, 19, 40);
        Range rangeGamma = new Range(-19, -13, 36);
//        Range rangeC = new Range(0, 4, 40);
//        Range rangeGAmma = new Range(-5, 0, 50);

        parameters = getBestParameters(rangeC, rangeGamma);
        System.out.println("MEJOR C:     " + parameters.getC());
        System.out.println("MEJOR GAMMA: " + parameters.getGamma());

        drawData();

        model = svm.svm_train(problem, parameters);
        double result = 0;
        for (int i = 0; i < parser.getTestSize(); i++) {
            double value = svm.svm_predict(model, parser.getTestX()[i]);
            if (value == parser.getTestY()[i]) {
                System.out.println( parser.getTestY()[i] + " " + value + " BIEN");
                result++;
            } else {
                System.out.println( parser.getTestY()[i] + " " + value + " MAL");
            }

        }
        result = result * 100 / parser.getTestSize();

        System.out.println("Porcentaje de asertividad: " + result + " %");
    }

    public MyParameters getBestParameters(Range rangeC, Range rangeGamma) {

        double bestResult = 0;
        double bestC = -1;
        double bestGamma = -1;
        double bestLogC = Double.NaN;
        double bestLogGamma = Double.NaN;

        arrayData = new ArrayList<Data>();

//        double total = rangeC.getPoints() * rangeGamma.getPoints();
        for (int i = 0; i <= rangeC.getPoints(); i++) {
//            System.out.println( rangeC.getLog(i) );
            for (int j = 0; j <= rangeGamma.getPoints(); j++) {
                double avance = i * rangeGamma.getPoints() +  j;
//                System.out.println(" PORCENTAJE DE AVANCE  " + (avance / total) + " %");

                MyParameters par = new MyParameters(rangeC.getValue(i), rangeGamma.getValue(j));
                double[] target = new double[problem.size()];

                //---->        VALIDACION CRUZADA
                svm.svm_cross_validation(problem, par, 10, target);

                double result = 0;
                for (int k = 0; k < target.length; k++) {
                    result += target[k] == problem.y[k] ? 1.0 : 0.0;
                }
                result = result * 100 / (target.length);

                Data data = new Data(rangeC.getLog(i), rangeGamma.getLog(j), result);
                arrayData.add(data);

                if (result > bestResult) {
                    bestResult = result;
                    bestC = rangeC.getValue(i);
                    bestLogC = rangeC.getLog(i);
                    bestGamma = rangeGamma.getValue(j);
                    bestLogGamma = rangeGamma.getLog(j);
                }
            }
        }

        System.out.println();
        System.out.println("RESULTADO " + bestResult);
        System.out.println("C " + bestC);
        System.out.println("CLOG " + bestLogC);
        System.out.println("GAMMA " + bestGamma);
        System.out.println("GAMMALOG " + bestLogGamma);

        MyParameters bestPar = new MyParameters(bestC, bestGamma);
        return bestPar;
    }

    public void drawData() {
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.setTitle("Grafico Dispersion Accuracy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(getChartPanel());
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getChartPanel() {
        XYSeriesCollection collection = new XYSeriesCollection();
        XYSeries[] arraySeries = new XYSeries[14];

        arraySeries[0] = new XYSeries("<10%");
        arraySeries[1] = new XYSeries("<20%");
        arraySeries[2] = new XYSeries("<30%");
        arraySeries[3] = new XYSeries("<40%");
        arraySeries[4] = new XYSeries("<50%");
        arraySeries[5] = new XYSeries("<60%");
        arraySeries[6] = new XYSeries("<70%");
        arraySeries[7] = new XYSeries("<80%");
        arraySeries[8] = new XYSeries("<90%");
        arraySeries[9] = new XYSeries("<92%");
        arraySeries[10] = new XYSeries("<94%");
        arraySeries[11] = new XYSeries("<96%");
        arraySeries[12] = new XYSeries("<98%");
        arraySeries[13] = new XYSeries("<100%");


        for (int i = 0; i < arrayData.size(); i++) {
            Data data = arrayData.get(i);
            double ac = data.getAccuracy();

            if (ac >= 0 && ac < 10) {
                arraySeries[0].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 10 && ac < 20) {
                arraySeries[1].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 20 && ac < 30) {
                arraySeries[2].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 30 && ac < 40) {
                arraySeries[3].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 40 && ac < 50) {
                arraySeries[4].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 50 && ac < 60) {
                arraySeries[5].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 60 && ac < 70) {
                arraySeries[6].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 70 && ac < 80) {
                arraySeries[7].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 80 && ac < 90) {
                arraySeries[8].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 90 && ac < 92) {
                arraySeries[9].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 92 && ac < 94) {
                arraySeries[10].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 94 && ac < 96) {
                arraySeries[11].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 96 && ac < 98) {
                arraySeries[12].add(data.getCLog(), data.getGammaLog());
            } else if (ac >= 98 && ac < 100) {
                arraySeries[13].add(data.getCLog(), data.getGammaLog());
            }
        }

        for (int i = 0; i < arraySeries.length; i++) {
            collection.addSeries(arraySeries[i]);
        }

        JFreeChart chart = ChartFactory.createScatterPlot(
                "Accuracy",
                "CLog",
                "GammaLog",
                collection,
                PlotOrientation.VERTICAL,
                true,
                true,
                true);

        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
        new MainCore();
    }
}
