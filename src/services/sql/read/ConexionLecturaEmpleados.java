package services.sql.read;

import Models.Empleados;
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
public class ConexionLecturaEmpleados 
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
     public ObservableList<Empleados> getEmpleados(Connection connection)
    {
        ObservableList<Empleados> empleados =  FXCollections.observableArrayList();
        query="select id_empleado,nombre,telefono,calle, num_ext,num_int,colonia, observaciones, fecha_nacimiento from empleados";
        String direccion;
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                direccion=rs.getString(4)+","+rs.getString(5);
                if(rs.getString(6)!=null)
                {
                    direccion+=","+rs.getString(6).toUpperCase();
                }
                if(rs.getString(7)!=null)
                {
                    direccion+=","+rs.getString(7);
                }
               
                
                empleados.add(new Empleados(String.valueOf(rs.getInt(1)),rs.getString(2).toUpperCase(),rs.getString(3),direccion.toUpperCase(),rs.getString(8).toUpperCase(),String.valueOf(rs.getDate(9))));
              
            }
            
            ps.close();
        }
        catch(SQLException ex)
        {
          
        }
        
        return empleados;
    }
    
}
