/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author VAPESIN
 */
public class Servicio extends RecursiveTreeObject<Servicio> {
    private int id_servicio;
    private String telefono;
    private String nombre;
    
    private String calle;
    private String colonia;
    private String numeroExt;
    private String numeroInt;
    
    private String observaciones;
    private String notas;
    private int idUnidad;
    private int idEmpleado;
    private boolean servicioActivo;
    private String destino;
    private Date fecha_inicio;
    private Time hora;
    private boolean diario;
    private String seleccionDia;
    private boolean programadow;

    public Servicio(int id_servicio, String telefono, String nombre,
            String calle, String colonia, String numeroExt, String numeroInt,
            String observaciones, String notas, int idUnidad, int idEmpleado, 
            boolean servicioActivo, String destino, LocalDate fecha_inicio, LocalTime hora, 
            boolean diario, String seleccionDia, boolean programadow)
    {
        this.id_servicio = id_servicio;
        this.telefono = telefono;
        this.nombre = nombre;
        this.calle = calle;
        this.colonia = colonia;
        this.numeroExt = numeroExt;
        this.numeroInt = numeroInt;
        this.observaciones = observaciones;
        this.notas = notas;
        this.idUnidad = idUnidad;
        this.idEmpleado = idEmpleado;
        this.servicioActivo = servicioActivo;
        this.destino = destino;
        this.fecha_inicio = Date.valueOf(fecha_inicio);
        this.hora = Time.valueOf(hora);
        this.diario = diario;
        this.seleccionDia = seleccionDia;
        this.programadow = programadow;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public boolean isServicioActivo() {
        return servicioActivo;
    }

    public void setServicioActivo(boolean servicioActivo) {
        this.servicioActivo = servicioActivo;
    }

    

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public boolean isDiario() {
        return diario;
    }

    public void setDiario(boolean diario) {
        this.diario = diario;
    }

    public String getSeleccionDia() {
        return seleccionDia;
    }

    public void setSeleccionDia(String seleccionDia) {
        this.seleccionDia = seleccionDia;
    }

    public boolean isProgramadow() {
        return programadow;
    }

    public void setProgramadow(boolean programadow) {
        this.programadow = programadow;
    }
    
    public String getDireccion(){
        return this.calle +"_"+ this.colonia +"_"+ this.numeroExt +"_"+ this.numeroInt;
    }
    public void setDireccion(String direccion){
        String[] split = direccion.split("_");

        this.calle = split[0];
        this.colonia = split[1];
        this.numeroExt = split[2];
        
        this.numeroInt = split.length == 4? split[3]:"";
        
        
    }
    
    
    
}
