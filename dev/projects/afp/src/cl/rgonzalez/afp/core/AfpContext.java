/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 *
 * @author administrador
 */
public class AfpContext implements Serializable {

    private List<Afp> afps;
    private TreeSet<AfpDate> dates;
    private Map<String, Afp> nameToAfp;

    public AfpContext() {
        this.afps = new ArrayList<Afp>();
        this.dates = new TreeSet<AfpDate>();
        this.nameToAfp = new HashMap<String, Afp>();
    }

    public Afp putAfp(String afpName) {
        Afp afp = this.nameToAfp.get(afpName);
        if (afp == null) {
            afp = new Afp(afpName);
            this.afps.add(afp);
            this.nameToAfp.put(afpName, afp);
        }
        return afp;
    }

    public Afp getAfp(String afpName) {
        return this.nameToAfp.get(afpName);
    }

    public AfpDate getAfpDate(int year, int month) {
        AfpDate date = new AfpDate(year, month);
        dates.add(date);
        return date;
    }

    public AfpCluster getAfpCluster(String clusterName) {
        return AfpCluster.valueOf(clusterName);
    }

    public List<Afp> getAfps() {
        return afps;
    }

    public TreeSet<AfpDate> getDates() {
        return dates;
    }

    public double simulate(double cotizacion, String afpName, String clusterName, int year, int month) {
        Afp afp = getAfp(afpName);
        AfpCluster cluster = getAfpCluster(clusterName);
    
        double bote = 0;
        AfpDate date = getAfpDate(year, month);
        NavigableSet<AfpDate> set = dates.tailSet(date, true);
        Iterator<AfpDate> iterator = set.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            AfpDate afpDate = iterator.next();
            double rate = afp.getRate(cluster, afpDate);
            bote += cotizacion;
            bote += bote * rate;
        }

        return bote;
    }

    public double simulate(double cotizacion, double rate, int year, int month) {
        double bote = 0;
        AfpDate date = getAfpDate(year, month);
        NavigableSet<AfpDate> set = dates.tailSet(date, true);
        Iterator<AfpDate> iterator = set.iterator();
        iterator.next();
        while (iterator.hasNext()) {
            AfpDate afpDate = iterator.next();
            bote += cotizacion;
            bote += bote * rate;
        }
        return bote;

    }

}
