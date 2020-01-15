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
import java.util.logging.Level;
import java.util.logging.Logger;
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
               
                servicios.add(crearServicio(rs));
       
            }
            
            ps.close();
        }
        catch(SQLException ex)
        {
          
        }
        
        return servicios;
    }
    
    /**
     * 
     * @param tipo
     * El campo de la DB, telefono (filtro clientes), idUnidad (filtro unidad - taxista), idEmpleado (filtro modulador), 
     * @param primaryKey
     * @return 
     */
    public ObservableList<Servicio> getServicios(String tipo /*filtro*/,String primaryKey){
        
       ObservableList<Servicio> listaServicios = FXCollections.observableArrayList();
        
        try {
            query = "SELECT * FROM servicios WHERE " + tipo + primaryKey;
            

            ps=connection.prepareCall(query);
            rs = ps.executeQuery();
            
            while(rs.next()){
                listaServicios.add(crearServicio(rs));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionLecturaServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaServicios;
    }

     
     
    private Servicio crearServicio(ResultSet rs) throws SQLException{
        rs.getDate(16);
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
                        rs.getInt(7)==0?null:rs.getInt(7),
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

       return nuevoServicio;
    }
}
