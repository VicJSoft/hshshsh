/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author vicen
 */
public class Clientes extends RecursiveTreeObject<Clientes>
{
    //campo telefono es el primary key*.
    private SimpleStringProperty   nombre, telefono, direccion, observaciones, fecha_nacimiento;
    public Clientes()
    {}

    public Clientes( String telefono,String nombre, String direccion, String observaciones) {
    
        this.nombre = new SimpleStringProperty(nombre);
        this.telefono = new SimpleStringProperty(telefono);
        this.direccion = new SimpleStringProperty(direccion);
        this.observaciones = new SimpleStringProperty(observaciones);
    }

  
    public String getTelefono() {
        return telefono.get();
    }

    public void setTelefono(String telefono) {
        this.telefono = new SimpleStringProperty(telefono);
    }
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getDireccion() {
        return direccion.get();
    }

    public void setDireccion(String direccion) {
        this.direccion =new SimpleStringProperty(direccion);
    }

    public String getObservaciones() {
        return observaciones.get();
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = new SimpleStringProperty(observaciones);
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento.get();
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = new SimpleStringProperty(fecha_nacimiento);
    }
}
