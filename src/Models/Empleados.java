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
public class Empleados extends RecursiveTreeObject<Empleados>
{
    
    private SimpleStringProperty  id_empleado, nombre, telefono, direccion, observacion, fecha_nacimiento;
    public Empleados()
    {}

    public Empleados(String id_empleado, String nombre, String telefono, String direccion, String observaciones, String fecha_nacimiento) {
        this.id_empleado = new SimpleStringProperty(id_empleado);
        this.nombre = new SimpleStringProperty(nombre);
        this.telefono = new SimpleStringProperty(telefono);
        this.direccion = new SimpleStringProperty(direccion);
        this.observacion = new SimpleStringProperty(observaciones);
        this.fecha_nacimiento = new SimpleStringProperty(fecha_nacimiento);
    }

    public String getId_empleado() {
        return id_empleado.get();
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = new SimpleStringProperty(id_empleado);
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
        return direccion.get();
    }

    public void setDireccion(String direccion) {
        this.direccion =new SimpleStringProperty(direccion);
    }

    public String getObservaciones() {
        return observacion.get();
    }

    public void setObservaciones(String observacion) {
        this.observacion = new SimpleStringProperty(observacion);
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento.get();
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = new SimpleStringProperty(fecha_nacimiento);
    }
    
}
