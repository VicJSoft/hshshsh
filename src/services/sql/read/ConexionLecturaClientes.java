/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;

import Models.Clientes;
import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vicen
 */
public class ConexionLecturaClientes
{
    private boolean key;
    private String query;
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;

    public ConexionLecturaClientes() {
        
        connection = Statics.getConnections();
    
    }
    //este metodo es una mamada, si ya se tiene getClientes normal, 
    //por qué no usar ese y extraer lo requerido desde un objeto de esa lista?.
    public ObservableList<String> getClientes_id_name()
    {
        ObservableList<String> clientes =  FXCollections.observableArrayList();
        query="select telefono,nombre from clientes";
        try
        {  
            
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                
                //lo trae en pascal case.
                clientes.add(rs.getString(1)+"  "+rs.getString(2));
                
            }
            
        
                                      
            ps.close();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        
        return clientes;
    }
    
    public ObservableList<Clientes> getClientes()
    {
        ObservableList<Clientes> clientes =  FXCollections.observableArrayList();
        query="select * from clientes";
        String direccion;
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
          
                clientes.add( crearCliente(rs) );
                
            }
            
            ps.close();
        }
        catch(SQLException ex)
        {
            System.out.println("Error getClientes() metodo.");
        }
        
        return clientes;
    }
    
    private Clientes crearCliente(ResultSet rs) throws SQLException{
        Clientes clienteCreado = null;//nuncá retornará null.
        
        clienteCreado = 
            new Clientes(
                        rs.getString(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getString(5),                                 
                        rs.getString(6),                                 
                        rs.getString(7)
                    );
        
        return clienteCreado;
    }
    /**
     * Trae el cliente que le corresponda ese numero.
     * @param numero
     * @return 
     * Si no existe cliente con ese numero, entonces retorna un onjeto clientes, nulo.
     */
    public Clientes getClientByNumber(String numero){
        Clientes clienteObtenido = null;
        try {
            query="SELECT * FROM clientes WHERE telefono = " + numero;
            
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            if(rs.first())
                clienteObtenido = crearCliente(rs);
            else
                clienteObtenido = null;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionLecturaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clienteObtenido;
    }
}
