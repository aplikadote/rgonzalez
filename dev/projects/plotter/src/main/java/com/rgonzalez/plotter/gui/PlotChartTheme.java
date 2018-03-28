/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.plotter.gui;

import java.awt.Color;
import java.awt.RenderingHints;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;


/**
 *
 * @author Administrador
 */
public final class PlotChartTheme {

    public static void applyDefault(JFreeChart chart){
        Plot plot = chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        chart.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        if(plot instanceof XYPlot){
            XYPlot xyplot = (XYPlot) plot;
            xyplot.setRangeGridlinePaint(Color.gray);
        }
        else{
            CategoryPlot categoryplot = (CategoryPlot) plot;
        }
    }

}
