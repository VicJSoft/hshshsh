package services.sql.write;

import Models.Clientes;
import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        //si el cliente ya existe no hay necesidad de hacer algo, solo retornar true
        //para que continue el flujo donde se llamó a este metodo.-
        if(clienteExist(cliente.getTelefono())){
           return true;
        }
        query="insert into clientes values(?,?,?,?,?,?,?)";
        try
        {
            ps =null;
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
    
    /**
     * Indica si el numero dado existe en la tabla clientes.
     * @param numero
     * Numero de telefono del cliente a verificar.
     * @return 
     * TRUE:
     *  Cuando EXISTE al menos 1 registro con ese número(en teoría solo sería 1 o ninguno).
     * FALSE:
     *  Cuando no existe ese cliente en la tabla clientes.
     *  Cuando hubo SQLException también retorna false.
     */
    private boolean clienteExist(String numero){
        try {
            String queryExist = "SELECT * FROM clientes WHERE telefono = ?";
            
            ps = connection.prepareCall(queryExist);
            ps.setString(1, numero);
            ResultSet rs = ps.executeQuery();
            
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionEscrituraClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}



