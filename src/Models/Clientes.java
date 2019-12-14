/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Date;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author vicen
 */
public class Clientes extends RecursiveTreeObject<Clientes>
{
    //campo telefono es el primary key*.
    private String nombre, telefono, calle,numeroExt,numeroInt,colonia, observaciones;
    public Clientes()
    {}

    public Clientes(String telefono , String nombre, String calle, String colonia, String numeroExt, String numeroInt, String observaciones) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.calle = calle;
        this.numeroExt = numeroExt;
        this.numeroInt = numeroInt!=null?numeroInt:"";//
        this.colonia = colonia;
        this.observaciones = observaciones!=null?observaciones:"";//
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumeroExt() {
        return numeroExt;
    }

    public void setNumeroExt(String numeroExt) {
        this.numeroExt = numeroExt;
    }

    public String getNumeroInt() {
        return numeroInt;
    }

    public void setNumeroInt(String numeroInt) {
        this.numeroInt = numeroInt;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

   public String getDireccion(){
       return calle+" "+numeroExt+" "+numeroInt+" "+colonia;
   }
        
}
