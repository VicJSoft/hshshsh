/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.update;

import Models.Empleados;
import Models.Taxistas;
import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author VAPESIN
 */
public class ConexionUpdateEmpleado {
    PreparedStatement ps;
    String query="",auxiliar="";
    ResultSet rs;
    boolean key;
    private final Connection connection;

    public ConexionUpdateEmpleado(){
        
        this.connection = Statics.getConnections();
        
    }

   
    public boolean update(Empleados empleadoUpdate){
        
        query = "UPDATE empleados"
                + " SET nombre = ?, fecha_nacimiento = ?,telefono = ?,"
                    + "sexo = ?, tipo_empleado = ?,calle = ?,colonia = ?,"
                    + "num_ext =?,num_int = ?,observaciones = ?, password = ?"
                + " WHERE empleados.id_empleado = ?";
        try
        {
            ps = connection.prepareStatement(query);
  
            ps.setString(1, empleadoUpdate.getNombre());
            ps.setDate(2, empleadoUpdate.getFecha_nacimiento());
            ps.setString(3, empleadoUpdate.getTelefono());
            ps.setBoolean(4, !empleadoUpdate.getSexo().equals(Statics.sexo.get(0)));
            ps.setString(5, empleadoUpdate.getTipo_empleado());
            ps.setString(6, empleadoUpdate.getCalle());
            ps.setString(7, empleadoUpdate.getColonia());
            ps.setString(8, empleadoUpdate.getNum_ext());
            ps.setString(9, empleadoUpdate.getNum_int());
            ps.setString(10, empleadoUpdate.getObservaciones());
            ps.setString(11, empleadoUpdate.getPassword());
            ps.setInt(12, empleadoUpdate.getId_empleado());
 
            
            ps.executeUpdate();
            key=true;
            ps.close();
        }
        catch(SQLException ex)
        {
             key=false;
             ex.printStackTrace();
        }
        
        return key;
        
    }
}
