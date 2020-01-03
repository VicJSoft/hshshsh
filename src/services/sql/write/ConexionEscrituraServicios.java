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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        query = "INSERT INTO servicios VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, servicio.getTelefono());
            ps.setString(2, servicio.getNombre());
            ps.setString(3, servicio.getDireccion());
            ps.setString(4, servicio.getObservaciones());
            ps.setString(5, servicio.getNotas());
            ps.setInt(6, servicio.getIdUnidad());
            ps.setInt(7, servicio.getIdEmpleado());
            ps.setBoolean(8, servicio.isServicioActivo());
            ps.setString(9, servicio.getDestino());
            ps.setDate(10, servicio.getFecha_inicio());
            ps.setTime(11, servicio.getHora());
            ps.setBoolean(12, servicio.isDiario());
           // ps.setString(13, servicio.getSeleccionDia());
            ps.setBoolean(14, servicio.isProgramadow());
            //cuando es un servicio regular
            if(!servicio.isDiario() && !servicio.isProgramadow()){
                ps.setString(13, null);
            }else if(servicio.isDiario() && !servicio.isProgramadow()){
                ps.setString(13, "1111111");
            }else if(!servicio.isDiario() && servicio.isProgramadow()){
                ps.setString(13, servicio.getSeleccionDia());

            }
            ps.executeUpdate();
            key = true;
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionEscrituraServicios.class.getName()).log(Level.SEVERE, null, ex);
            key = false;
        }
        return key;
    }
    
}






