/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.read;

import Models.Taxis;
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
public class ConexionLecturaTaxis 
{
    PreparedStatement ps;
    String query="",auxiliar="";
    ResultSet rs;
    boolean key;
    Connection connection;

    public ConexionLecturaTaxis() {
    
        connection = Statics.getConnections();
    
    }
    
    
    
    
    public ObservableList<Taxis> getTaxis()
    {
        query="select id_unidad, marca, modelo,placa,taxistas.nombre,taxistas.id_taxista from unidades, taxistas where unidades.id_taxista=taxistas.id_taxista";
//la misma columna, no hay registro duplicados.        
//select id_unidad, marca, modelo,placa,nombre,taxistas.id_taxista, unidades.id_taxista from unidades, taxistas where unidades.id_taxista=taxistas.id_taxista
        ObservableList<Taxis> listaTaxis = FXCollections.observableArrayList();
        try
        {
            ps = connection.prepareStatement(query);
            rs= ps.executeQuery();
            while(rs.next())
            {
                listaTaxis.add(
                        new Taxis(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getInt(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getInt(1)                        
                        )
                );     
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
