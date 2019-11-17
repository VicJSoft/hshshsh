/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;
import Models.Taxistas;
import Resources.statics.Statics;
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
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;

    public ConexionLecturaTaxistas() {
    
        connection = Statics.getConnections();
        
    }
    
    
    
    public ObservableList<String> getTaxistas_id_name()
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
                    //lo trae en pascal case.
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
        query="select * from taxistas";
       // String direccion;
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            while(rs.next())
            {
                /*
                direccion=rs.getString(4)+","+rs.getString(5);
                if(rs.getString(6)!=null)
                {
                    //direc+= Numero interior
                    direccion+=","+rs.getString(6);
                }
                if(rs.getString(7)!=null)
                {
                    //direc+= Colonia
                    direccion+=","+rs.getString(7);
                }
               */
                taxistas.add(
                        new Taxistas(
                                rs.getInt(1),//id
                                rs.getString(2), //nombre
                                rs.getString(3),
                                rs.getDate(4).toLocalDate(), 
                                "0".equals(rs.getString(5))?Statics.sexo.get(0):Statics.sexo.get(1), /*sexo 0 1*/
                                rs.getString(6),//calle
                                rs.getString(7),
                                rs.getString(8),//numInt
                                rs.getString(9),//NumExt
                                rs.getString(10)
                        )
                        
                
                );
                
                /*taxistas.add(
                        new Taxistas(
                                rs.getInt(1),
                                rs.getString(2),//nombre
                                rs.getString(3),//telefono
                                
                                direccion,//observaciones
                                rs.getString(8),
                                String.valueOf(rs.getDate(9)),
                                "0".equals(rs.getString(10))?Statics.sexo.get(0):Statics.sexo.get(1))
                );*/
              
            }
            
            ps.close();
        }
        catch(SQLException ex)
        {
         
        }
        
        return taxistas;
    }
    
    
     
    
}
