/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.update;

import Models.Clientes;
import Models.Taxis;
import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author VAPESIN
 */
public class ConexionUpdateCliente {
     PreparedStatement ps;
    String query="",auxiliar="";
    ResultSet rs;
    boolean key;
    private final Connection connection;

    public ConexionUpdateCliente() {
        this.connection = Statics.getConnections();

    }
    
    public boolean update(Clientes clienteUpdate){
        
        query = "UPDATE clientes "
                + " SET nombre = ? ,calle = ?,colonia = ?,num_ext = ?,num_int = ?,observaciones = ? "
                + " WHERE clientes.telefono = ?";
        try
        {
            ps = connection.prepareStatement(query);
            
            ps.setString(1,clienteUpdate.getNombre());
            ps.setString(2,clienteUpdate.getCalle());
            ps.setString(3,clienteUpdate.getColonia());
            ps.setString(4,clienteUpdate.getNumeroExt());
            ps.setString(5,clienteUpdate.getNumeroInt());
            ps.setString(6,clienteUpdate.getObservaciones());
            ps.setString(7,clienteUpdate.getTelefono());
            
            ps.executeUpdate();
            key=true;
            ps.close();
        }
        catch(SQLException ex)
        {
             key=false;
             ex.printStackTrace();
        }
        
        return key;
        
    } 
}
