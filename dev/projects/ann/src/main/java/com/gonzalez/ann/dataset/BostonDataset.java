/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.dataset;

import com.gonzalez.ann.dataset.RegresionInstance;
import com.gonzalez.ann.dataset.Dataset;
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
public class BostonDataset{

    private List<RegresionInstance> data = new ArrayList<RegresionInstance>();
    private List<RegresionInstance> minMaxData = new ArrayList<RegresionInstance>();
    private List<RegresionInstance> stdData = new ArrayList<RegresionInstance>();
    private List<RegresionInstance> trainMinMaxData = new ArrayList<RegresionInstance>();
    private List<RegresionInstance> testMinMaxData = new ArrayList<RegresionInstance>();
    private List<RegresionInstance> trainStdData = new ArrayList<RegresionInstance>();
    private List<RegresionInstance> testStdData = new ArrayList<RegresionInstance>();
    private int dataSize;
    private int testSize;
    private int trainSize;
    private int columns;

    public BostonDataset(){
        init("boston.data");
    }

    public BostonDataset(String filename){
        init(filename);
    }

    /**
     * 
     * @param filename
     * @param instances
     * @param columns La cantidad de columnas de la data. Esto incluye a la 
     * columna referente al valor y. Por ejemplo, dim(x) = 13 e dim(y)=1, el
     * valor para "columns" sera 14
     */
    public void init(String filename) {
        FileReader reader = null;
        try {
            File file = new File("dataset", filename);
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
                dataSize = data.size();
                trainSize = (int) (dataSize * 0.8);
                testSize = dataSize - trainSize;

                setMinMaxDataset();
                setStdDataset();
                
                for (int i = 0; i < trainSize; i++) {
                    trainMinMaxData.add(minMaxData.get(i));
                    trainStdData.add(stdData.get(i));
                }
                
                for (int i = 0; i < testSize; i++) {
                    testMinMaxData.add(minMaxData.get(i+trainSize));
                    testStdData.add(stdData.get(i+trainSize));
                }

            } else {
                dataSize = 0;
                trainSize = 0;
                testSize = 0;
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
        this.data.add(new RegresionInstance(x, y));
    }

    public BostonDataset(List<RegresionInstance> data) {
        if (data != null && data.size() != 0) {
            this.data = data;
            this.dataSize = data.size();
            this.columns = data.get(0).getX().length + 1;
        } else {
            this.data = null;
            this.dataSize = 0;
            this.columns = 0;
        }
    }

    public List<RegresionInstance> getRealDataset() {
        return data;
    }

    public List<RegresionInstance> getTrainMinMaxDataset() {
        return trainMinMaxData;
    }

    public List<RegresionInstance> getTestMinMaxDataset() {
        return testMinMaxData;
    }

    public List<RegresionInstance> getTrainStdDataset() {
        return trainStdData;
    }

    public List<RegresionInstance> getTestStdDataset() {
        return testStdData;
    }

    private void setMinMaxDataset() {
        int p = data.get(0).getX().length;
        int size = data.size();

        for (int i = 0; i < size; i++) {
            minMaxData.add(new RegresionInstance(data.get(i).getX(), data.get(i).getY()));
        }

        double[] min = new double[p];
        double[] max = new double[p];

        for (int j = 0; j < p; j++) {
            min[j] = Double.POSITIVE_INFINITY;
            max[j] = Double.NEGATIVE_INFINITY;

            for (int i = 0; i < size; i++) {
                double comp = minMaxData.get(i).getX()[j];
                if (Double.compare(comp, min[j]) < 0) {
                    min[j] = comp;
                }

                if (comp > max[j]) {
                    max[j] = comp;
                }
            }
        }

        for (int j = 0; j < p; j++) {
            for (int i = 0; i < size; i++) {
                if (Double.compare(max[j], min[j]) == 0) {
                    minMaxData.get(i).getX()[j] = max[j];
                } else {
                    double comp = minMaxData.get(i).getX()[j];
                    minMaxData.get(i).getX()[j] = ((2 / (max[j] - min[j])) * (comp - min[j])) - 1;
                }
            }
        }

        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < size; i++) {
            double comp = minMaxData.get(i).getY();
            if (comp < minY) {
                minY = comp;
            }
            if (comp > maxY) {
                maxY = comp;
            }
        }

        for (int i = 0; i < size; i++) {
            RegresionInstance sample = minMaxData.get(i);
            if (Double.compare(maxY, minY) == 0) {
                minMaxData.get(i).setY(maxY);
            } else {
                double comp = sample.getY();
                sample.setY(((2 / (maxY - minY)) * (comp - minY)) - 1);
            }
        }

    }

    private void setStdDataset() {
        int p = data.get(0).getX().length;
        int size = data.size();

        for (int i = 0; i < size; i++) {
            stdData.add(new RegresionInstance(data.get(i).getX(), data.get(i).getY()));
        }

        double[] mu = new double[p];
        double[] sigma2 = new double[p];
        for (int j = 0; j < p; j++) {
            mu[j] = 0;
            for (int i = 0; i < size; i++) {
                mu[j] += stdData.get(i).getX()[j];
            }
            mu[j] /= size;

            for (int i = 0; i < size; i++) {
                sigma2[j] += Math.pow(stdData.get(i).getX()[j] - mu[j], 2);
            }
            sigma2[j] /= size;

            for (int i = 0; i < size; i++) {
                if (sigma2[j] == 0) {
                    stdData.get(i).getX()[j] = 0;
                } else {
                    stdData.get(i).getX()[j] = (stdData.get(i).getX()[j] - mu[j]) / (Math.pow(sigma2[j], 0.5));
                }
            }
        }

        double muY;
        double sigma2Y;
        muY = 0;
        sigma2Y = 0;
        for (int i = 0; i < size; i++) {
            muY += stdData.get(i).getY();
        }
        muY /= size;

        for (int i = 0; i < size; i++) {
            sigma2Y += Math.pow(stdData.get(i).getY() - muY, 2);
        }
        sigma2Y /= size;

        for (int i = 0; i < size; i++) {
            if (sigma2Y == 0) {
                stdData.get(i).setY(0);
            } else {
                stdData.get(i).setY((stdData.get(i).getY() - muY) / (Math.pow(sigma2Y, 0.5)));
            }
        }

    }

    public int getDataSize() {
        return dataSize;
    }

    public int getXSize() {
        return columns - 1;
    }

    public int getYSize() {
        return 1;
    }
}
