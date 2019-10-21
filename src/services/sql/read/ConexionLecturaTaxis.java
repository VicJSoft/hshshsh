/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;

import Models.Taxis;
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
public class ConexionLecturaTaxis 
{
    PreparedStatement ps;
    String query="",auxiliar="";
    ResultSet rs;
    boolean key;
    
    public ObservableList<Taxis> getTaxis(Connection connection)
    {
        query="select id_unidad, marca, modelo,placa,nombre from unidades, taxistas where unidades.id_taxista=taxistas.id_taxista";
        
        ObservableList<Taxis> listaTaxis = FXCollections.observableArrayList();
        try
        {
            ps = connection.prepareStatement(query);
            rs= ps.executeQuery();
            while(rs.next())
            {
                listaTaxis.add(new Taxis(String.valueOf(rs.getInt(1)),rs.getString(2).toUpperCase(),rs.getString(3),rs.getString(4).toUpperCase(),rs.getString(5).toUpperCase()));     
            }
            ps.close();

        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
        return listaTaxis;
        
    }
    
}
