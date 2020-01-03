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
public class ConexionLecturaEmpleados 
{
    PreparedStatement ps;
    String query="";
    ResultSet rs;
    boolean key;
    Connection connection;

    public ConexionLecturaEmpleados() {
    
        connection = Statics.getConnections();
    
    }
    
    public ObservableList<String> getModulador_id_name()
    {
        ObservableList<String> empleados =  FXCollections.observableArrayList();
        query="select id_empleado,nombre from empleados";
        try
        {  
            
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                
                    //lo trae en pascal case.
                     empleados.add(rs.getString(1)+"  "+rs.getString(2));
                
            }
            
        
                                      
            ps.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return empleados;
    }
    
    public boolean obtenerEmpleado(String nombre, String password)throws SQLException
    {
        query = "select * from empleados where nombre='"+nombre+"' and password='"+password+"'";      
        key=false;

            ps = connection.prepareStatement(query);
            rs= ps.executeQuery();
            if(rs.first())
            {
                Statics.ID_EMPLEADO_SESION_ACTUAL = rs.getInt(1);//id_empleado
                key=true;
            }
            ps.close();
        
        
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
