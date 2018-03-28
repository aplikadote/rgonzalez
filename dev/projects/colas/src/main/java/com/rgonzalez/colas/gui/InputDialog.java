/*
 * InputDialog.java
 *
 * Created on 26 de julio de 2008, 0:38
 */
package com.rgonzalez.colas.gui;

import com.rgonzalez.colas.distributions.Beta;
import com.rgonzalez.colas.distributions.Binomial;
import com.rgonzalez.colas.distributions.BinomialNegativa;
import com.rgonzalez.colas.distributions.Erlang;
import com.rgonzalez.colas.distributions.Exponencial;
import com.rgonzalez.colas.distributions.Gamma;
import com.rgonzalez.colas.distributions.Geometrica;
import com.rgonzalez.colas.distributions.Hipergeometrica;
import com.rgonzalez.colas.distributions.IDistribution;
import com.rgonzalez.colas.distributions.Normal;
import com.rgonzalez.colas.distributions.Pascal;
import com.rgonzalez.colas.distributions.Poisson;
import com.rgonzalez.colas.distributions.UniformeContinua;
import com.rgonzalez.colas.distributions.UniformeDiscreta;
import com.rgonzalez.colas.distributions.Weibull;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Clase principal donde se presenta el ingreso de datos para las distribuciones
 * de llegada y de servicio del sistema.
 *
 * @author Administrador
 */
public class InputDialog extends javax.swing.JFrame {

    /**
     * Lista de Distribuciones posibles a asignar a la llegada de usuarios al sistema
     */
    private ArrayList<IDistribution> distribucionesArrive;
    /**
     * Lista de Distribucions posibles para los tiempos de servicio del sistema
     */
    private ArrayList<IDistribution> distribucionesService;

    /**
     * Constructor de la clase
     */
    public InputDialog() {
        initComponents();
        IDistribution dist;

        this.setTitle("Simulacion orientada a Eventos Discretos");

        this.textConfidence.setText("50");

        this.distribucionesArrive = new ArrayList<IDistribution>();
        agregarDistribuciones(distribucionesArrive);

        this.comboArrive.removeAllItems();
        for (int i = 0; i < distribucionesArrive.size(); i++) {
            this.comboArrive.addItem(distribucionesArrive.get(i));
        }
        dist = (IDistribution) comboArrive.getSelectedItem();
        labelArriveParameters.setText(dist.getStringParameters());

        this.comboArrive.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        IDistribution dist = (IDistribution) comboArrive.getSelectedItem();
                        labelArriveParameters.setText(dist.getStringParameters());
                    }
                });

        this.distribucionesService = new ArrayList<IDistribution>();
        agregarDistribuciones(distribucionesService);

        this.comboService.removeAllItems();
        for (int i = 0; i < distribucionesService.size(); i++) {
            this.comboService.addItem(distribucionesService.get(i));
        }
        dist = (IDistribution) comboService.getSelectedItem();
        labelServiceParameters.setText(dist.getStringParameters());

        this.comboService.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        IDistribution dist = (IDistribution) comboService.getSelectedItem();
                        labelServiceParameters.setText(dist.getStringParameters());
                    }
                });

        this.pack();
        this.setSize(this.getWidth() + 100, this.getHeight() + 20);
        Util.center(this);
        this.setVisible(true);
    }

    /**
     * Agrega todas las distribuciones creadas a la lista especificada de
     * distribuciones.
     *
     * @param array La lista en donde agregar las distribuciones.
     */
    public void agregarDistribuciones(ArrayList<IDistribution> array) {
        array.add(new Binomial());
        array.add(new Poisson());
        array.add(new BinomialNegativa());
        array.add(new Geometrica());
        array.add(new Hipergeometrica());
        array.add(new UniformeDiscreta());
        array.add(new Pascal());
        array.add(new Exponencial());
        array.add(new Normal());
        array.add(new Erlang());
        array.add(new Gamma());
        array.add(new Beta());
        array.add(new Weibull());
        array.add(new UniformeContinua());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor. Metodo autogenerado por Netbeans para
     * crear los componentes iniciales de la ventana.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        northPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        centerPanel = new javax.swing.JPanel();
        confidencePanel = new javax.swing.JPanel();
        panelLabelConfidence = new javax.swing.JPanel();
        labelConfidence = new javax.swing.JLabel();
        panelTextConfidence = new javax.swing.JPanel();
        textConfidence = new javax.swing.JTextField();
        arrivePanel = new javax.swing.JPanel();
        panelComboArrive = new javax.swing.JPanel();
        comboArrive = new javax.swing.JComboBox();
        panelArriveParameters = new javax.swing.JPanel();
        panelTitleArriveParameters = new javax.swing.JPanel();
        titleArriveParameters = new javax.swing.JLabel();
        panelLabelArriveParameters = new javax.swing.JPanel();
        labelArriveParameters = new javax.swing.JLabel();
        panelButtonArrive = new javax.swing.JPanel();
        buttonArrive = new javax.swing.JButton();
        servicePanel = new javax.swing.JPanel();
        panelComboService = new javax.swing.JPanel();
        comboService = new javax.swing.JComboBox();
        panelServiceParameters = new javax.swing.JPanel();
        panelTitleServiceParameters = new javax.swing.JPanel();
        titleServiceParameters = new javax.swing.JLabel();
        panelLabelServiceParamters = new javax.swing.JPanel();
        labelServiceParameters = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        buttonService = new javax.swing.JButton();
        southPanel = new javax.swing.JPanel();
        buttonGo = new javax.swing.JButton();
        buttonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        northPanel.setMinimumSize(new java.awt.Dimension(202, 30));
        northPanel.setPreferredSize(new java.awt.Dimension(100, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Simulacion Cola Eventos Discretos");
        northPanel.add(jLabel1);

        getContentPane().add(northPanel, java.awt.BorderLayout.NORTH);

        centerPanel.setLayout(new javax.swing.BoxLayout(centerPanel, javax.swing.BoxLayout.Y_AXIS));

        confidencePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Intervalo de Confianza"));
        confidencePanel.setLayout(new javax.swing.BoxLayout(confidencePanel, javax.swing.BoxLayout.LINE_AXIS));

        labelConfidence.setText("Intervalo de Confianza");
        panelLabelConfidence.add(labelConfidence);

        confidencePanel.add(panelLabelConfidence);

        textConfidence.setPreferredSize(new java.awt.Dimension(100, 20));
        panelTextConfidence.add(textConfidence);

        confidencePanel.add(panelTextConfidence);

        centerPanel.add(confidencePanel);

        arrivePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Distribucion de Llegada"));
        arrivePanel.setLayout(new javax.swing.BoxLayout(arrivePanel, javax.swing.BoxLayout.X_AXIS));

        panelComboArrive.setPreferredSize(new java.awt.Dimension(150, 50));

        comboArrive.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelComboArrive.add(comboArrive);

        arrivePanel.add(panelComboArrive);

        panelArriveParameters.setLayout(new javax.swing.BoxLayout(panelArriveParameters, javax.swing.BoxLayout.Y_AXIS));

        panelTitleArriveParameters.setPreferredSize(new java.awt.Dimension(200, 25));

        titleArriveParameters.setText("Parametros Actuales");
        panelTitleArriveParameters.add(titleArriveParameters);

        panelArriveParameters.add(panelTitleArriveParameters);

        panelLabelArriveParameters.setPreferredSize(new java.awt.Dimension(200, 25));

        labelArriveParameters.setText("<Parametros>");
        panelLabelArriveParameters.add(labelArriveParameters);

        panelArriveParameters.add(panelLabelArriveParameters);

        arrivePanel.add(panelArriveParameters);

        panelButtonArrive.setPreferredSize(new java.awt.Dimension(150, 50));

        buttonArrive.setText("Configurar");
        buttonArrive.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonArriveActionPerformed(evt);
            }
        });
        panelButtonArrive.add(buttonArrive);

        arrivePanel.add(panelButtonArrive);

        centerPanel.add(arrivePanel);

        servicePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Distribucion de Servicio"));
        servicePanel.setLayout(new javax.swing.BoxLayout(servicePanel, javax.swing.BoxLayout.X_AXIS));

        panelComboService.setPreferredSize(new java.awt.Dimension(150, 50));

        comboService.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        panelComboService.add(comboService);

        servicePanel.add(panelComboService);

        panelServiceParameters.setLayout(new javax.swing.BoxLayout(panelServiceParameters, javax.swing.BoxLayout.Y_AXIS));

        panelTitleServiceParameters.setPreferredSize(new java.awt.Dimension(200, 25));

        titleServiceParameters.setText("Parametros Actuales");
        panelTitleServiceParameters.add(titleServiceParameters);

        panelServiceParameters.add(panelTitleServiceParameters);

        panelLabelServiceParamters.setPreferredSize(new java.awt.Dimension(200, 25));

        labelServiceParameters.setText("<Parametros>");
        panelLabelServiceParamters.add(labelServiceParameters);

        panelServiceParameters.add(panelLabelServiceParamters);

        servicePanel.add(panelServiceParameters);

        jPanel11.setPreferredSize(new java.awt.Dimension(150, 50));

        buttonService.setText("Configurar");
        buttonService.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonServiceActionPerformed(evt);
            }
        });
        jPanel11.add(buttonService);

        servicePanel.add(jPanel11);

        centerPanel.add(servicePanel);

        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

        southPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        buttonGo.setText("Iniciar");
        buttonGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGoActionPerformed(evt);
            }
        });
        southPanel.add(buttonGo);

        buttonClose.setText("Cerrar");
        buttonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCloseActionPerformed(evt);
            }
        });
        southPanel.add(buttonClose);

        getContentPane().add(southPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Action seteada al clickear sobre el boton "Cerrar". La ventana se cierra.
     *
     * @param evt El evento
     */
private void buttonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCloseActionPerformed
    this.dispose();
}//GEN-LAST:event_buttonCloseActionPerformed

    /**
     * Al apretar el boton "Configurar" del panel de configuracion para la
     * distribucion de llegada, se lanza una nueva ventana que es la encargada
     * de capturar los datos de distribucion que se ha elegido en el ComboBox.
     *
     * @param evt El evento
     */
private void buttonArriveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonArriveActionPerformed
    IDistribution distConf = (IDistribution) this.comboArrive.getSelectedItem();
    new ConfigDialog(this, true, distConf);
    this.labelArriveParameters.setText(distConf.getStringParameters());
}//GEN-LAST:event_buttonArriveActionPerformed

    /**
     * Al apretar el boton "Configurar" del panel de configuracion para la
     * distribucion de servicio, se lanza una nueva ventana que es la encargada
     * de capturar los datos de distribucion que se ha elegido en el ComboBox .
     *
     * @param evt El evento
     */
private void buttonServiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonServiceActionPerformed
    IDistribution distConf = (IDistribution) this.comboService.getSelectedItem();
    new ConfigDialog(this, true, distConf);
    this.labelServiceParameters.setText(distConf.getStringParameters());
}//GEN-LAST:event_buttonServiceActionPerformed

    /**
     * Inicia la simulacion FEL. Chequea si el nivel de confianza esta
     * correctamente inicializado y luego despliega una ventana en donde se
     * presentaran los resultados.
     *
     * @param evt El evento.
     */
private void buttonGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGoActionPerformed
    try {
        double confidence = Double.parseDouble(this.textConfidence.getText());

        if (confidence < 0 || confidence > 100) {
            JOptionPane.showMessageDialog(this, "El intervalo de confianza debe estar entre 0 y 100", "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            IDistribution distArrive = (IDistribution) this.comboArrive.getSelectedItem();
            IDistribution distService = (IDistribution) this.comboService.getSelectedItem();
            new OutputDialog(this, true, confidence, distArrive, distService);
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Ingrese numeros reales para el intervalo de confianza ", "Error", JOptionPane.WARNING_MESSAGE);
    }
}//GEN-LAST:event_buttonGoActionPerformed

    /**
     * El metodo main.
     *
     * @param args Los argumentos de entrada
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new InputDialog();
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel arrivePanel;
    private javax.swing.JButton buttonArrive;
    private javax.swing.JButton buttonClose;
    private javax.swing.JButton buttonGo;
    private javax.swing.JButton buttonService;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JComboBox comboArrive;
    private javax.swing.JComboBox comboService;
    private javax.swing.JPanel confidencePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JLabel labelArriveParameters;
    private javax.swing.JLabel labelConfidence;
    private javax.swing.JLabel labelServiceParameters;
    private javax.swing.JPanel northPanel;
    private javax.swing.JPanel panelArriveParameters;
    private javax.swing.JPanel panelButtonArrive;
    private javax.swing.JPanel panelComboArrive;
    private javax.swing.JPanel panelComboService;
    private javax.swing.JPanel panelLabelArriveParameters;
    private javax.swing.JPanel panelLabelConfidence;
    private javax.swing.JPanel panelLabelServiceParamters;
    private javax.swing.JPanel panelServiceParameters;
    private javax.swing.JPanel panelTextConfidence;
    private javax.swing.JPanel panelTitleArriveParameters;
    private javax.swing.JPanel panelTitleServiceParameters;
    private javax.swing.JPanel servicePanel;
    private javax.swing.JPanel southPanel;
    private javax.swing.JTextField textConfidence;
    private javax.swing.JLabel titleArriveParameters;
    private javax.swing.JLabel titleServiceParameters;
    // End of variables declaration//GEN-END:variables

}