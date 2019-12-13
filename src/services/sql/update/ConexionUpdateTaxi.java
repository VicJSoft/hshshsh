package services.sql.update;

import Models.Taxis;
import Resources.statics.Statics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ESPINO
 */
public class ConexionUpdateTaxi {
    PreparedStatement ps;
    String query="",auxiliar="";
    ResultSet rs;
    boolean key;
    private final Connection connection;

    public ConexionUpdateTaxi() {
        
        this.connection = Statics.getConnections();
    
    }
   
    public boolean update(Taxis taxiUpdate){
        
        query = "UPDATE unidades SET unidades.marca = ?, unidades.modelo = ?,unidades.placa = ?, "+
                "unidades.id_taxista = ?"
                + " WHERE  id_unidad = ?";
        //+ "unidades.id_taxista = (SELECT taxistas.id_taxista FROM taxistas WHERE taxistas.nombre = ? )"
    //     query="insert into unidades values(?,?,?,?,?)";
        try
        {
            ps = connection.prepareStatement(query);
  
            ps.setString(1, taxiUpdate.getMarca() );
            ps.setInt(2, taxiUpdate.getModelo());
            ps.setString(3, taxiUpdate.getPlaca());
            ps.setInt(4, taxiUpdate.getId_taxista());
            ps.setInt(5, taxiUpdate.getId());
            
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
