package services.sql;

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
    
    public boolean obtenerEmpleado(String nombre, String password,Connection connection)throws SQLException
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
    
}
