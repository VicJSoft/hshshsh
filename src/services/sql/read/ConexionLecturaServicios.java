/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;

import Models.Servicios;
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
public class ConexionLecturaServicios 
{
    PreparedStatement ps;
    String query="";
    ResultSet rs;
    boolean key;
    Connection connection;

    public ConexionLecturaServicios() {
    
        connection = Statics.getConnections();
    
    }
    
    public ObservableList<String> getServicios_id_name()
    {
        ObservableList<String> servicios =  FXCollections.observableArrayList();
        query="select Id_Servicio,nombre,telefono from empleados";
        try
        {  
            
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                
                    //lo trae en pascal case.
                     servicios.add(rs.getString(1)+"  "+rs.getString(3)+" "+rs.getString(2));
                
            }
            
        
                                      
            ps.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return servicios;
    }
    
    
     public ObservableList<Servicios> getServicios()
    {
        ObservableList<Servicios> servicios =  FXCollections.observableArrayList();
        query="select * from servicios";
        
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {             
                servicios.add(
                        new Servicios(                                
                                String.valueOf(rs.getInt(1)),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                String.valueOf(rs.getInt(7)),
                                String.valueOf(rs.getInt(8)),
                                rs.getDate(9).toLocalDate().toString(),
                                rs.getString(10),
                                rs.getDate(11).toLocalDate().toString(),
                                rs.getTime(12).toLocalTime().toString(),
                                String.valueOf(rs.getInt(13)),
                                rs.getString(14),
                                String.valueOf(rs.getInt(15))
                                
                        )
                
                );

                        
            }
            
            ps.close();
        }
        catch(SQLException ex)
        {
          
        }
        
        return servicios;
    }
}
