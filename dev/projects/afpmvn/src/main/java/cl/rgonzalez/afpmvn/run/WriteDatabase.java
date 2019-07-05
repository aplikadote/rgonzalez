/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afpmvn.run;

import cl.rgonzalez.afpmvn.core.Periodo;
import cl.rgonzalez.afpmvn.core.Database;
import cl.rgonzalez.afpmvn.core.Tipo;
import cl.rgonzalez.afpmvn.core.Storage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author administrador
 */
public class WriteDatabase {

    private Database db;
    private NumberFormat df;

    public WriteDatabase() {
        this.db = new Database();
        this.df = NumberFormat.getNumberInstance(new Locale("ES", "CL"));
    }

    public void start() throws IOException, ParseException {
        InputStream is = WriteDatabase.class.getResourceAsStream("/data/201906.json");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JSONObject json = new JSONObject(sb.toString());
        processJson(json);

        Storage storage = new Storage();
        storage.persist(db);
    }

    public void processJson(JSONObject json) throws IOException, ParseException {
        String yearStr = json.getString("year");
        String monthStr = json.getString("month");
        JSONArray columns = json.getJSONArray("fondosColumns");
        JSONArray fondosData = json.getJSONArray("fondosData");

        int year = Integer.parseInt(yearStr);
        int month = Integer.parseInt(monthStr);

        Periodo periodo = new Periodo(year, month);

        int columnSize = columns.length();
        int n = fondosData.length();

        for (int i = 0; i < n; i++) {
            JSONObject fondo = fondosData.getJSONObject(i);
            String fondoName = fondo.getString("fondo");
//            System.out.println("fondoName : " + fondoName);

            JSONArray fondoData = fondo.getJSONArray("data");
            int m = fondoData.length();
            for (int j = 0; j < m; j++) {
                JSONArray row = fondoData.getJSONArray(j);
                int rowSize = row.length();
                if (columnSize != rowSize) {
                    throw new RuntimeException("La dimension entre fila y columna debe ser igual: " + row);
                }

                String afpName = row.getString(0);
                String rateMonthRateStr = row.getString(1);
                String rateAcumThisYearStr = row.getString(2);
                String rateAcumFullYearStr = row.getString(3);
                String rateAverageTotalStr = row.getString(4);

                Double rateMonth = parse(rateMonthRateStr);
                Double rateAcumThisYear = parse(rateAcumThisYearStr);
                Double rateAcumFullYear = parse(rateAcumFullYearStr);
                Double rateAverageTotal = parse(rateAverageTotalStr);

                db.put(afpName, fondoName, periodo, Tipo.MENSUAL, rateMonth);
                db.put(afpName, fondoName, periodo, Tipo.ACUM_DESDE_ENERO, rateAcumThisYear);
                db.put(afpName, fondoName, periodo, Tipo.ACUM_ANHO_COMPLETO, rateAcumFullYear);
                db.put(afpName, fondoName, periodo, Tipo.PROMEDIO_HISTORICO, rateAverageTotal);
            }
        }
    }

    private Double parse(String str) {
        try {
            return df.parse(str).doubleValue();
        } catch (ParseException ex) {
            return null;
        }
    }

    public Database getRoot() {
        return db;
    }

    public static void main(String[] args) throws ParseException {
        try {
            WriteDatabase process = new WriteDatabase();
            process.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
