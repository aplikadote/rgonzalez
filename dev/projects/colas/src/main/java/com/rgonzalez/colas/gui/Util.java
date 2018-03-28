/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rgonzalez.colas.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * Clase utilitaria
 * @author Administrador
 */
public class Util {

    /**
     * Sirve para centrar alguna ventana.
     * @param frame La ventana a centrar.
     */
    public static final void center(Window frame){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int)screen.getWidth();
        int screenHeight = (int)screen.getHeight();
        int dialogWidth = frame.getWidth();
        int dialogHeight = frame.getHeight();
        
        frame.setLocation((screenWidth-dialogWidth)/2, (screenHeight-dialogHeight)/2);        
    }
    
}
