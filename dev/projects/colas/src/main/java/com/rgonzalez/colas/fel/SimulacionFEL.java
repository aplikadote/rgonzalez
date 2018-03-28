/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.colas.fel;

import com.rgonzalez.colas.distributions.IDistribution;
import java.util.ArrayList;

/**
 * Basicamente, la simulacion trabaja de la siguiente manera
 * <OL>
 * <LI> Mientras no se cumpla la una condicion que no entiendo (asociada al nivel de confianza)
 *   <OL> 
 *   <LI>Generar tiempos de llegada y servicio de forma aleatoria,
 *        segun la distribucion que se haya asociado a cada caso.
 *   <LI> Guardar los indicadores asociados a cada suceso en un nodo
 *        de tiempo, encapsulado en la clase "TimeNode".
 *   <LI> Calcular indicadores generales para el sistema de lo que se
 *        lleva hasta el momento.
 *   </OL>
 * </OL>
 * Los nodos se almacenan en una lista de TimeNode llamada "arrayTimeNode".
 * @author Administrador
 */
public class SimulacionFEL {

    private double t_medio_cola = 0;
    private double t_medio_servidor = 0;
    private double t_medio_sistema = 0;
    private double n_medio_cola = 0;
    private double n_medio_servidor = 0;
    private double n_medio_sistema = 0;
    private double porcentaje = 0;
    private int userNumber=0; // numero de usuarios
    private ArrayList<TimeNode> arrayTimeNode = new ArrayList<TimeNode>();

    /**
     * Constructor de SimulacionFEL.
     * @param confidence El nivel de confianza
     * @param distArrive La distribucion con que simular los tiempos llegada.
     * @param distService La distribucion con que simular los tiempos de servicio.
     */
    public SimulacionFEL(double confidence, IDistribution distArrive, IDistribution distService) {

        double Z = getNormal(confidence);
        double WW = 0; // tiempo asignado en el sistema 
        double condicion = 0;
        double llegada_ant = 0;
        double salida_ant = 0;
        double inicio_simula = 0;
        double fin_simula = 0;
        double suma_cola = 0;
        double suma_servidor = 0;
        double suma_sistema = 0;
        int i = 0;

        do {
            TimeNode nodo = new TimeNode();

            // Generar llegada
            double randomLlegada = distArrive.getValue();
            nodo.setTiempoEntreLlegadas(randomLlegada);

            if (i == 0) {
                inicio_simula = randomLlegada; //tiempo inicial de la simulacion					
                nodo.setTiempoDeLlegada(randomLlegada);
                llegada_ant = randomLlegada;
            } else {
                nodo.setTiempoDeLlegada(randomLlegada + llegada_ant);
                llegada_ant += randomLlegada;
            }

            // Generar servicio
            double randomServicio = distService.getValue();
            nodo.setTiempoDeServicio(randomServicio);

            // Generar tiempos en el sistema
            if (WW < nodo.getTiempoEntreLlegadas()) {
                WW = nodo.getTiempoDeServicio();
                nodo.setTiempoEnElSistema(WW);
            } else {
                WW += nodo.getTiempoDeServicio() - nodo.getTiempoEntreLlegadas();
                nodo.setTiempoEnElSistema(WW);
            }

            // Obtener tiempo en cola
            nodo.setTiempoEnCola(nodo.getTiempoEnElSistema() - nodo.getTiempoDeServicio());

            // Generar tiempos salida
            nodo.setTiempoDeSalida(nodo.getTiempoDeLlegada() + nodo.getTiempoEnElSistema());

            fin_simula = nodo.getTiempoDeLlegada() + nodo.getTiempoEnElSistema();
            
            // Medidas
            if (i == 0) {
                salida_ant = nodo.getTiempoDeSalida();
            } else {
                suma_cola += salida_ant - nodo.getTiempoDeLlegada();
                salida_ant = nodo.getTiempoDeSalida();
            }

            // Acumula los tiempos en servidor de los usuarios para el calculo de medidas posteriormente
            suma_servidor += nodo.getTiempoDeServicio();

            // Acumula los tiempos en sistema de los usuarios para el calculo de medidas posteriormente
            suma_sistema += nodo.getTiempoEnElSistema();

            // contador del numero de usuarios que llega al sistema
            userNumber++;
            
            this.t_medio_cola = suma_cola / userNumber;
            this.t_medio_servidor = suma_servidor / userNumber;
            this.t_medio_sistema = suma_sistema / userNumber;
            /*(fin_simula-inicio_simula)->equivale al numero de muestras, o al numero 
            de simulaciones*/
            this.n_medio_cola = suma_cola / (fin_simula - inicio_simula);
            this.n_medio_servidor = suma_servidor / (fin_simula - inicio_simula);
            this.n_medio_sistema = suma_sistema / (fin_simula - inicio_simula);
            this.porcentaje = n_medio_servidor * 100;

            this.arrayTimeNode.add(nodo);
            
            if (i != 0) 
                condicion = getConfidence(userNumber);

            i = 1;
        } while (condicion < Z);

    }

    /**
     * Se obtiene el valor Z que permite generar la condicion
     * de parada para la simulacion.
     * @param level El nivel de confianza.
     * @return El valor Z.
     */
    private double getNormal(double level) {
        double nivel = level;
        
        nivel /= 100;
        nivel = 1 - nivel;
        nivel = 1 - (nivel / 2);
        return (Math.pow(nivel, 0.135) - Math.pow(1 - nivel, 0.135)) / 0.1975;
    }

    /**
     * Calculo de nivel de confianza con respecto al
     * tiempo medio de usuarios en el sistema.
     * @param userNumber Numero de usuarios.
     * @return El nivel de confianza.
     */
    private double getConfidence(int userNumber) {
        double suma = 0;
        double media;
        double varianza;
        double A;
        double desviacionEstandar;

        // Media
        for (TimeNode timeNode : arrayTimeNode) {
            suma += timeNode.getTiempoEnElSistema();
        }
        media = suma / userNumber;

        A = media / 100;

        // Desviacion Estandar
        suma = 0;
        for (TimeNode timeNode : arrayTimeNode) {
            suma += Math.pow(media - timeNode.getTiempoEnElSistema(), 2);
        }
        varianza = suma / (userNumber - 1);

        desviacionEstandar = Math.pow(varianza, 0.5);
        return A * (Math.pow(userNumber, 0.5)) / desviacionEstandar;
    }
    
    /**
     * Se obtiene el numero de usuarios que pasaron por el sistema.
     * @return El numero de usuarios
     */
    public int getUserNumber() {
        return userNumber;
    }

    /**
     * Seteador para el numero de usuarios que pasaron por el sistema.
     * @param userNumber El numero de usuarios.
     */
    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }
    
    /**
     * Devuelve la lista de nodos de tiempos generados
     * por la simulacion.
     * @return La lista de nodos
     */
    public ArrayList<TimeNode> getArrayTimeNode() {
        return arrayTimeNode;
    }

    /**
     * Seteador de la lista de nodos de tiempo.
     * @param arrayTimeNode La lista de nodos.
     */
    public void setArrayTimeNode(ArrayList<TimeNode> arrayTimeNode) {
        this.arrayTimeNode = arrayTimeNode;
    }

    /**
     * Retorna el numero medio de usuarios en cola.
     * @return el numero medio de usuarios en cola.
     */
    public double getN_medio_cola() {
        return n_medio_cola;
    }

    /**
     * Setea el numero medio de usuarios en cola.
     * @param n_medio_cola el numero medio de usuarios en cola.
     */
    public void setN_medio_cola(double n_medio_cola) {
        this.n_medio_cola = n_medio_cola;
    }

    /**
     * Retorna el numero medio de usuarios en el servidor.
     * @return el numero medio de usuarios en el servidor.
     */
    public double getN_medio_servidor() {
        return n_medio_servidor;
    }

    /**
     * Setea el numero medio de usuarios en el servidor.
     * @param n_medio_servidor el numero medio de usuarios en el servidor.
     */
    public void setN_medio_servidor(double n_medio_servidor) {
        this.n_medio_servidor = n_medio_servidor;
    }

    /**
     * Retorna el numero medio de usuarios en el sistema.
     * @return el numero medio de usuarios en el sistema.
     */
    public double getN_medio_sistema() {
        return n_medio_sistema;
    }

    /**
     * Setea el numero medio de usuarios en el sistema.
     * @param n_medio_sistema el numero medio de usuarios en el sistema.
     */ 
    public void setN_medio_sistema(double n_medio_sistema) {
        this.n_medio_sistema = n_medio_sistema;
    }

    /**
     * Retorna el porcentaje de ocupacion del sistema.
     * @return el porcentaje de ocupacion del sistema.
     */
    public double getPorcentaje() {
        return porcentaje;
    }

    /**
     * Setea el porcentaje de ocupacion del sistema.
     * @param porcentaje el porcentaje de ocupacion del sistema.
     */
    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * Retorne el tiempo medio en cola.
     * @return el tiempo medio en cola.
     */
    public double getT_medio_cola() {
        return t_medio_cola;
    }

    /**
     * Setea el el tiempo medio en cola.
     * @param t_medio_cola el tiempo medio en cola.
     */
    public void setT_medio_cola(double t_medio_cola) {
        this.t_medio_cola = t_medio_cola;
    }

    /**
     * Retorne el tiempo medio en el servidor.
     * @return el tiempo medio en el servidor.
     */
    public double getT_medio_servidor() {
        return t_medio_servidor;
    }

    /**
     * Setea el tiempo medio en el servidor.
     * @param t_medio_servidor el tiempo medio en el servidor.
     */
    public void setT_medio_servidor(double t_medio_servidor) {
        this.t_medio_servidor = t_medio_servidor;
    }

    /**
     * Retorna el tiempo medio en el sistema.
     * @return el tiempo medio en el sistema.
     */
    public double getT_medio_sistema() {
        return t_medio_sistema;
    }

    /**
     * Setea el tiempo medio en el sistema.
     * @param t_medio_sistema el tiempo medio en el sistema.
     */
    public void setT_medio_sistema(double t_medio_sistema) {
        this.t_medio_sistema = t_medio_sistema;
    }
}
