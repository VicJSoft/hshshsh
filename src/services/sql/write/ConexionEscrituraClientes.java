package services.sql.write;

import Models.Clientes;
import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author vicen
 */
public class ConexionEscrituraClientes 
{
    private boolean key;
    private String query;
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;

    public ConexionEscrituraClientes() {
        connection = Statics.getConnections();
    }
    
    
    public boolean  insertClientes(Clientes cliente){
        query="insert into clientes values(?,?,?,?,?,?,?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1,cliente.getTelefono() );
            ps.setString(2, cliente.getNombre() );
            ps.setString(3, cliente.getCalle() );
            ps.setString(4, cliente.getColonia());
            if(cliente.getNumeroExt().equals(""))
                ps.setString(5, null);
            else
                ps.setString(5, cliente.getNumeroExt());
            
            if(cliente.getNumeroInt().equals(""))
                ps.setString(6, null);
            else
                ps.setString(6, cliente.getNumeroInt());
            
            ps.setString(7, cliente.getObservaciones() );
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



