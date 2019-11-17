/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;

import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vicen
 */
public class ConexionLecturaUnidades 
{
    private boolean key;
    private String query;
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;

    public ConexionLecturaUnidades() {
    
        connection = Statics.getConnections();
        
    }
    
    
    
    public boolean existeUnidad(int id_unidad)
    {
        key=false;
        query="select * from unidades where id_unidad='"+id_unidad+"'";
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            key = !rs.first();
            
            ps.close();
        }
        catch(SQLException ex)
        {
            key=false;
        }
        
        return key;
    }
    
}
