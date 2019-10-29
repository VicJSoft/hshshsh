/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.write;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConexionEscrituraTaxis 
{
    private boolean key;
    private String query;
    private Connection c;
    private ResultSet rs;
    private PreparedStatement ps;
    public boolean insertTaxis(int id_unidad, String marca,int modelo,String placa,int id_taxista, Connection connection)
    {
        
        query="insert into unidades values(?,?,?,?,?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1,id_unidad );
            ps.setString(2, marca );
            ps.setInt(3, modelo );
            ps.setString(4, placa);
            ps.setInt(5, id_taxista);
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
