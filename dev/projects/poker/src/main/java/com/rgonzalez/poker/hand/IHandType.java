/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.hand;

import com.rgonzalez.poker.core.Card;

/**
 *
 * @author Administrador
 */
public interface IHandType extends Comparable<IHandType> {

    public int getValue();

    public Card[] getCardArray();

    public String getName();
}
