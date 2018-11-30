package com.tibco.support.manager;

import java.io.*;

/**
 * Created by rodri on 2/14/2018.
 */
public class Reader {
    public static Casos readFromFile() {
        FileInputStream fileIn = null;
        Casos tramites= new Casos();
        try {
            fileIn = new FileInputStream("casos.db");
            ObjectInputStream in = new ObjectInputStream(fileIn);
                try{
                    tramites =((Casos)in.readObject());
                } catch(EOFException e){
                    e.printStackTrace();
                }
            in.close();
            fileIn.close();
            return tramites;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    }
