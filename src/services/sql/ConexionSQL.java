package services.sql;


import Resources.persistencia.SharePreferencesDB;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
public class ConexionSQL 
{
 //  private final String  PATH="jdbc:mysql://localhost:3306/sitio_taxi";
   private final String  NAME="com.mysql.jdbc.Driver";
   //private final String  USER="root";
   //private final String  PASS="";
   Connection connection=null;

   public  Connection getConexion() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException 
   {
 
           Class.forName(NAME).newInstance();
           SharePreferencesDB configuracionDB = SharePreferencesDB.getConfiguracion();
           String path = "jdbc:mysql://";
           path+=configuracionDB.getIp() + ":";
           path+=configuracionDB.getPuerto() + "/sitio_taxi";
           
           connection = DriverManager.getConnection(path,configuracionDB.getUser(),configuracionDB.getPass());

       
       return connection;
   }
   
   public void desconectar()
   {
       connection=null;
   }
   /***********************************************************************************************************************************************/
    
}
/**************************************************************************************************************************************************/
