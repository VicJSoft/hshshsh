/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Date;
import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author vicen
 */
public class Taxistas extends RecursiveTreeObject<Taxistas>
{
    private SimpleStringProperty nombre, telefono;
    private String sexo;
    private int id_taxista;
    private String calle,numInt,numExt,colonia;
    private Date fecha_nacimiento;
    private String observaciones;
    
    public Taxistas()
    {}

    public Taxistas(int id_taxista, String nombre, String telefono, LocalDate fecha_nacimiento, String sexo,String calle,String colonia,String numExt,String numInt, String observaciones ) {
        this.id_taxista = id_taxista;
        this.nombre = new SimpleStringProperty(nombre);
        this.telefono = new SimpleStringProperty(telefono);
      //  this.direccion = new SimpleStringProperty(direccion);
        this.calle = calle;
        this.numInt = numInt!=null?numInt:"";
        this.numExt = numExt;
        this.colonia = colonia;
        this.observaciones =observaciones!=null?observaciones:"";
        //this.fecha_nacimiento = fecha_nacimiento.;
        this.fecha_nacimiento = Date.valueOf(fecha_nacimiento);
        this.sexo = sexo;
    }

    public int getId_taxista() {
        return id_taxista;
    }

    public void setId_taxista(int id_taxista) {
        this.id_taxista = id_taxista;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public void setTelefono(String telefono) {
        this.telefono = new SimpleStringProperty(telefono);
    }

    public String getDireccion() {
        return calle +" "+numInt +" "+ numExt +" "+ colonia;
    }



    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones =observaciones;
    }

    public Date getFecha_nacimiento() {
        return this.fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumInt() {
        return numInt;
    }

    public void setNumInt(String numInt) {
        this.numInt = numInt;
    }

    public String getNumExt() {
        return numExt;
    }

    public void setNumExt(String numExt) {
        this.numExt = numExt;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }
    
    
    
    
    
}
