/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author administrador
 */
public class Afp implements Serializable {

    private String name;
    private Map<AfpCluster, AfpClusterData> map;

    public Afp(String name) {
        this.name = name;
        this.map = new HashMap<AfpCluster, AfpClusterData>();
    }

    public String getName() {
        return name;
    }

    public void putRate(AfpCluster cluster, AfpDate date, double profit) {
        AfpClusterData data = map.get(cluster);
        if (data == null) {
            data = new AfpClusterData();
            map.put(cluster, data);
        }
        data.putRate(date, profit);
    }

    public double getRate(AfpCluster cluster, AfpDate date) {
        AfpClusterData data = map.get(cluster);
        if (data == null) {
            return -1;
        }

        return data.getRate(date);
    }

    public Map<AfpCluster, AfpClusterData> getMap() {
        return map;
    }

    public AfpClusterData getClusterData(AfpCluster cluster) {
        return map.get(cluster);
    }

}
