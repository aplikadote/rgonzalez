/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gonzalez.ann.dataset;

import com.gonzalez.ann.dataset.RegresionInstance;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author rene
 */
public class MesDataset extends DefaultDataset {

    private List<Tuple> tempDataList;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm");

    public MesDataset(String filename) {
        this.tempDataList = new ArrayList<Tuple>();
        this.dataList = new ArrayList<RegresionInstance>();
        FileReader reader = null;
        try {
            File file = new File("dataset", filename);
            reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader);
            String line;

            line = in.readLine();
            while ((line = in.readLine()) != null) {
                addTempData(new StringTokenizer(line, " ;"));
            }
            processData();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addTempData(StringTokenizer tk) {
        String dateString = tk.nextToken().trim();
        String timeString = tk.nextToken().trim();
        String durationString = tk.nextToken().replace(',', '.');
        String type = tk.nextToken();

        Date date = null;
        double duration = 0;
        if(type.charAt(0)=='M' && type.charAt(1)=='C'){
            try {
                String dateTime = dateString + ", " + timeString;
                date = dateFormat.parse(dateTime);
                duration = Double.parseDouble(durationString);
                this.tempDataList.add(new Tuple(date, duration));
            } catch (ParseException ex) {
                ex.printStackTrace();
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void processData(){
        int tempSize = this.tempDataList.size();

        if(tempSize <=1){
            return;
        }

        double[] tbf = new double[tempSize-1];
        Tuple leftTuple = this.tempDataList.get(0);
        for(int i=1; i<tempSize; i++){
            Tuple rightTuple = this.tempDataList.get(i);
            tbf[i-1] = getTbf(leftTuple, rightTuple);
//            System.out.println(tbf[i-1]);
        }

        Arrays.sort(tbf);
        double[][] accumulateFunction = new double[tbf.length][2];
        for(int i=0; i<tbf.length; i++){
            accumulateFunction[i][0] = tbf[i];
            accumulateFunction[i][1] = getAccumulateFunctionValue(tbf[i], tbf);
//            System.out.println(accumulateFunction[i][0] +"  " + accumulateFunction[i][1]);
            double[] y = new double[1];
            y[0] = accumulateFunction[i][1];

            RegresionInstance instance = new RegresionInstance(y, accumulateFunction[i][0]);
            this.dataList.add(instance);
        }

        this.dataCount = tbf.length;
        this.xDataSize = 1;

    }

    private double getAccumulateFunctionValue(double x, double[] tbf){
        int cont=0;
        for(int i=0; i<tbf.length; i++){
            if(tbf[i] <= x){
                cont++;
            }
            else{
                break;
            }
        }

        return (double) cont/(double)tbf.length;
    }

    private double getTbf(Tuple leftTuple, Tuple rightTuple){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(leftTuple.getDate());
        calendar.add(Calendar.HOUR_OF_DAY, leftTuple.getHours());
        calendar.add(Calendar.MINUTE, leftTuple.getMinutes());
        long initDate = calendar.getTime().getTime();
        long endDate = rightTuple.getDate().getTime();

        double duration = (double) ((((endDate - initDate)/1000)/60))/60;
        return duration;
    }

    private class Tuple{

        private Date date;
        private int hours;
        private int minutes;

        public Tuple(Date date, double duration){
            this.date = date;
            this.hours = (int) Math.floor(duration);
            this.minutes = (int) Math.floor( (duration - Math.floor(duration))*60);
//            System.out.println(date + "  " + hours+":"+minutes);
        }

        public Date getDate() {
            return date;
        }

        public int getHours() {
            return hours;
        }

        public int getMinutes() {
            return minutes;
        }
    }

    public static void main(String[] args) {
        new MesDataset("harnero2.csv");
    }
}
