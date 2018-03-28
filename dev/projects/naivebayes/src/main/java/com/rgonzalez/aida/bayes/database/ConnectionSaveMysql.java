/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.database;

import com.rgonzalez.aida.bayes.core.Cluster;
import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.FilterManager;
import com.rgonzalez.aida.bayes.core.Word;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class ConnectionSaveMysql {

    private String url = "jdbc:mysql://localhost/textClasification";
    private String user = "root";
    private String pass = "3instein";
    private TreeSet<Word> vocabulary;
    private TreeSet<Document> documents;

    public ConnectionSaveMysql(FilterManager manager) {
        this.vocabulary = manager.getVocabulary();
        this.documents = manager.getDocuments();

        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            Connection conexion = DriverManager.getConnection(url, user, pass);
            Statement s = conexion.createStatement();

            s.execute("Delete words.* from words");
            String vocabularyQuery = getVocabularyQuery();
            s.execute(vocabularyQuery);

            s.execute("Delete documents.* from documents");
            String documentQuery = getDocumentQuery();
            s.execute(documentQuery);

            s.execute("Delete documentlinks.* from documentlinks");
            String documentLinksQuery = getDocumentLinksQuery();
            s.execute(documentLinksQuery);

            s.execute("Delete clusters.* from clusters");
            String clusterQuery = getClusterQuery();
            s.execute(clusterQuery);

            s.close();
            conexion.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionSaveMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSaveMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getVocabularyQuery() {
        StringBuffer query = new StringBuffer();
        query.append("Insert Into words values ");

        Iterator<Word> it = vocabulary.iterator();
        int i=0;
        while (it.hasNext()) {
            Word word = it.next();
            StringBuffer wordQuery = new StringBuffer();
            wordQuery.append("(");
            wordQuery.append(i);
            wordQuery.append(",");
            wordQuery.append("\"");
            wordQuery.append(word.toString());
            wordQuery.append("\")");
            wordQuery.append(",");
            query.append(wordQuery);
            i++;
        }
        query.delete(query.length() - 1, query.length());
        return query.toString();
    }

    private String getDocumentQuery() {
        StringBuffer query = new StringBuffer();
        query.append("Insert Into documents values ");

        Iterator<Document> it = documents.iterator();
        int i=0;
        while (it.hasNext()) {
            Document doc = it.next();
            StringBuffer docQuery = new StringBuffer();
            docQuery.append("(");
            docQuery.append(i);
            docQuery.append(",");
            docQuery.append("\"");
            docQuery.append(doc.getFilename());
            docQuery.append("\"");
            docQuery.append(",");
            docQuery.append("\"");
            docQuery.append(doc.getClusterCod());
            docQuery.append("\"");
            docQuery.append(")");
            docQuery.append(",");
            query.append(docQuery);
            i++;
        }
        query.delete(query.length() - 1, query.length());
        return query.toString();
    }

    private String getDocumentLinksQuery() {
        StringBuffer query = new StringBuffer();
        query.append("Insert Into documentlinks values ");

        Iterator<Document> it = documents.iterator();
        while (it.hasNext()) {
            Document doc = it.next();

            TreeMap<Word,Integer> map = doc.getMapFrecuency();
            Iterator<Word> itWord = map.keySet().iterator();
            while (itWord.hasNext()) {
                Word word = itWord.next();

                StringBuffer docLinkQuery = new StringBuffer();
                docLinkQuery.append("(");
                docLinkQuery.append("\"");
                docLinkQuery.append(doc.getFilename());
                docLinkQuery.append("\"");
                docLinkQuery.append(",");
                docLinkQuery.append("\"");
                docLinkQuery.append(word.toString());
                docLinkQuery.append("\"");
                docLinkQuery.append(",");
                docLinkQuery.append(map.get(word).intValue());
                docLinkQuery.append(")");
                docLinkQuery.append(",");
                query.append(docLinkQuery);
            }
        }
        query.delete(query.length() - 1, query.length());
        return query.toString();
    }

    private String getClusterQuery() {
        StringBuffer query = new StringBuffer();
        query.append("Insert Into clusters values ");

        for (int i = 0; i < Cluster.n_cluster; i++) {
            StringBuffer docLinkQuery = new StringBuffer();
            docLinkQuery.append("(");
            docLinkQuery.append(Cluster.CLUSTER_COD[i]);
            docLinkQuery.append(",");
            docLinkQuery.append("\"");
            docLinkQuery.append(Cluster.CLUSTER_STRING[i]);
            docLinkQuery.append("\"");
            docLinkQuery.append(")");
            docLinkQuery.append(",");
            query.append(docLinkQuery);
        }
        query.delete(query.length() - 1, query.length());
        return query.toString();
    }

}

