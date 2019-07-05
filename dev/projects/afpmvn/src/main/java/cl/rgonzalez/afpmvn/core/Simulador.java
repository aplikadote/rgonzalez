/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afpmvn.core;

import java.util.Iterator;
import java.util.NavigableSet;

/**
 *
 * @author usuario
 */
public class Simulador {

    private Database db;

    public Simulador(Database db) {
        this.db = db;
    }

    public double simulate(String afpName, String fondoName, int initYear, int initMonth, double cotizacion) {
        double bote = 0;
        Periodo initPeriodo = db.getPeriodo(initYear, initMonth);
        NavigableSet<Periodo> set = db.getPeriodos().tailSet(initPeriodo, true);
        Iterator<Periodo> iterator = set.iterator();
        iterator.next();
//        while (iterator.hasNext()) {
//            Periodo periodo = iterator.next();
//            double rate = db.get(afpName, fondoName, periodo, Tipo.MENSUAL);
//            bote += cotizacion;
//            bote += bote * rate;
//        }

        return bote;
    }

//    public double simulate(double cotizacion, double rate, int year, int month) {
//        double bote = 0;
//        Periodo date = getAfpDate(year, month);
//        NavigableSet<Periodo> set = periodos.tailSet(date, true);
//        Iterator<Periodo> iterator = set.iterator();
//        iterator.next();
//        while (iterator.hasNext()) {
//            Periodo afpDate = iterator.next();
//            bote += cotizacion;
//            bote += bote * rate;
//        }
//        return bote;
//
//    }

}
