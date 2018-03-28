/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rgonzalez.afp.utils;

import cl.rgonzalez.afp.core.AfpContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author administrador
 */
public class Storage {

    private File home = new File("/home/rgonzalez");
    private File file = new File(home, "afp.ser");

    public void persist(AfpContext context) {
        ObjectOutputStream stream = null;
        try {
            stream = new ObjectOutputStream(new FileOutputStream(file));
            stream.writeObject(context);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public AfpContext restore() {
        ObjectInputStream stream = null;
        try {
            stream = new ObjectInputStream(new FileInputStream(file));
            return (AfpContext) stream.readObject();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
