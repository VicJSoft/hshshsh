/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.delete;

import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Espino
 */
public class ConexionEliminacionCliente {
    
    private boolean key;
    private String query;
    private ResultSet rs;
    private PreparedStatement ps;
    Connection connection;

    public ConexionEliminacionCliente() {
    
        this.connection = Statics.getConnections();
    
    }
    
    
    
    public boolean deleteCliente(String telefono) throws SQLException {
        //falso hasta que no se demuestre lo contrario.
        boolean isEliminado = false;
        
        query = "DELETE FROM clientes WHERE clientes.telefono = " + telefono;

        ps = connection.prepareStatement(query);
        if (ps.executeUpdate() == 0)
            throw new SQLException("El registro que intenta borrar no existe en la base de datos.\n\nRows afectados: 0.");

     
        isEliminado = true;
        
        return isEliminado;
    }
    
}
