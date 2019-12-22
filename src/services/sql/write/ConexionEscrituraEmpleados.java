/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.write;

import Models.Empleados;
import Resources.statics.Statics;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author vicen
 */
public class ConexionEscrituraEmpleados
{
    private boolean key;
    private String query;
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;

    public ConexionEscrituraEmpleados() {
        connection = Statics.getConnections();
    }
    
    public boolean insertEmpleados(Empleados empleado){
        
        query="insert into empleados values(null,?,?,?,?,?,?,?,?,?,?,?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1,empleado.getNombre() );
            ps.setDate(2, empleado.getFecha_nacimiento());
            ps.setString(3, empleado.getTelefono());
            if(empleado.getSexo().equals("MASCULINO"))
            {
                ps.setInt(4, 0);
            }
            else
            {
                 ps.setInt(4, 1);//femenino
            }
            if(empleado.getTipo_empleado().equals("ADMINISTRATIVO"))
            {
                ps.setInt(5, 0);
            }
            else
            {
                 ps.setInt(5, 1);//modulador
            }
            ps.setString(6, empleado.getCalle());
            ps.setString(7, empleado.getColonia());
            if(empleado.getNum_ext().equals(""))
                ps.setString(8, null);
            else
                ps.setString(8, empleado.getNum_ext() );
            
            if(empleado.getNum_int().equals(""))
                ps.setString(9, null);
            else
                ps.setString(9, empleado.getNum_int() );
      
            ps.setString(10, empleado.getObservaciones());
            ps.setString(11, empleado.getPassword());
            ps.executeUpdate();
            key=true;
            ps.close();
        }
        catch(SQLException ex)
        {
             key=false;
        }
        
        return key;        
    }
    
   
}
