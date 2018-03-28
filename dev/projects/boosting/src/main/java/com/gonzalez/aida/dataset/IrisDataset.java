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
public class IrisDataset extends ClassificationDataset {
    
    public IrisDataset(String filename, int cols, int clusters, boolean standarize) {
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
                StringTokenizer tk = new StringTokenizer(line, "; ");
                addData(tk, id);
                id++;
                while ((line = in.readLine()) != null) {
                    addData(new StringTokenizer(line, "; "), id);
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
        String token = tk.nextToken();
        int target = Integer.parseInt(token);
        
        double[] x = new double[columns];
        while (tk.hasMoreTokens()) {
            String[] punto = tk.nextToken().split(":");
            int index = Integer.parseInt(punto[0])-1;
            double value = Double.parseDouble(punto[1]);
            x[index] = value;
        }
        this.data.add(new ClassificationInstance(x, target, clusters, id));
    }
}
