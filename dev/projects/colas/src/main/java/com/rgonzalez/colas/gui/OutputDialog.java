/*
 * OutputDialog.java
 *
 * Created on 26 de julio de 2008, 23:48
 */

package com.rgonzalez.colas.gui;

import com.rgonzalez.colas.distributions.IDistribution;
import com.rgonzalez.colas.fel.SimulacionFEL;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Antes de presentar la ventana de resultados, primero se realiza
 * la simulacion FEL, encapsulada en la clase "SimulacionFEL". Luego
 * de terminar, se presenta los resultados.
 * @author  Administrador
 */
public class OutputDialog extends javax.swing.JDialog {

    private SimulacionFEL simulacion;
    
    public OutputDialog(java.awt.Frame parent, boolean modal, double confidence, IDistribution distArrive, IDistribution distService) {
        super(parent, "Resultados de la Simulacion", modal);
        initComponents();
        
        this.simulacion = new SimulacionFEL(confidence, distArrive, distService);
        int x = simulacion.getUserNumber();
        JOptionPane.showMessageDialog(this,"El Nivel de Confianza ha sido alcanzado"+'\n'+"Numero de Replicas "+x);
        
        ResultTableModel model = new ResultTableModel( simulacion.getArrayTimeNode() );
        this.tableResults.setModel(model);
        
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        this.tableResults.setDefaultRenderer(Double.class, cellRenderer);
        this.tableResults.setDefaultRenderer(Integer.class, cellRenderer);
        
        this.tableResults.getColumnModel().getColumn(0).setPreferredWidth(30);
        
        this.pack();
        this.setSize( this.getWidth() + 500, this.getHeight());
        Util.center(this);
        this.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     * Metodo autogenerado por Netbeans para crear los componentes
     * iniciales de la ventana.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCenter = new javax.swing.JPanel();
        scrollTableResults = new javax.swing.JScrollPane();
        tableResults = new javax.swing.JTable();
        panelSouth = new javax.swing.JPanel();
        buttonMesures = new javax.swing.JButton();
        buttonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelCenter.setLayout(new javax.swing.BoxLayout(panelCenter, javax.swing.BoxLayout.LINE_AXIS));

        tableResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        scrollTableResults.setViewportView(tableResults);

        panelCenter.add(scrollTableResults);

        getContentPane().add(panelCenter, java.awt.BorderLayout.CENTER);

        panelSouth.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        buttonMesures.setText("Ver Medidas");
        buttonMesures.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonMesuresActionPerformed(evt);
            }
        });
        panelSouth.add(buttonMesures);

        buttonClose.setText("Cerrar");
        buttonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCloseActionPerformed(evt);
            }
        });
        panelSouth.add(buttonClose);

        getContentPane().add(panelSouth, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void buttonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCloseActionPerformed
    this.dispose();
}//GEN-LAST:event_buttonCloseActionPerformed

/**
 * Se muestra una ventana con los indicadores del sistema.
 * @param evt El evento
 */
private void buttonMesuresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonMesuresActionPerformed
    new MesuresDialog(this, true, simulacion);
}//GEN-LAST:event_buttonMesuresActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClose;
    private javax.swing.JButton buttonMesures;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelSouth;
    private javax.swing.JScrollPane scrollTableResults;
    private javax.swing.JTable tableResults;
    // End of variables declaration//GEN-END:variables

}
