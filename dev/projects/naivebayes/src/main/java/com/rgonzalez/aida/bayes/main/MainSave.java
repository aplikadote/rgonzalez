/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rgonzalez.aida.bayes.main;

import com.rgonzalez.aida.bayes.core.FilterManager;
import com.rgonzalez.aida.bayes.database.ConnectionSaveMysql;
import java.io.File;

/**
 *
 * @author Administrador
 */
public class MainSave {

    private FilterManager manager;
    private String dirString = "noticias";

    public MainSave() {
        manager = new FilterManager();
        loadFromFiles();
        saveToDB();
    }

    private void loadFromFiles() {
        File dir = new File(dirString);
        String[] categorias;

        if (dir.isDirectory()) {
            categorias = dir.list();
            for (int i = 0; i < categorias.length; i++) {
                System.out.println(categorias[i]);
                String pathCategory = dirString + File.separator + categorias[i];
                File category = new File(pathCategory);
                String[] ficheros;

                if (category.isDirectory()) {
                    ficheros = category.list();
                    for (int j = 0; j < ficheros.length; j++) {
//                        System.out.println("   " + ficheros[j]);
                        manager.load(pathCategory, ficheros[j]);
                    }
                } else {
                    System.out.println("El archivo no es un directorio");
                    System.out.println("En el directorio " + dirString + " solo deben ir directorios de categorias");
                }
            }
        } else {
            System.out.println("Directorio Pricipal de noticias no encontrado");
            System.out.println("La aplicacion no puede continuar");
            System.exit(0);
        }
    }

    private void saveToDB() {
        new ConnectionSaveMysql(manager);
    }

    public FilterManager getManager() {
        return manager;
    }

    public static void main(String[] args) {
        new MainSave();
    }
}
