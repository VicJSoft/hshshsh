package services.sql.write;

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
public class ConexionEscrituraClientes 
{
    private boolean key;
    private String query;
    private Connection c;
    private ResultSet rs;
    private PreparedStatement ps;
    public boolean insertClientes(String telefono, String nombre,String calle,String colonia, String numExt,String numInt,String observaciones, Connection connection)
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
