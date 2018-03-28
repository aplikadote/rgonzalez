/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.core;

import java.io.Serializable;

/**
 *
 * @author administrador
 */
public class AfpDate implements Comparable<AfpDate>, Serializable {

    private final int year;
    private final int month;

    public AfpDate(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o.getClass() != AfpDate.class) {
            return false;
        }

        AfpDate date = (AfpDate) o;
        return year == date.year && month == date.month;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = hash * 31 + year;
        hash = hash * 31 + month;
        return hash;
    }

    @Override
    public int compareTo(AfpDate o) {
        int diff = year - o.year;
        if (diff == 0) {
            diff = month - o.month;
        }
        return diff;
    }

    public String toString() {
        return String.format("[%s %s]", year, month);
    }

}
