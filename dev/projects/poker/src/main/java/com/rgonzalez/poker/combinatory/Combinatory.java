/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.poker.combinatory;

import java.util.ArrayList;
import java.util.List;
import com.rgonzalez.poker.visitor.PrintVisitor;

/**
 * La clase genera todas las combinaciones sin repeticion de
 * n objetos de una lista, donde n es menor que la cantidad de elementos
 * de la lista.
 * <BR>
 * @author Administrador
 */
public class Combinatory<A> {

    private List<A> list;
    private ArrayList<A> comb;
    private int n;
    private ICombinatoryVisitor<A> visitor;

    /**
     * Obtiene todas las combinaciones sin repeticion de
     * 'n' objetos de la lista 'list', utilizando una copia de
     * este ultimo objeto.
     * @param list La lista de objetos
     * @param n El tamaño del subconjunto de combinatorias.
     * @param cloneList TRUE, para crear una nueva lista y no asignar
     * la lista 'list' como puntero, agregando los objetos de esta
     * lista a una nueva; FALSE, en caso de que esto no sea necesario.
     * @throws Exception
     */
    public Combinatory(List<A> list, int n) {
        if (n > list.size()) {
            throw new IllegalArgumentException("n debe ser menor que el tamaño de la lista");
        }

        this.list = list;
        this.n = n;
        this.comb = new ArrayList<A>(n);
        for (int i = 0; i < n; i++) {
            comb.add(null);
        }
    }

    /**
     * El
     * @param visitor
     * @throws Exception
     */
    public void accept(ICombinatoryVisitor<A> visitor) {
        if (list == null) {
            throw new IllegalArgumentException("La lista se encuentra vacia");
        } else if (list.size() == 0) {
            throw new IllegalArgumentException("La lista no contiene elementos");
        } else if (n <= 0) {
            throw new IllegalArgumentException("n debe ser mayor que cero");
        } else if (n > list.size()) {
            throw new IllegalArgumentException("n debe ser menor o igual al tamaño de la lista");
        } else {
            this.visitor = visitor;
            recursiveAccept(0, 0);
        }
    }

    private void recursiveAccept(int index, int pos) {
        if (index == n) {
            visitor.visit(comb);
        } else {
            for (int k = pos; k < list.size() - (n - 1 - index); k++) {
                comb.set(index, list.get(k));
                recursiveAccept(index + 1, k + 1);
            }
        }
    }

    public static void main(String[] args) {
        int n = 3;
        List<String> list = new ArrayList<String>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        list.add("E");

//        int p = 0;
//        for (int i = p; i < list.size() - (n - 1); i++) {
//            for (int j = i + 1; j < list.size() - (n - 1 - 1); j++) {
//                for (int k = j + 1; k < list.size() - (n - 1 - 1 - 1); k++) {
//                    System.out.println(list.get(i) + " " + list.get(j) + " " + list.get(k));
//                }
//            }
//        }

        Combinatory<String> combinatory = new Combinatory<String>(list, n);
        combinatory.accept(new PrintVisitor());

    }
}
