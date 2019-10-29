/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;

import Models.Clientes;
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
public class ConexionLecturaClientes
{
    private boolean key;
    private String query;
    private Connection c;
    private ResultSet rs;
    private PreparedStatement ps;
    public ObservableList<Clientes> getClientes(Connection connection)
    {
        ObservableList<Clientes> clientes =  FXCollections.observableArrayList();
        query="select telefono,nombre,calle, num_ext,num_int,colonia, observaciones from clientes";
        String direccion;
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                direccion=rs.getString(3)+","+rs.getString(4);
                if(rs.getString(5)!=null)
                {
                    direccion+=","+rs.getString(5).toUpperCase();
                }
                if(rs.getString(6)!=null)
                {
                    direccion+=","+rs.getString(6);
                }
               
                
                clientes.add(new Clientes(rs.getString(1),rs.getString(2).toUpperCase(),direccion,rs.getString(7).toUpperCase()));
              
            }
            
            ps.close();
        }
        catch(SQLException ex)
        {
         
        }
        
        return clientes;
    }
}
