/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.utilities;

import com.gonzalez.ann.dataset.RegresionInstance;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class Fold {

    private List<RegresionInstance> data;

    public Fold() {
        this.data = new ArrayList<RegresionInstance>();
    }

    public List<RegresionInstance> getData() {
        return data;
    }

    public int getSize() {
        return data.size();
    }

    public void add(RegresionInstance inst) {
        this.data.add(inst);
    }

    public RegresionInstance get(int i) {
        return this.data.get(i);
    }

    public int size() {
        return this.data.size();
    }
}
