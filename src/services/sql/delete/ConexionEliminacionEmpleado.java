/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.delete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author VAPESIN
 */
public class ConexionEliminacionEmpleado {
    
    private boolean key;
    private String query;
    private ResultSet rs;
    private PreparedStatement ps;
    
    public boolean deleteEmpleado(int id_empleado, Connection connection) throws SQLException {
        //falso hasta que no se demuestre lo contrario.
        boolean isEliminado = false;
        
        query = "DELETE FROM empleados WHERE empleados.id_empleado = " + id_empleado;

            ps = connection.prepareStatement(query);
            if (ps.executeUpdate() == 0)
                throw new SQLException("El registro que intenta borrar no existe en la base de datos.\n\nRows afectados: 0.");

     
        isEliminado = true;
        
        return isEliminado;
    }
    
}
