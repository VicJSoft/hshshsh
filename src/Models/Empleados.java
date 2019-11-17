/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author ESPINO
 */
public class Empleados extends RecursiveTreeObject<Empleados>
{
    private int id_empleado;
    private String  nombre,telefono,sexo,tipo_empleado,calle,colonia,num_ext,num_int,observaciones,password;
    private Date fecha_nacimiento;
    public Empleados()
    {}

    public Empleados(int id_empleado, String nombre,LocalDate fecha_nacimiento, String telefono, String sexo, String tipo_empleado, String calle, String colonia, String num_ext, String num_int, String observaciones, String password) {
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.telefono = telefono;
        this.sexo = sexo;
        this.tipo_empleado = tipo_empleado;
        this.calle = calle;
        this.colonia = colonia;
        this.num_ext = num_ext;
        this.num_int = num_int!=null?num_int:"";
        this.observaciones = observaciones!=null?observaciones:"";
        this.password = password;
        this.fecha_nacimiento = Date.valueOf(fecha_nacimiento);
    }

    
    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipo_empleado() {
        return tipo_empleado;
    }

    public void setTipo_empleado(String tipo_empleado) {
        this.tipo_empleado = tipo_empleado;
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

    public String getNum_ext() {
        return num_ext;
    }

    public void setNum_ext(String num_ext) {
        this.num_ext = num_ext;
    }

    public String getNum_int() {
        return num_int;
    }

    public void setNum_int(String num_int) {
        this.num_int = num_int;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getDireccion(){
        return calle+colonia+num_ext+num_int;
    }
    
}
