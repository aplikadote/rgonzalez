/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.utils;

import cl.rgonzalez.afp.core.Afp;
import cl.rgonzalez.afp.core.AfpContext;
import cl.rgonzalez.afp.core.AfpDate;
import cl.rgonzalez.afp.core.AfpCluster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author administrador
 */
public class Reload {

    private DecimalFormat df = new DecimalFormat("#.#%");
    private SimpleDateFormat sdf = new SimpleDateFormat("MMMM' de 'yyy");
    private AfpContext context;

    public Reload(AfpContext context) {
        this.context = context;
    }

    public void read(File file) throws IOException, ParseException {
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String dateLine = br.readLine();
            Date date = sdf.parse(dateLine.trim());
            br.readLine();

            for (int index = 0; index < AfpCluster.values().length; index++) {
                while (true) {
                    line = br.readLine();
                    if (line == null || line.isEmpty()) {
                        break;
                    }

                    String afpName = "";
                    double profit = 0;

                    String[] split = line.split(" ");
                    for (String s : split) {
                        String str = s.trim();
                        try {
                            profit = df.parse(str).doubleValue();
                            break;
                        } catch (ParseException ex) {
                            afpName = afpName + " " + str;
                        }
                    }

                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);

                    Afp afp = context.putAfp(afpName.trim());
                    AfpDate afpDate = context.getAfpDate(year, month);
                    afp.putRate(AfpCluster.values()[index], afpDate, profit);
                }
            }
        }
    }

    public AfpContext getContext() {
        return context;
    }

     public static void main(String[] args) throws ParseException {
        try {
            AfpContext context = new AfpContext();
            Reload reader = new Reload(context);
            
            File dir = new File("/home/rgonzalez/afp");
            File[] files = dir.listFiles();
            for (File file : files) {
                reader.read(file);
            }

            Storage storage = new Storage();
            storage.persist(context);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
