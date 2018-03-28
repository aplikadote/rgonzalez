/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.run;

import cl.rgonzalez.afp.core.Afp;
import cl.rgonzalez.afp.core.AfpCluster;
import cl.rgonzalez.afp.core.AfpClusterData;
import cl.rgonzalez.afp.core.AfpContext;
import cl.rgonzalez.afp.core.AfpDate;
import cl.rgonzalez.afp.utils.Storage;
import java.util.ArrayList;
import java.util.List;

/**
BANSANDER
=============================
first: [2005 7]
last : [2008 2]
 
SANTA MARIA
=============================
first: [2005 7]
last : [2008 2]
 
CAPITAL
=============================
first: [2008 3]
last : [2015 11]
=============================
 
MODELO
=============================
first: [2010 8]
last : [2015 11]
 
 */
public class Main {

    public void run() {
        try {
            Storage storage = new Storage();
            AfpContext context = storage.restore();

            int year = 2006;
            int month = 0;
            double mensual = 30000;

            double bote1 = context.simulate(mensual, 0.000, year, month);
            System.out.println("bote1: " + bote1);

            double bote2 = context.simulate(mensual, 0.0035, year, month);
            double renta2 = (bote2 - bote1) / bote1;
            System.out.println("bote2: " + bote2 + ", rentabilidad :" + (renta2 * 100));
     
            List<Afp> afps = new ArrayList<Afp>();
            afps.add(context.getAfp("CUPRUM"));
            afps.add(context.getAfp("HABITAT"));
            afps.add(context.getAfp("PLANVITAL"));
            afps.add(context.getAfp("PROVIDA"));
            afps.add(context.getAfp("SISTEMA"));
            
//            afps.add(context.getAfp("CAPITAL"));
//            afps.add(context.getAfp("MODELO"));
            
            for (Afp afp: afps) {
                String name = afp.getName();
                System.out.println("=============================");
                System.out.println(name);
                System.out.println("=============================");
                
                for (int i = 0; i < AfpCluster.values().length; i++) {
                    AfpCluster cluster = AfpCluster.values()[i];
                    double bote = context.simulate(mensual, name, cluster.name(), year, month);
                    double rentabilidad = (bote - bote1) / bote1;
//                    System.out.println("bote '" + cluster.name() + "': " + bote + ", rentabilidad '" + cluster.name() + "': " + (rentabilidad * 100));
                    System.out.println((rentabilidad * 100));
                }
                System.out.println();
            }

//            double acum = 0;
//            Afp afp = context.getAfps().get(0);
//            for (int i = 0; i < 12; i++) {
//                double rate = afp.getRate(AfpCluster.A, new AfpDate(2006, i));
//                acum += rate;
//                System.out.println("rate: " + rate);
//                System.out.println("acum: " + acum);
//                System.out.println();
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

}

