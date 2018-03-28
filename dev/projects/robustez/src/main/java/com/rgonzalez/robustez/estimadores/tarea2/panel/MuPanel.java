/*
 * MuPanel.java
 *
 * Created on 27 de noviembre de 2008, 17:59
 */
package com.rgonzalez.robustez.estimadores.tarea2.panel;

import com.rgonzalez.robustez.estimadores.tarea2.estimators.MuEstimator;
import javax.swing.JOptionPane;

/**
 *
 * @author  Administrador
 */
public class MuPanel extends javax.swing.JPanel {

    private PlotFunctionPanel panelPlot;
    private MuEstimator mu;
    private double epsilon = 0.9;

    /** Creates new form MuPanel */
    public MuPanel() {
        initComponents();

        this.textEpsilon.setText(Double.toString(epsilon));
        mu = new MuEstimator(epsilon);
        panelPlot = new PlotFunctionPanel(mu.getData(), "k-Huber vs mu", "k-Huber", "mu");
        this.mainPanel.add(panelPlot);
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
        panelUpdate = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textEpsilon = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        buttonUpdate = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        mainPanel.setLayout(new java.awt.BorderLayout());
        add(mainPanel, java.awt.BorderLayout.CENTER);

        jLabel1.setText("epsilon");
        panelUpdate.add(jLabel1);

        textEpsilon.setPreferredSize(new java.awt.Dimension(60, 20));
        textEpsilon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textEpsilonActionPerformed(evt);
            }
        });
        panelUpdate.add(textEpsilon);

        jLabel3.setText("      ");
        panelUpdate.add(jLabel3);

        buttonUpdate.setText("Update");
        buttonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateActionPerformed(evt);
            }
        });
        panelUpdate.add(buttonUpdate);

        add(panelUpdate, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

private void textEpsilonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textEpsilonActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_textEpsilonActionPerformed

private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
    try {
        double myEpsilon = Double.parseDouble(this.textEpsilon.getText());
        if (myEpsilon > 1 || epsilon <= 0) {
            JOptionPane.showMessageDialog(this, "Se ha capturado una cholga", "Cholga", JOptionPane.WARNING_MESSAGE);
        } else {
            epsilon = myEpsilon;
            mu.update(epsilon);
            panelPlot.update(mu.getData());
            panelPlot.updateUI();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Se ha capturado una cholga", "Cholga", JOptionPane.WARNING_MESSAGE);
    }
}//GEN-LAST:event_buttonUpdateActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel panelUpdate;
    private javax.swing.JTextField textEpsilon;
    // End of variables declaration//GEN-END:variables
}
