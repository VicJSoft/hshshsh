/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;

import Models.Clientes;
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
public class ConexionLecturaClientes
{
    private boolean key;
    private String query;
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;

    public ConexionLecturaClientes() {
        
        connection = Statics.getConnections();
    
    }
    
    public ObservableList<String> getClientes_id_name()
    {
        ObservableList<String> clientes =  FXCollections.observableArrayList();
        query="select telefono,nombre from clientes";
        try
        {  
            
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                
                    //lo trae en pascal case.
                     clientes.add(rs.getString(1)+"  "+rs.getString(2));
                
            }
            
        
                                      
            ps.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return clientes;
    }
    
    public ObservableList<Clientes> getClientes()
    {
        ObservableList<Clientes> clientes =  FXCollections.observableArrayList();
        query="select * from clientes";
        String direccion;
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
          
                clientes.add(
                        new Clientes(
                                rs.getString(1), 
                                rs.getString(2), 
                                rs.getString(3), 
                                rs.getString(4), 
                                rs.getString(5),                                 
                                rs.getString(6),                                 
                                rs.getString(7)
                        )
                );
                
            }
            
            ps.close();
        }
        catch(SQLException ex)
        {
         
        }
        
        return clientes;
    }
}
