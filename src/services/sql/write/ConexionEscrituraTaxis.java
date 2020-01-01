/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.write;

import Models.Taxis;
import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConexionEscrituraTaxis 
{
    private boolean key;
    private String query;
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;

    public ConexionEscrituraTaxis() {
    
        connection = Statics.getConnections();
        
    }
    
    
    
    public boolean insertTaxis(Taxis taxiInsertar)
    {
        
        query="insert into unidades values(?,?,?,?,?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1,taxiInsertar.getId() );
            ps.setString(2, taxiInsertar.getMarca() );
            ps.setInt(3, taxiInsertar.getModelo() );
            ps.setString(4, taxiInsertar.getPlaca());
            ps.setInt(5, taxiInsertar.getId_taxista());
            ps.executeUpdate();
            key=true;
            ps.close();
        }
        catch(SQLException ex)
        {
             key=false;
             ex.printStackTrace();
        }
        
        return key;
    }


    
}
