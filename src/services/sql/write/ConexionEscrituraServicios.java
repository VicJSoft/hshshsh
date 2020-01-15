/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.write;

import Models.Clientes;
import Models.Servicio;
import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VAPESIN
 */
public class ConexionEscrituraServicios {
 
    private boolean key;
    private String query;
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;

    public ConexionEscrituraServicios() {
    
        this.connection = Statics.getConnections();

        
    }
    
    
    
    public boolean insertServicio(Servicio servicio){
        
        Clientes cliente = new Clientes(servicio.getTelefono(), servicio.getNombre(), 
                servicio.getCalle(), servicio.getColonia(), servicio.getNumeroExt(), servicio.getNumeroInt(),
                servicio.getObservaciones()
        );
        ConexionEscrituraClientes cec = new ConexionEscrituraClientes();
        cec.insertClientes(cliente);//inserta cliente nuevo//si existe o no, ya se asegura que haya la foreign key telefono
        query = "INSERT INTO servicios VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, servicio.getTelefono());
            ps.setString(2, servicio.getNombre());
            ps.setString(3, servicio.getDireccion());
            ps.setString(4, servicio.getObservaciones());
            ps.setString(5, servicio.getNotas());
            if(servicio.getIdUnidad()==null){
                ps.setNull(6,Types.INTEGER);
            }else
                ps.setInt(6, servicio.getIdUnidad());
            ps.setInt(7, servicio.getIdEmpleado());
            ps.setBoolean(8, servicio.isServicioActivo());
            ps.setString(9, servicio.getDestino().equals("")? null:servicio.getDestino());
            ps.setDate(10, Date.valueOf(servicio.getFecha_inicio()));
            ps.setTime(11, servicio.getHora());
            ps.setBoolean(12, servicio.isDiario());
           // ps.setString(13, servicio.getSeleccionDia());
            ps.setBoolean(14, servicio.isProgramadow());
            ps.setDate(15, servicio.getFecha_fin());
            //cuando es un servicio regular
            if( !servicio.isProgramadow()){
                ps.setString(13, null);
            }
            //cuando el servicio es diario
            else if(servicio.isDiario() ){
                ps.setString(13, "1111111");
            }
            //cuando es personalizado
            else if(!servicio.isDiario() ){
                ps.setString(13, servicio.getSeleccionDia());

            }
            ps.executeUpdate();
            //obtiene el ID del servicio que acaba de subir
            ResultSet rsID = ps.executeQuery("SELECT MAX(IdServicio) FROM servicios");
            rsID.next();
            servicio.setId_servicio(rsID.getInt(1));
            key = true;
            ps=null;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionEscrituraServicios.class.getName()).log(Level.SEVERE, null, ex);
            key = false;
        }
        return key;
    }
    /**
     * Consulta para cancelar un servicicio con el ID dado, la fecha de cancelación que se setea
     * es now()*.
     * @param idSelected
     * ID de servicio a marcar como cancelado.
     * @return 
     */
    public boolean cancelarServicio(int idSelected) {
        key = false;
        try {
            query = "UPDATE servicios SET servicioActivo = '0',fechaFin = ? WHERE servicios.IdServicio =  " + idSelected;
            
            ps = connection.prepareStatement(query);
            ps.setDate(1, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();
            key = true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionEscrituraServicios.class.getName()).log(Level.SEVERE, null, ex);
        }

        return key;
    }
    
    public boolean asignarUnidad(Servicio servicioAAsignar){
        try {
            key = false;
            query = "UPDATE servicios SET idUnidad = ? WHERE servicios.IdServicio = ?";
            
            ps = connection.prepareStatement(query);
            ps.setInt(1, servicioAAsignar.getIdUnidad());
            ps.setInt(2, servicioAAsignar.getId_servicio());
            
            ps.executeUpdate();
            key = true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionEscrituraServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return key;
        
    }
    
}






