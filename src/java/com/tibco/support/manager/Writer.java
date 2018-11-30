package com.tibco.support.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Writer {
    static void writeToFile(String fileName, Caso caso) throws IOException {
        
        Casos casos= Reader.readFromFile();
        File f = new File(fileName);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        casos.add(caso);
        oos.writeObject(casos);
        oos.flush();
        oos.close();
    }
}