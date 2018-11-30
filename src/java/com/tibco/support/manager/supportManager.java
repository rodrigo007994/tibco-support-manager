/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.support.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author rodrigo
 */
@ManagedBean
@RequestScoped
public class supportManager {
    @ManagedProperty(value="#{caso}")
    private Caso caso;
    
    
    public String removeCaso(){
        try {
            Writer.writeToFile("casos.db",this.getCaso());
            return "viewActive";
        } catch (IOException ex) {
            Logger.getLogger(supportManager.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
        
    }
    
    public String saveCaso(){
        try {
            Writer.writeToFile("casos.db",this.getCaso());
            return "viewActive";
        } catch (IOException ex) {
            Logger.getLogger(supportManager.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
        
    }

    public Caso getCaso() {
        return caso;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }
    public static Caso[] getCasos(){
        Casos casos=Reader.readFromFile();
        Caso[] out=new Caso[casos.size()];
        
        if(!casos.isEmpty()) {
            for(int c=0;c<casos.size();c++){
                out[c]=casos.get(c);
            }
        }
        return out;
    }
    public static Caso[] getActiveCasos(){
        Casos casos=Reader.readFromFile();
        for (int c = 0; c < casos.size(); c++) {
            
            if(!casos.get(c).isActive()){
                casos.remove(c);
                c--;
            }
        }
        Caso[] out=new Caso[casos.size()];
        
        if(!casos.isEmpty()) {
            for(int c=0;c<casos.size();c++){
                out[c]=casos.get(c);
            }
        }
        return out;
    }
    public static Caso[] getInactiveCasos(){
        Casos casos=Reader.readFromFile();
        for (int c = 0; c < casos.size(); c++) {
            
            if(casos.get(c).isActive()){
                casos.remove(c);
                c--;
            }
        }
        Caso[] out=new Caso[casos.size()];
        
        if(!casos.isEmpty()) {
            for(int c=0;c<casos.size();c++){
                out[c]=casos.get(c);
            }
        }
        return out;
    }
    
    public String setCaseActive(int id, boolean active, String streturn){
        String fileName="casos.db";
        Casos casos= Reader.readFromFile();
        for(Caso caso : casos){
            if(caso.getId()==id){
                caso.setActive(active);
                break;
            }
        }
        File f = new File(fileName);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        try {
            oos.writeObject(casos);
            oos.flush();
            oos.close();
            return streturn;
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }


    }
    
    public String removeCaso(int removeid) {
        String fileName="casos.db";
        Casos casos= Reader.readFromFile();
        int removeIndex=-1;
        for(Caso caso : casos){
            if(caso.getId()==removeid){
                removeIndex=casos.indexOf(caso);
                break;
            }
        }
        casos.remove(removeIndex);

        File f = new File(fileName);
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(f));
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        try {
            oos.writeObject(casos);
            oos.flush();
            oos.close();
            return "exito";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }


    }
    
    
    
}
