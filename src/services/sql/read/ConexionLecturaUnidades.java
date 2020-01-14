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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 *
 * @author vicen
 */
public class ConexionLecturaUnidades 
{
    private boolean key;
    private String query;
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;

    public ConexionLecturaUnidades() {
    
        connection = Statics.getConnections();
        
    }
    
    
    
    public boolean existeUnidad(int id_unidad)
    {
        key=false;
        query="select * from unidades where id_unidad='"+id_unidad+"'";
        try
        {
            ps = connection.prepareStatement(query);
            rs=ps.executeQuery();
            key = !rs.first();
            
            ps.close();
        }
        catch(SQLException ex)
        {
            key=false;
        }
        
        return key;
    }
    
    public ObservableList<String> getUnidades(){
        ObservableList<String> listaUnidades = FXCollections.observableArrayList();
        try {
            query = "SELECT unidades.id_unidad,taxistas.nombre " +
                    " FROM unidades INNER JOIN taxistas " +
                    " ON unidades.id_taxista = taxistas.id_taxista ";
            
            ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                listaUnidades.add( rs.getString(1) + " " + rs.getString(2) );
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionLecturaUnidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaUnidades;
    }
    
}
