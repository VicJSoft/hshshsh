/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author vicen
 * @deprecated 
 * Usa objetos StringProperty en su totalidad, para más dinamismo se debe usar datos concretos.
 * En su lugar, usar la clase modelo "Servicio".
 */
public class Servicios extends RecursiveTreeObject<Servicios>
{
    StringProperty IdServicio,telefono,nombre, direccion, observaciones,notas,idUnidad,idEmpleado,fecha,destino,fechaInicio,hora, diario,seleccionDia,programado;

    public Servicios(String IdServicio, String telefono, String nombre, String direccion, String observaciones, String notas, String idUnidad, String idEmpleado, String fecha, String destino, String fechaInicio, String hora, String diario, String seleccionDia, String programado) {
        this.IdServicio = new SimpleStringProperty(IdServicio);
        this.telefono = new SimpleStringProperty(telefono);
        this.nombre = new SimpleStringProperty(nombre);
        this.direccion = new SimpleStringProperty(direccion);
        this.observaciones = new SimpleStringProperty(observaciones);
        this.notas = new SimpleStringProperty(notas);
        this.idUnidad = new SimpleStringProperty(idUnidad);
        this.idEmpleado = new SimpleStringProperty(idEmpleado);
        this.fecha = new SimpleStringProperty(fecha);
        this.destino =new SimpleStringProperty( destino);
        this.fechaInicio =new SimpleStringProperty(fechaInicio);
        this.hora = new SimpleStringProperty(hora);
        this.diario = new SimpleStringProperty(diario);
        this.seleccionDia =new SimpleStringProperty( seleccionDia);
        this.programado = new SimpleStringProperty(programado);
    }

    public String getIdServicio()
    {
        return IdServicio.get();
    }

    public void setIdServicio(String IdServicio)
    {
        this.IdServicio = new SimpleStringProperty(IdServicio);
    }

    public String getTelefono()
    {
        return telefono.get();
    }

    public void setTelefono(String telefono)
    {
        this.telefono = new SimpleStringProperty(telefono);
    }

    public String getNombre()
    {
        return nombre.get();
    }

    public void setNombre(String nombre)
    {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getDireccion()
    {
        return direccion.get();
    }

    public void setDireccion(String direccion)
    {
        this.direccion = new SimpleStringProperty(direccion);
    }

    public String getObservaciones()
    {
        return observaciones.get();
    }

    public void setObservaciones(String observaciones)
    {
        this.observaciones = new SimpleStringProperty(observaciones);
    }

    public String getNotas()
    {
        return notas.get();
    }

    public void setNotas(String notas)
    {
        this.notas = new SimpleStringProperty(notas);
    }

    public String getIdUnidad()
    {
        return idUnidad.get();
    }

    public void setIdUnidad(String idUnidad)
    {
        this.idUnidad = new SimpleStringProperty(idUnidad);
    }

    public String getIdEmpleado()
    {
        return idEmpleado.get();
    }

    public void setIdEmpleado(String idEmpleado)
    {
        this.idEmpleado = new SimpleStringProperty(idEmpleado);
    }

    public String getFecha()
    {
        return fecha.get();
    }

    public void setFecha(String fecha)
    {
        this.fecha = new SimpleStringProperty(fecha);
    }

    public String getDestino()
    {
        return destino.get();
    }

    public void setDestino(String destino)
    {
        this.destino = new SimpleStringProperty(destino);
    }

    public String getFechaInicio()
    {
        return fechaInicio.get();
    }

    public void setFechaInicio(String fechaInicio)
    {
        this.fechaInicio = new SimpleStringProperty(fechaInicio);
    }

    public String getHora()
    {
        return hora.get();
    }

    public void setHora(String hora)
    {
        this.hora = new SimpleStringProperty(hora);
    }

    public String getDiario()
    {
        return diario.get();
    }

    public void setDiario(String diario)
    {
        this.diario = new SimpleStringProperty(diario);
    }

    public String getSeleccionDia()
    {
        return seleccionDia.get();
    }

    public void setSeleccionDia(String seleccionDia)
    {
        this.seleccionDia = new SimpleStringProperty(seleccionDia);
    }

    public String getProgramado()
    {
        return programado.get();
    }

    public void setProgramado(String programado)
    {
        this.programado = new SimpleStringProperty(programado);
    }
    
    
    
    
}
