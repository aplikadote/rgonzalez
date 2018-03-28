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
public class BostonDataset extends RegresionDataset {

    public BostonDataset(String filename) {
        super(filename);
    }

    public void parseAndFillData(String filename) {
        FileReader reader = null;
        try {
//            File file = new File("dataset\\" + filename);
            File file = new File("dataset/" + filename);
            reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader);
            String line;
            line = in.readLine();
            if (line != null) {
                int id=1;
                StringTokenizer tk = new StringTokenizer(line, " ,;");
                columns = tk.countTokens() - 1;
                addData(tk, id);
                id++;
                while ((line = in.readLine()) != null) {
                    addData(new StringTokenizer(line, " ,;"), id);
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

    public void addData(StringTokenizer tk, int id) {
        double[] x = new double[columns];
        double y;
        for (int j = 0; j < columns; j++) {
            x[j] = Double.parseDouble(tk.nextToken());
        }
        y = Double.parseDouble(tk.nextToken());
        this.data.add(new RegresionInstance(x, y, id));
    }
}
