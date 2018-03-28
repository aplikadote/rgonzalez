/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class ConnectionLoadFileMysql {

    private String url = "jdbc:mysql://localhost/textClasification";
    private String user = "root";
    private String pass = "3instein";
    private Connection conexion;
    private Statement s;
    private int doc_size;
    private int voc_size;
    private double[][] frecuencyMatrix;
    private int[] clusterMatrix;

    public ConnectionLoadFileMysql() {

        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            conexion = DriverManager.getConnection(url, user, pass);
            s = conexion.createStatement();

            loadDocumentSize();
            loadVocabularySize();
            load();

            s.close();
            conexion.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionSaveMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSaveMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDocumentSize() throws SQLException {
        String load = "Select count(*) from documents";
        ResultSet result = s.executeQuery(load);
        result.next();
        doc_size = result.getInt(1);
    }

    private void loadVocabularySize() throws SQLException {
        String load = "Select count(*) from words";
        ResultSet result = s.executeQuery(load);
        result.next();
        voc_size = result.getInt(1);
    }

    private void load() throws SQLException {
        String load = getLoadString();
        ResultSet result = s.executeQuery(load);
        this.frecuencyMatrix = new double[doc_size][voc_size];
        this.clusterMatrix = new int[doc_size];

        while (result.next()) {
            int idDocument = result.getInt("idDocument");
            int idWord = result.getInt("idWord");
            int frecuency = result.getInt("frecuency");
            int idCluster = result.getInt("idCluster");
            frecuencyMatrix[idDocument][idWord] = frecuency;
            clusterMatrix[idDocument] = idCluster;
        }
    }

    private String getLoadString() {
        return "SELECT idDocument, idWord, frecuency, documents.idCluster " +
                "FROM documentlinks, documents, words, clusters " +
                "WHERE documents.theDocument = documentlinks.document " +
                "AND words.theWord = documentlinks.word AND documents.idCluster = clusters.idCluster ";
    }

    public int[] getClusterMatrix() {
        return clusterMatrix;
    }

    public double[][] getFrecuencyMatrix() {
        return frecuencyMatrix;
    }
}
