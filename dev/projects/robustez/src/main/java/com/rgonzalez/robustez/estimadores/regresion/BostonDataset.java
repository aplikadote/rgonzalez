/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.robustez.estimadores.regresion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrador
 */
public class BostonDataset {

    private List<Instance> data = new ArrayList<Instance>();
    private int columns;

    /**
     * 
     * @param filename
     * @param instances
     * @param columns La cantidad de columnas de la data. Esto incluye a la 
     * columna referente al valor y. Por ejemplo, dim(x) = 13 e dim(y)=1, el
     * valor para "columns" sera 14
     */
    public BostonDataset(String filename) {
        FileReader reader = null;
        try {
//            File file = new File("dataset\\" + filename);
            File file = new File("dataset/" + filename);
            reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader);
            String line;

            line = in.readLine();
            if (line != null) {
                StringTokenizer tk = new StringTokenizer(line, " ,;");
                columns = tk.countTokens();
                addData(tk);
                while ((line = in.readLine()) != null) {
                    addData(new StringTokenizer(line, " ,;"));
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(BostonDataset.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BostonDataset.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(BostonDataset.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addData(StringTokenizer tk) {
        double[] x = new double[columns - 1];
        double y;
        for (int j = 0; j < columns - 1; j++) {
            x[j] = Double.parseDouble(tk.nextToken());
        }
        y = Double.parseDouble(tk.nextToken());
        this.data.add(new Instance(x, y));
    }


    public List<Instance> getData() {
        return data;
    }

}
