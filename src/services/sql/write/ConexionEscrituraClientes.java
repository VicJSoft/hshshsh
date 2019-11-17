package services.sql.write;

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
    
    
    public boolean insertClientes(String telefono, String nombre,String calle,String colonia, String numExt,String numInt,String observaciones)
    {
        
        query="insert into clientes values(?,?,?,?,?,?,?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1,telefono );
            ps.setString(2, nombre );
            ps.setString(3, calle );
            ps.setString(4, colonia);
            if(numExt.equals(""))
                ps.setString(5, null);
            else
                ps.setString(5, numExt);
            if(numInt.equals(""))
                ps.setString(6, null);
            else
                ps.setString(6, numInt);
            ps.setString(7, observaciones);
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
