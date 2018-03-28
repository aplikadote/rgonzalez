/*
 * Main.java
 *
 * Created on 02 de mayo de 2007, 09:58 PM
 * @author  Luis Chinchilla
 */

package com.rgonzalez.mathevalexp;

public class Main extends javax.swing.JFrame {
        
    public Main() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelNorth = new javax.swing.JPanel();
        lblExpresion = new javax.swing.JLabel();
        panelCenter = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtExpresion = new javax.swing.JTextArea();
        panelEast = new javax.swing.JPanel();
        btnEvaluar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        lblResultado = new javax.swing.JLabel();
        txtResultado = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Evaluador de Expresiones Matemáticas");
        setAlwaysOnTop(true);
        setResizable(false);

        lblExpresion.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lblExpresion.setText("Expresión Matemática");
        panelNorth.add(lblExpresion);

        getContentPane().add(panelNorth, java.awt.BorderLayout.NORTH);

        panelCenter.setLayout(new javax.swing.BoxLayout(panelCenter, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setViewportView(txtExpresion);

        panelCenter.add(jScrollPane1);

        getContentPane().add(panelCenter, java.awt.BorderLayout.CENTER);

        btnEvaluar.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        btnEvaluar.setText("Evaluar");
        btnEvaluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEvaluarActionPerformed(evt);
            }
        });
        panelEast.add(btnEvaluar);

        getContentPane().add(panelEast, java.awt.BorderLayout.EAST);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblResultado.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        lblResultado.setText("Resultado");
        jPanel4.add(lblResultado);

        txtResultado.setColumns(20);
        txtResultado.setEditable(false);
        jPanel4.add(txtResultado);

        getContentPane().add(jPanel4, java.awt.BorderLayout.PAGE_END);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-479)/2, (screenSize.height-273)/2, 479, 273);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEvaluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEvaluarActionPerformed
        try {
            evaluar();            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, e.getMessage());
        }        
    }//GEN-LAST:event_btnEvaluarActionPerformed
    
    private void evaluar() throws Exception {
        Calc calc = new Calc();
        String exp = txtExpresion.getText();
        txtResultado.setText( String.valueOf(calc.calc(exp)) );
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEvaluar;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblExpresion;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelEast;
    private javax.swing.JPanel panelNorth;
    private javax.swing.JTextArea txtExpresion;
    private javax.swing.JTextField txtResultado;
    // End of variables declaration//GEN-END:variables
    
}
