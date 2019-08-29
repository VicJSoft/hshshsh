/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources.conexionBBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vicen
 */
public class ConexionLectura 
{
    PreparedStatement ps;
    String query="";
    ResultSet rs;
    boolean key;
    public boolean obtenerEmpleado(String nombre, String password,Connection connection)
    {
        query = "select * from empleados where nombre='"+nombre+"' and password='"+password+"'";
        try
        {
            key=false;
            ps = connection.prepareStatement(query);
            rs= ps.executeQuery();
            if(rs.first())
            {
                key=true;
            }
            ps.close();
        }
        catch(SQLException ex)   
        {
            
        }
        return key;
    }
    
}
