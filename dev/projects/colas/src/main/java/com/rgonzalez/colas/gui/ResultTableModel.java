/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.colas.gui;

import com.rgonzalez.colas.fel.TimeNode;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * El abstractModel para la tabla de resultados.
 *
 * @author Administrador
 */
public class ResultTableModel extends AbstractTableModel {

    /**
     * La lista de datos
     */
    private ArrayList<TimeNode> data;

    /**
     * Constructor de ResultTableModel
     *
     * @param data La lista de datos
     */
    public ResultTableModel(ArrayList<TimeNode> data) {
        this.data = data;
    }

    /**
     * Numero de filas de la tabla
     *
     * @return El numero de filas de la tabla
     */
    public int getRowCount() {
        return this.data.size();
    }

    /**
     * El numero de columnas de la tabla.
     *
     * @return El numero de columnas de la tabla.
     */
    public int getColumnCount() {
        return 6 + 1;
    }

    /**
     * La clase asociada a cada columna
     *
     * @param columnIndex La columna
     * @return La clase asociada a la columna.
     */
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return Integer.class;
        } else {
            return Double.class;
        }
    }

    /**
     * Retorna si la celda especificada es editable o no. Para esta caso
     * particular, ninguna es editable.
     *
     * @param rowIndex La fila.
     * @param columnIndex La columna.
     * @return Siempre False.
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    /**
     * El nombre de cada columna que aparacera en la cabecera
     *
     * @param column La columna.
     * @return Un string con el nombre de la columna asociada.
     */
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Numero";
            case 1:
                return "Tiempo entre Llegadas";
            case 2:
                return "Tiempo de Llegadas";
            case 3:
                return "Tiempo en Cola";
            case 4:
                return "Tiempo de Servicio";
            case 5:
                return "Tiempo Sistema";
            case 6:
                return "Tiempo de Salida";
            default:
                return "";
        }
    }

    /**
     * Retorna el valor de la celda especificada. Esto se obtiene de la lista de
     * datos.
     *
     * @param rowIndex La fila.
     * @param columnIndex La columna.
     * @return Un objecto con el valor asociado a la celda.
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        TimeNode node = data.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return new Integer(rowIndex + 1);
            case 1:
                return new Double(node.getTiempoEntreLlegadas());
            case 2:
                return new Double(node.getTiempoDeLlegada());
            case 3:
                return new Double(node.getTiempoEnCola());
            case 4:
                return new Double(node.getTiempoDeServicio());
            case 5:
                return new Double(node.getTiempoEnElSistema());
            case 6:
                return new Double(node.getTiempoDeSalida());
            default:
                return null;
        }
    }

}
