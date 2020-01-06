/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;

import Models.Servicio;
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
    
    
     public ObservableList<Servicio> getServicios()
    {
        ObservableList<Servicio> servicios =  FXCollections.observableArrayList();
        query="select * from servicios";
        
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {             
                //String calle,colonia,numExt,numInt;
               // String[] split = rs.getString(4).split("|");
               /* calle = split[0];
                colonia = split[1];
                numExt = split[2];
                split.length[]*/
                
                    Servicio nuevoServicio = new Servicio(                                
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),//direccion split
                                rs.getString(4),//direccion split (no le puedo dar null, por eso repito la propiedad. y la asigno más adelante)
                                rs.getString(4),//direccion split
                                rs.getString(4),//direccion split
                                rs.getString(5),
                                rs.getString(6),
                                rs.getInt(7),
                                rs.getInt(8),
                                rs.getBoolean(9),//activo
                                rs.getString(10),
                                rs.getDate(11).toLocalDate(),
                                rs.getTime(12).toLocalTime(),
                                rs.getBoolean(13),
                                rs.getString(14),
                                rs.getBoolean(15),
                                rs.getDate(16)
                        );
                    //formatea la direccion, adecuadamente al model
                    nuevoServicio.setDireccion(rs.getString(4));
                servicios.add(nuevoServicio);
       
            }
            
            ps.close();
        }
        catch(SQLException ex)
        {
          
        }
        
        return servicios;
    }
}
