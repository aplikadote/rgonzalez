/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.dataset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author rene
 */
public class RegresionDataset extends DefaultDataset {

    public RegresionDataset(String filename) {
        this.dataList = new ArrayList<RegresionInstance>();
        FileReader reader = null;
        try {
            File file = new File("dataset" + File.separator + filename);
            reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader);
            String line;

            line = in.readLine();
            if (line != null) {
                StringTokenizer tk = new StringTokenizer(line, " ,;");
                xDataSize = tk.countTokens() - 1;
                addData(tk);
                while ((line = in.readLine()) != null) {
                    addData(new StringTokenizer(line, " ,;"));
                }

                this.dataCount = dataList.size();

                this.unorderData();
            } else {
                this.dataCount = 0;
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

    public void addData(StringTokenizer tk) {
        double[] x = new double[xDataSize];
        double y;
        for (int j = 0; j < xDataSize; j++) {
            x[j] = Double.parseDouble(tk.nextToken());
        }
        y = Double.parseDouble(tk.nextToken());
        dataList.add(new RegresionInstance(x, y));
    }

}
