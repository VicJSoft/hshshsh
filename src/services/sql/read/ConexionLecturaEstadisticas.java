/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;

import Models.Empleados;
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
public class ConexionLecturaEstadisticas 
{
    PreparedStatement ps;
    String query="";
    ResultSet rs;
    boolean key;
    Connection connection;

    public ConexionLecturaEstadisticas()
    {
        connection = Statics.getConnections();
    }
    
    
    
    public boolean obtenerEmpleado(String nombre, String password)throws SQLException
    {
        query = "select * from empleados where nombre='"+nombre+"' and password='"+password+"'";      
        key=false;
        try
        {
            ps = connection.prepareStatement(query);
            rs= ps.executeQuery();
            if(rs.first())
            {
                key=true;
            }
            ps.close();
        }
        catch(SQLException sql){}
        return key;
    }
     public ObservableList<Empleados> getEmpleados()
    {
        ObservableList<Empleados> empleados =  FXCollections.observableArrayList();
        query="select * from empleados";
        String direccion;
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {             
                empleados.add(
                        new Empleados(                                
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getDate(3).toLocalDate(),
                                rs.getString(4),
                                !"0".equals(rs.getString(5))?Statics.sexo.get(0):Statics.sexo.get(1),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9),
                                rs.getString(10),
                                rs.getString(11),
                                rs.getString(12)
                        )
                
                );

                        
            }
            
            ps.close();
        }
        catch(SQLException ex)
        {
          
        }
        
        return empleados;
    }
    
}
