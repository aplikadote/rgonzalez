/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 *
 * @author administrador
 */
public class AfpClusterData implements Serializable {

    private TreeSet<AfpDate> dates;
    private Map<AfpDate, Double> rates;

    public AfpClusterData() {
        this.dates = new TreeSet<AfpDate>();
        this.rates = new HashMap<AfpDate, Double>();
    }

    public void putRate(AfpDate date, double value) {
        this.dates.add(date);
        this.rates.put(date, value);
    }

    public double getRate(AfpDate date) {
        Double profit = this.rates.get(date);
//        return profit == null ? -1 : profit;
        return profit == null ? 0 : profit;
    }

    public Map<AfpDate, Double> getRates() {
        return rates;
    }

    public TreeSet<AfpDate> getDates() {
        return dates;
    }
    
}
