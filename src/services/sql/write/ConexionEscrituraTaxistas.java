/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.sql.write;

import Models.Taxistas;
import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author vicen
 */
public class ConexionEscrituraTaxistas 
{
    
    private boolean key;
    private String query;
    private Connection connection;
    private ResultSet rs;
    private PreparedStatement ps;

    public ConexionEscrituraTaxistas() {
    
        connection = Statics.getConnections();
        
    }
    
    public boolean insertTaxista(Taxistas taxista){
        query="insert into taxistas values(null,?,?,?,?,?,?,?,?,?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1,taxista.getNombre() );
            ps.setString(2, taxista.getTelefono());
            ps.setDate(3, taxista.getFecha_nacimiento() );
           
            if(taxista.getSexo().equals("MASCULINO"))
            {
                ps.setInt(4, 0);
            }
            else
            {
                 ps.setInt(4, 1);//femenino
            }
            
            ps.setString(5, taxista.getCalle());
            ps.setString(6, taxista.getColonia());
            if(taxista.getNumExt().equals(""))
                ps.setString(7, null);
            else
                ps.setString(7, taxista.getNumExt());
            if(taxista.getNumInt().equals(""))
                ps.setString(8, null);
            else
                ps.setString(8, taxista.getNumInt());
            ps.setString(9, taxista.getObservaciones());
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
    /*
    public boolean insertTaxistas(String nombre,String telefono,LocalDate fechaN,String sexo,String calle,String colonia,String numExt,String numInt,String observaciones)
    {
        
       
        query="insert into taxistas values(null,?,?,?,?,?,?,?,?,?)";
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1,nombre );
            ps.setString(2, telefono);
            ps.setDate(3, (java.sql.Date.valueOf(fechaN)));
           
            if(sexo.equals("MASCULINO"))
            {
                ps.setInt(4, 0);
            }
            else
            {
                 ps.setInt(4, 1);//femenino
            }
            ps.setString(5, calle);
            ps.setString(6, colonia);
            if(numExt.equals(""))
                ps.setString(7, null);
            else
                ps.setString(7, numExt);
            if(numInt.equals(""))
                ps.setString(8, null);
            else
                ps.setString(8, numInt);
            ps.setString(9, observaciones);
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
    */
}
