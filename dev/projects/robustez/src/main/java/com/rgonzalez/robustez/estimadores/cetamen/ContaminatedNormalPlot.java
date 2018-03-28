/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.robustez.estimadores.cetamen;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Random;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Administrador
 */
public class ContaminatedNormalPlot extends JFrame{
    private Random r = new Random();
    private JFreeChart chart;
    
    public ContaminatedNormalPlot(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container main = this.getContentPane();
        main.setLayout(new BorderLayout());
        main.add(new ChartPanel(getChart()));
        
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
    
    public JFreeChart getChart(){
        HistogramDataset dataset = getDataset();
        chart = ChartFactory.createHistogram(
                "Titulo",
                "N(0,1)",
                "Frecuencia Absoluta",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                true);
        
        return chart;
    }
    
    public HistogramDataset getDataset(){
        HistogramDataset dataset = new HistogramDataset();
        
        int n_test = 10;
        for(int i=0; i< n_test; i++){
            double test = (double)i/n_test;
            dataset.addSeries("N(0,"+test+")", getSerie(0), 300);    
        }
        
        return dataset;
    }
    
    public double[] getSerie(double e){
        int n= 100000;
        double mu = 0;
        double sigma = 0.0001;
        double[] data = new double[n];
        for(int i=0;i<n;i++){
            data[i] = (1 -e)*r.nextGaussian() + e*(sigma*r.nextGaussian() + mu);
        }
        return data;
    }
    
    public static void main(String[] args){
        new ContaminatedNormalPlot();
    }

}
