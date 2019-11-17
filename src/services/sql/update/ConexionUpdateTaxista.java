/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.update;

import Models.Taxistas;
import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author VAPESIN
 */
public class ConexionUpdateTaxista {
    PreparedStatement ps;
    String query="",auxiliar="";
    ResultSet rs;
    boolean key;
    private final Connection connection;

    public ConexionUpdateTaxista(){
        
        this.connection = Statics.getConnections();
        
    }

   
    public boolean update(Taxistas taxistaUpdate){
        
        query = "UPDATE taxistas "
                + "SET nombre = ?,telefono = ?,fecha_nacimiento =?,sexo = ?,calle = ?,colonia = ?,num_ext = ?,num_int=?,observaciones =? "
                + "WHERE taxistas.id_taxista = ? ";
        try
        {
            ps = connection.prepareStatement(query);
  
            ps.setString(1, taxistaUpdate.getNombre());
            ps.setString(2, taxistaUpdate.getTelefono());
            ps.setDate(3, taxistaUpdate.getFecha_nacimiento());
            ps.setBoolean(4, !taxistaUpdate.getSexo().equals(Statics.sexo.get(0)));
            ps.setString(5, taxistaUpdate.getCalle());
            ps.setString(6, taxistaUpdate.getColonia());
            ps.setString(7, taxistaUpdate.getNumExt());
            ps.setString(8, taxistaUpdate.getNumInt());
            ps.setString(9, taxistaUpdate.getObservaciones());
            ps.setInt(10, taxistaUpdate.getId_taxista());
            
            
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
