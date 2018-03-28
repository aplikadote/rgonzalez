/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gonzalez.aida.dataset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author Administrador
 */
public class WisconsinDataset extends ClassificationDataset {


    public WisconsinDataset(String filename, int cols, int clusters, boolean standarize) {
        super(filename, cols, clusters, standarize);
    }
    
    public void parseAndFillData(String filename) {
        FileReader reader = null;
        try {
            File file = new File("dataset\\" + filename);
            reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader);
            String line;
            line = in.readLine();
            
            if (line != null) {
                int id = 1;
                StringTokenizer tk = new StringTokenizer(line, ", ");
                addData(tk, id);
                id++;
                while ((line = in.readLine()) != null) {
                    addData(new StringTokenizer(line, ", "), id);
                    id++;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void addData(StringTokenizer tk, int id) {
        int id_instancia = Integer.parseInt(tk.nextToken()); // sin uso
        String targetString = tk.nextToken();
        int target = -1;
        if( targetString.compareTo("M")==0){
            target=1;
        }
        else{
            target=-1;
        }
        
        double[] x = new double[columns];
        for (int i=0; tk.hasMoreTokens(); i++) {
            x[i] = Double.parseDouble(tk.nextToken());
        }
        this.data.add(new ClassificationInstance(x, target, clusters, id));
    }

}
