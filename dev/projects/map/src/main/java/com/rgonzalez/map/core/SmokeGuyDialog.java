/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SmokeGuyDialog.java
 *
 * Created on 06-may-2009, 17:55:37
 */

package com.rgonzalez.map.core;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JLabel;

/**
 *
 * @author Administrador
 */
public class SmokeGuyDialog extends javax.swing.JDialog {

    /** Creates new form SmokeGuyDialog */
    public SmokeGuyDialog(java.awt.Frame parent) {
        super(parent, true);
        initComponents();

        this.add(new JLabel("Ha hecho click exitosamente"));
        this.pack();
        this.center();
        this.setVisible(true);
    }

    private void center() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screen = toolkit.getScreenSize();
        Dimension frame = this.getSize();

        int x = (screen.width - frame.width) / 2;
        int y = (screen.height - frame.height) / 2;

        this.setLocation(x+100, y+100);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
