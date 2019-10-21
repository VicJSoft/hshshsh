/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.write;

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
    private Connection c;
    private ResultSet rs;
    private PreparedStatement ps;
    public boolean insertEmpleados(String nombre, LocalDate fechaN,String telefono,String tipo,String sexo,String calle,String colonia, String numExt,String numInt,String observaciones,String password, Connection connection)
    {
        
        query="insert into empleados values(null,?,?,?,?,?,?,?,?,?,?,?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1,nombre );
            ps.setDate(2, (java.sql.Date.valueOf(fechaN)));
            ps.setString(3, telefono);
            if(sexo.equals("MASCULINO"))
            {
                ps.setInt(4, 0);
            }
            else
            {
                 ps.setInt(4, 1);//femenino
            }
            if(tipo.equals("ADMINISTRATIVO"))
            {
                ps.setInt(5, 0);
            }
            else
            {
                 ps.setInt(5, 1);//modulador
            }
            ps.setString(6, calle);
            ps.setString(7, colonia);
            if(numExt.equals(""))
                ps.setString(8, null);
            else
                ps.setString(8, numExt);
            if(numInt.equals(""))
                ps.setString(9, null);
            else
                ps.setString(9, numInt);
      
            ps.setString(10, observaciones);
            ps.setString(11, password);
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
