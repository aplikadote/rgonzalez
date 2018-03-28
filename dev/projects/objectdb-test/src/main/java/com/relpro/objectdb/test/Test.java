/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.relpro.objectdb.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author rgonzalez
 */
public class Test {

    private static File file = new File("/home/rgonzalez/Descargas/DATOS_DISPATCH_BUENO.csv");
    private SimpleDateFormat sdf = new SimpleDateFormat("DD-MM-YYYY HH:mm:ss");
    private DecimalFormat df = new DecimalFormat("#.####");
    private int count = 0;

    public void test() throws IOException, ParseException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("db/points.odb");
        EntityManager em = emf.createEntityManager();

        for (int i = 0; i < 107; i++) {
            commit(em);
        }

         // Find the number of Point objects in the database:
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        System.out.println("Total Points: " + q1.getSingleResult());
//
//        // Find the average X value:
//        Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
//        System.out.println("Average X: " + q2.getSingleResult());
//
//        // Retrieve all the Point objects from the database:
//        TypedQuery<Point> query =
//            em.createQuery("SELECT p FROM Point p", Point.class);
//        List<Point> results = query.getResultList();
//        for (Point p : results) {
//            System.out.println(p);
//        }
        // Close the database connection:
        System.out.println("count: " + count);
        em.close();
        emf.close();
    }

    public void commit(EntityManager em) throws FileNotFoundException, IOException, ParseException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = "";
        
        em.getTransaction().begin();
        line = bf.readLine();
        while ((line = bf.readLine()) != null) {

            StringTokenizer st = new StringTokenizer(line, ";");
            String fecha = st.nextToken();
            String hora = st.nextToken();
            String duracion = st.nextToken();
            String tipo = st.nextToken();
            String gplan = st.nextToken();
            String costo = st.nextToken();
            String equipo = st.nextToken();
            String sintoma = st.nextToken();
            String modo = st.nextToken();
            String causa = st.nextToken();
            String orden = st.nextToken();
            String impacto = st.nextToken();
            String detSistema = st.nextToken();

            Tuple tuple = new Tuple();
            tuple.setInicio(sdf.parse(fecha + " " + hora));
            tuple.setDuracion(df.parse(duracion).doubleValue());
            tuple.setTipo(tipo);
            tuple.setGplan(gplan);
            tuple.setCosto(df.parse(costo).doubleValue());
            tuple.setEquipo(equipo);
            tuple.setSintoma(sintoma);
            tuple.setModo(modo);
            tuple.setCausa(causa);
            tuple.setOrden(orden);
            tuple.setImpacto(df.parse(impacto).doubleValue());
            tuple.setDetSys(detSistema.compareTo("1") == 0);

            em.persist(tuple);
            count++;

//            if (count % 100 == 0) {
//                System.out.println(count);
//            }
        }
        
        em.getTransaction().commit();

    }

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.test();
    }
}
