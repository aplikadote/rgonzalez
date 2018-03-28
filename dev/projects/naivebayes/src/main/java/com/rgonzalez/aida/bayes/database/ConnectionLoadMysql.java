/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.database;

import com.rgonzalez.aida.bayes.core.Document;
import com.rgonzalez.aida.bayes.core.Word;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class ConnectionLoadMysql {

    private String url = "jdbc:mysql://localhost/textClasification";
    private String user = "root";
    private String pass = "3instein";
    private Connection conexion;
    private Statement s;
    private TreeSet<Word> vocabulary;
    private TreeSet<Document> documents;

    public ConnectionLoadMysql() {
        this.documents = new TreeSet<Document>();
        this.vocabulary = new TreeSet<Word>();
        
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            conexion = DriverManager.getConnection(url, user, pass);
            s = conexion.createStatement();

            loadDocuments();
            loadVocabulary();
            loadWordsToDocuments();
            
            s.close();
            conexion.close();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionSaveMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionSaveMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadDocuments() throws SQLException {
        String loadDocument = "Select * from documents";
        ResultSet result = s.executeQuery(loadDocument);
        
        while(result.next()){
            int idDocument = result.getInt("idDocument");
            String filename = result.getString("theDocument");
            Document doc = new Document(filename);
            doc.setIdDocument(idDocument);
            this.documents.add(doc);
        }
    }
    
    public void loadVocabulary() throws SQLException {
        String loadDocument = "Select * from words";
        ResultSet result = s.executeQuery(loadDocument);
        
        while(result.next()){
            int idWord = result.getInt("idWord");
            String wordString = result.getString("theWord");
            Word word = new Word(wordString);
            word.setIdWord(idWord);
            this.vocabulary.add(word);
        }
    }
    
    public void loadWordsToDocuments() throws SQLException{
        Iterator<Document> itDoc = this.documents.iterator();
        while(itDoc.hasNext()){
            Document doc = itDoc.next();
            
            String loadDocument = "Select word, frecuency from documentlinks where document = '"+ doc.getFilename() + "'";
            ResultSet result = s.executeQuery(loadDocument);
            while(result.next()){
                String wordString = result.getString("word");
                int frecuency = result.getInt("frecuency");
                Word word = this.vocabulary.ceiling(new Word(wordString));
                doc.putWord(word, frecuency);
                word.addDocument(doc);
            }
        }
    }
    
    public TreeSet<Document> getDocuments(){
        return this.documents;
    }
    
}
