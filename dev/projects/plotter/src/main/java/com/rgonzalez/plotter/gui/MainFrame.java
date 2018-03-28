/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.plotter.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class MainFrame extends javax.swing.JFrame {

    private static final int POINTS = 1000;
    private static final double LOWER = -Math.PI;
    private static final double UPPER = Math.PI;
    //
    private ScriptEngine engine;
    private InputPanel panelFrom;
    private InputPanel panelTo;
    private PointsPanel panelPoints;
    private FunctionPanel panelFunction;
    //
    private XYSeriesCollection dataset;
    private XYSeries serie;
    private NumberAxis domainAxis;
    private NumberAxis rangeAxis;
    private XYLineAndShapeRenderer renderer;
    private XYPlot plot;
    private JFreeChart chart;

    public MainFrame() {
        super("Plotter");
        initComponents();
        initValues();
    }

    private void initValues() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        engine = new ScriptEngineManager().getEngineByName("javascript");

        try {
            engine.eval(new InputStreamReader(getClass().getResourceAsStream("/shortcuts.js")));
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }

        this.panelFrom = new InputPanel("Desde", "-5");
        this.panelTo = new InputPanel("Hasta", "5");
        this.panelPoints = new PointsPanel();
        this.panelFunction = new FunctionPanel();

        this.panelParameters.add(panelFrom);
        this.panelParameters.add(panelTo);
        this.panelParameters.add(panelPoints);
        this.panelParameters.add(panelFunction);

        this.dataset = new XYSeriesCollection();
        this.serie = new XYSeries("");
        this.dataset.addSeries(serie);

        this.domainAxis = new NumberAxis();
        this.rangeAxis = new NumberAxis();
        this.renderer = new XYLineAndShapeRenderer(true, false);
        this.plot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
        this.chart = new JFreeChart(plot);
        this.chart.removeLegend();
        PlotChartTheme.applyDefault(chart);

        this.panelCenter.add(new ChartPanel(chart));
    }

    private class InputPanel extends JPanel {

        private Dimension dimLabel = new Dimension(100, 19);
        private Dimension dimText = new Dimension(100, 19);
        private Border borderOk;
        private LineBorder borderBad = new LineBorder(Color.RED);
        //
        private String caption;
        private JTextField text;

        public InputPanel(String caption, String value) {
            this.caption = caption;
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setMaximumSize(new Dimension(2000, 25));
            setMinimumSize(new Dimension(10, 25));

            JLabel label = new JLabel(caption);
            configureAllDims(label, dimLabel);
            add(label);

            text = new JTextField(value);
            borderOk = text.getBorder();
            configureAllDims(text, dimText);
            text.setHorizontalAlignment(JTextField.RIGHT);
            add(text);
        }

        public JTextField getText() {
            return text;
        }

        private Double getValue() {
            try {
                double value = Double.parseDouble(text.getText());
                text.setBorder(borderOk);
                text.setToolTipText("");
                return value;
            } catch (NumberFormatException e) {
                text.setBorder(borderBad);
                String msg = String.format("El valor '%s' debe ser numerico", caption);
                text.setToolTipText(msg);
                return null;
            }

        }
    }

    private class PointsPanel extends JPanel {

        private Dimension dimLabel = new Dimension(100, 19);
        private Dimension dimText = new Dimension(100, 21);
        //
        private JSpinner spinner;

        public PointsPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setMaximumSize(new Dimension(2000, 25));
            setMinimumSize(new Dimension(10, 25));

            JLabel label = new JLabel("Puntos");
            configureAllDims(label, dimLabel);
            add(label);

            spinner = new JSpinner(new SpinnerNumberModel(100, 2, 1000, 1));
            add(spinner);
        }

        public JSpinner getSpinner() {
            return spinner;
        }

        public Integer getValue() {
            return (Integer) spinner.getValue();
        }
    }

    private class FunctionPanel extends JPanel {

        private Dimension dimLabel = new Dimension(100, 19);
        private Border borderOk;
        private LineBorder borderBad = new LineBorder(Color.RED);
        //
        private JTextArea textarea;

        public FunctionPanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            setMaximumSize(new Dimension(2000, 100));
            setMinimumSize(new Dimension(10, 100));

            JLabel label = new JLabel("f(x) = ");
            configureAllDims(label, dimLabel);
            add(label);

            textarea = new JTextArea(5, 40);
            textarea.setText("Math.pow(x,2)");
            borderOk = textarea.getBorder();
            add(new JScrollPane(textarea));
        }

        public JTextArea getTextarea() {
            return textarea;
        }

        public Object getValue() {
            try {
                String functionStr = String.format("var plotFunction = function(x){ return %s; }", textarea.getText());
                engine.eval(functionStr);
                textarea.setBorder(borderOk);
                textarea.setToolTipText("");
                return "ok";
            } catch (ScriptException ex) {
                textarea.setBorder(borderBad);
                textarea.setToolTipText("Funcion no evaluable");
                return null;
            }
        }
    }

    public void configureAllDims(Component c, Dimension dim) {
        c.setPreferredSize(dim);
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }

    public void configureMaxMinDims(Component c, Dimension dim) {
        c.setMaximumSize(dim);
        c.setMinimumSize(dim);
    }

    public void showMe() {
        this.setSize(1000, 700);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void updatePlot() {
        Object[] results = new Object[]{
            panelFrom.getValue(),
            panelTo.getValue(),
            panelPoints.getValue(),
            panelFunction.getValue()
        };

        boolean ok = true;
        for (Object result : results) {
            if (result == null) {
                ok = false;
                break;
            }
        }

        if (!ok) {
            return;
        }

        double from = (Double) results[0];
        double to = (Double) results[1];
        int points = (Integer) results[2];
        ScriptObjectMirror mirror = (ScriptObjectMirror) engine.get("plotFunction");

        if (from > to) {
            String msg = String.format("Limites incorrectos: %s > %s", from, to);
            JOptionPane.showMessageDialog(this, msg);
            return;
        }

        try {
            mirror.call(null, from);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            return;
        }

        serie.clear();
        double rate = (to - from) / (double) points;
        for (int i = 0; i < points; i++) {
            double x = rate * i + from;
            double y = (Double) mirror.call(null, x);
            serie.add(x, y);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        split = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        panelParameters = new javax.swing.JPanel();
        panelParametersButtons = new javax.swing.JPanel();
        buttonUpdate = new javax.swing.JButton();
        panelCenter = new javax.swing.JPanel();
        panelButtons = new javax.swing.JPanel();
        buttonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        split.setDividerLocation(210);
        split.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        split.setOneTouchExpandable(true);

        jPanel1.setLayout(new java.awt.BorderLayout());

        panelParameters.setLayout(new javax.swing.BoxLayout(panelParameters, javax.swing.BoxLayout.Y_AXIS));
        jPanel1.add(panelParameters, java.awt.BorderLayout.CENTER);

        buttonUpdate.setText("Actualizar");
        buttonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateActionPerformed(evt);
            }
        });
        panelParametersButtons.add(buttonUpdate);

        jPanel1.add(panelParametersButtons, java.awt.BorderLayout.SOUTH);

        split.setTopComponent(jPanel1);

        panelCenter.setLayout(new javax.swing.BoxLayout(panelCenter, javax.swing.BoxLayout.LINE_AXIS));
        split.setBottomComponent(panelCenter);

        getContentPane().add(split, java.awt.BorderLayout.CENTER);

        panelButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        buttonClose.setText("Cerrar");
        buttonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCloseActionPerformed(evt);
            }
        });
        panelButtons.add(buttonClose);

        getContentPane().add(panelButtons, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_buttonCloseActionPerformed

    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
        updatePlot();
    }//GEN-LAST:event_buttonUpdateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
//                VerosimilFunction verosimil = new VerosimilFunction(
//                        //                        new GaussianFunction(0,1),
//                        Util.getUniformRandomData(-Math.PI, Math.PI, 30)
//                );
//                ExponentialFunction exponential = new ExponentialFunction(0.2);
                MainFrame frame = new MainFrame();
                frame.showMe();
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonClose;
    private javax.swing.JButton buttonUpdate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelParameters;
    private javax.swing.JPanel panelParametersButtons;
    private javax.swing.JSplitPane split;
    // End of variables declaration//GEN-END:variables
}
