/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BackgroundPanel.java
 *
 * Created on 06-may-2009, 17:23:19
 */
package com.rgonzalez.map.core;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author Administrador
 */
public class BackgroundPanel extends javax.swing.JPanel {

    private Frame parent;

    /** Creates new form BackgroundPanel */
    public BackgroundPanel(Frame parent) {
        initComponents();
        this.parent = parent;
//        setSize(100,100);

        this.addMouseListener(new MouseClick(parent));

    }

    public void paintComponent(Graphics g) {
        Dimension tamanho = getSize();
        ImageIcon imagen = new ImageIcon(getClass().getResource("/snapshot.jpg"));
        g.drawImage(imagen.getImage(), 0, 0, tamanho.width, tamanho.height, null);
        setOpaque(false);

        g.drawRect(310, 130, 70, 100);
        super.paintComponent(g);
    }

    private class MouseClick extends MouseAdapter {

        private Frame parent;

        public MouseClick(Frame parent){
            this.parent = parent;
        }

        public void mouseClicked(MouseEvent e) {
            Point point = e.getPoint();
            double x = point.getX();
            double y = point.getY();

            if (x >= 311.0 && x <= 379.0 && y >= 132.0 && y <= 227.0) {

//                            System.out.println("x " + x);
//                            System.out.println("y " + y);
                System.out.println("DENTRO DEL AREA ");
                new SmokeGuyDialog(this.parent);
            }

        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}