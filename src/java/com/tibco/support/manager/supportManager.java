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
        if (isValidDate(this.getCaso().getDate())==false){
            String msg="La fecha esta mal formada";
            FacesMessage facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
            this.getCaso().setDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            FacesContext facesContext=FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
            return "add";
        }
        if (isValidDate(this.getCaso().getDueDate())==false){
            String msg="La fecha esta mal formada";
            FacesMessage facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
            this.getCaso().setDueDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            FacesContext facesContext=FacesContext.getCurrentInstance();
            facesContext.addMessage(null, facesMessage);
            return "add";
        }
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
    private static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
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
    public static Caso[] getMesCasos(){
        Casos casos=Reader.readFromFile();
        for (int c = 0; c < casos.size(); c++) {
            
            if(!casos.get(c).getDate().substring(3).equals(new SimpleDateFormat("MM-yyyy").format(new Date()))){
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
    public static Caso[] getActiveCasos(){
        Casos casos=Reader.readFromFile();
        for (int c = 0; c < casos.size(); c++) {
            
            if(!casos.get(c).getStatus().equals("ACTIVE")){
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
            
            if(!casos.get(c).getStatus().equals("INACTIVE")){
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
    
    public String setStatus(int changeid, String status, String streturn){
        String fileName="casos.db";
        Casos casos= Reader.readFromFile();
        for(Caso caso : casos){
            if(caso.getId()==changeid){
                caso.setStatus(status);
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
