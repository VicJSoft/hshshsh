/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;

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
    private Integer idUnidad;
    private int idEmpleado;
    private boolean servicioActivo;
    private String destino;
    private Date fecha_inicio;
    private Time hora;
    private boolean diario;
    private String seleccionDia;
    private boolean programadow;
    private Date fecha_fin;
    
    private HBox diasSeleccion;
    public JFXCheckBox cb_estado;
    public Servicio(int id_servicio, String telefono, String nombre,
            String calle, String colonia, String numeroExt, String numeroInt,
            String observaciones, String notas, Integer idUnidad, int idEmpleado, 
            boolean servicioActivo, String destino, LocalDate fecha_inicio, LocalTime hora, 
            boolean diario, String seleccionDia, boolean programadow,Date fecha_fin)
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
        this.fecha_fin = fecha_fin;
        cb_estado = new JFXCheckBox();
        cb_estado.allowIndeterminateProperty().set(true);
        
        calcularEstadoServicio();
 
        
    }

    private void calcularEstadoServicio(){
        if(!programadow){
            //si está pendiente, entonces no es marcado.
            cb_estado.selectedProperty().set(!servicioActivo);                      
        }else if(programadow){
            //seleccionado cuando está en curso la programacion = true, cuando se termina la programacion = false
            cb_estado.selectedProperty().set(servicioActivo);
                   
        }
        if(idUnidad==null){
            cb_estado.indeterminateProperty().set(true);
        }else{
            //si no es null, quedará seleccionado, con los if de arriba
            //cb_estado.indeterminateProperty().set(false);
        }  

        
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

    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
        calcularEstadoServicio();//se llama 2 veces, porque alfinal quedaba con la palomita chequeada, cuando era unidad null, 
      //  calcularEstadoServicio();
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
        calcularEstadoServicio();
    }

    

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio.toLocalDate();
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
    /**
     * Para no llamar a 2 propiedades a la vez y darles formato String, aquí lo entrega directo.
     * @return 
     */
    public String getFechaHora(){
        return getFecha_inicio() + " " + getHora();
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    
    public LocalDateTime getDateTime(){
        LocalDateTime dateTime = LocalDateTime.of(getFecha_inicio(), getHora().toLocalTime());
         //return dateTime.toString().replace('T', ' ');
         return dateTime;
    }
    
    private ArrayList<Label> getDiasSeleccionados(){
        ArrayList<Label> listaDias = new ArrayList<>();
        if(this.seleccionDia ==null)
            return listaDias;//me da weva xd

        listaDias.add(new Label("Lu"));            
        listaDias.add(new Label("Ma"));            
        listaDias.add(new Label("Mi"));            
        listaDias.add(new Label("Ju"));            
        listaDias.add(new Label("Vi"));            
        listaDias.add(new Label("Sa"));            
        listaDias.add(new Label("Do"));            


        
        if(this.seleccionDia.charAt(0)=='1')
            listaDias.get(0).setTextFill(Paint.valueOf("5AB444"));
        if(this.seleccionDia.charAt(1)=='1')
            listaDias.get(1).setTextFill(Paint.valueOf("5AB444"));
        if(this.seleccionDia.charAt(2)=='1')
            listaDias.get(2).setTextFill(Paint.valueOf("5AB444"));
        if(this.seleccionDia.charAt(3)=='1')
            listaDias.get(3).setTextFill(Paint.valueOf("5AB444"));
        if(this.seleccionDia.charAt(4)=='1')
            listaDias.get(4).setTextFill(Paint.valueOf("5AB444"));
        if(this.seleccionDia.charAt(5)=='1')
            listaDias.get(5).setTextFill(Paint.valueOf("5AB444"));
        if(this.seleccionDia.charAt(6)=='1')
            listaDias.get(6).setTextFill(Paint.valueOf("5AB444"));
        
        return listaDias;
        
    }
    
    
    public HBox getDiasSeleccion() {
 
        this.diasSeleccion = new HBox();
        
        this.diasSeleccion.getChildren().addAll(getDiasSeleccionados());
        this.diasSeleccion.setAlignment(Pos.CENTER);
        this.diasSeleccion.setSpacing(2);
        return diasSeleccion;
    }

    public void setDiasSeleccion(HBox diasSeleccion) {
        this.diasSeleccion = diasSeleccion;
    }

    public JFXCheckBox getCb_estado() {
        return cb_estado;
    }

    public void setCb_estado(JFXCheckBox cb_estado) {
        this.cb_estado = cb_estado;
    }
    
    
    
}
