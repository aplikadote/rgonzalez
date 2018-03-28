/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.colas.exceptions;

/**
 * Clase que es una excepcion que se lanza cuando se
 * ingresan datos incorrectos al momento de configurar
 * alguna distribucion de probabilidad.
 * @author Administrador
 */
public class ParametersException extends Exception{
    private String mensaje = "";
    
    /**
     * El constructor
     * @param mensaje El mensaje a mostrar.
     */
    public ParametersException(String mensaje){
        super(mensaje);
        this.mensaje = mensaje;
    }
    
    /**
     * El mensaje a mostrar. 
     * @return El mensaje a mostrar.
     */
    @Override
    public String getMessage(){
        return mensaje;
    }
    
}
