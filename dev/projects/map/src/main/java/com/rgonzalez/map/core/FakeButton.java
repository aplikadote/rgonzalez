/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.map.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Administrador
 */
public class FakeButton extends Canvas {

    boolean pulsado = false;  // Indica si el botón está pulsado o no
    int ancho;                // Anchura deo botón en pixels
    int alto;                 // Altura del botón en pixels
    int x;                 // Altura del botón en pixels
    int y;                 // Altura del botón en pixels
    String rotulo;            // Texto del botón

    // Constructor del Falso Botón
    public FakeButton(int ancho, int alto, int x, int y, String rotulo) {
        this.ancho = ancho;
        this.alto = alto;
        this.rotulo = rotulo;
        this.x = x;
        this.y = y;
//        this.setBackground(Color.green);
        this.setSize(this.ancho, this.alto);
    }

    public void paint(Graphics g) {
        // Determinamos el desplazamiento según la situación en que se
        // encuentre el botón y se pinta el rectángulo sobre el objeto Canvas
        if (pulsado) {
            g.drawRect(1+x, 1+y, ancho, alto);
        } else {
            g.drawRect(-1+x, -1+y, ancho, alto);
        }

        // Se escribe el texto del rótulo del botón centrado horizontalmente
        // y ligeramente desplazado verticalmente. Para este desplazamiento
        // vertical, se utiliza 1/4 de la altura de la fuente de caracteres
        // que se esté utilizando, para calcular la línea base sobre la que
        // se van a colocar los caracteres del texto
        int altoFuente = g.getFontMetrics().getHeight();
        int anchoCadena = g.getFontMetrics().stringWidth(rotulo);
//        g.drawString(rotulo,(ancho - anchoCadena) / 2, (alto / 2) + (altoFuente / 4));
    }
}
