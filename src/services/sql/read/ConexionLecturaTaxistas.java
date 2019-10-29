/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;
import Models.Taxistas;
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
public class ConexionLecturaTaxistas 
{
     private boolean key;
    private String query;
    private Connection c;
    private ResultSet rs;
    private PreparedStatement ps;
    public ObservableList<String> getTaxistas_id_name(Connection connection)
    {
        ObservableList<String> taxistas =  FXCollections.observableArrayList();
        query="select id_taxista,nombre from taxistas";
        try
        {  
            
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next()){
                if(rs.getInt(1)!=0)
                {
                     taxistas.add(rs.getInt(1)+"  "+rs.getString(2));
                }
            }
            
        
                                      
            ps.close();
        }
        catch(SQLException ex)
        {
         
        }
        
        return taxistas;
    }
    public ObservableList<Taxistas> getTaxistas(Connection connection)
    {
        ObservableList<Taxistas> taxistas =  FXCollections.observableArrayList();
        query="select id_taxista,nombre,telefono,calle, num_ext,num_int,colonia, observaciones, fecha_nacimiento from taxistas";
        String direccion;
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                direccion=rs.getString(4)+","+rs.getString(5);
                if(rs.getString(6)!=null)
                {
                    direccion+=","+rs.getString(6).toUpperCase();
                }
                if(rs.getString(7)!=null)
                {
                    direccion+=","+rs.getString(7);
                }
               
                
                taxistas.add(new Taxistas(String.valueOf(rs.getInt(1)),rs.getString(2).toUpperCase(),rs.getString(3),direccion.toUpperCase(),rs.getString(8).toUpperCase(),String.valueOf(rs.getDate(9))));
              
            }
            
            ps.close();
        }
        catch(SQLException ex)
        {
         
        }
        
        return taxistas;
    }
    
    
     
    
}
