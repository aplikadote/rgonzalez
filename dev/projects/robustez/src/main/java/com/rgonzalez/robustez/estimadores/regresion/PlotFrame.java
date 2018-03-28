/*
 * PlotFrame.java
 *
 * Created on 22 de diciembre de 2008, 21:41
 */

package com.rgonzalez.robustez.estimadores.regresion;

import com.rgonzalez.robustez.estimadores.tarea2.panel.PlotFunctionPanel;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author  Administrador
 */
public class PlotFrame extends javax.swing.JFrame {

    /** Creates new form PlotFrame */
    public PlotFrame(double[][] data, String titulo, String x, String y) {
        initComponents();
        this.mainPanel.add(new PlotFunctionPanel(data, titulo, x, y));
        
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        mainPanel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(mainPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables

}
