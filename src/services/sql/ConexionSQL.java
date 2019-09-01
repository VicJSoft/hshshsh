package services.sql;


/*** IMPORTS ***************************************************************************************************************************************/
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
/**************************************************************************************************************************************************/
/**
 *
 * @author vicen
 */
/*** CLASS /***************************************************************************************************************************************/
public class ConexionSQL 
{
   /***VARIABLES OR INSTANCES GLOBALS**************************************************************************************************************/
   private final String  PATH="jdbc:mysql://localhost:3306/sitio_taxi";
   private final String  NAME="com.mysql.jdbc.Driver";
   private final String  USER="root";
   private final String  PASS="";
   Connection connection=null;
   /************************************************************************************************************************************************/
   /***CUZTOMIZED PUBLIC METHODS/*******************************************************************************************************************/
   public Connection getConexion() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException 
   {
       
           Class.forName(NAME).newInstance();
           connection = DriverManager.getConnection(PATH,USER,PASS);
       
       
       return connection;
   }
   
   public void desconectar()
   {
       connection=null;
   }
   /***********************************************************************************************************************************************/
    
}
/**************************************************************************************************************************************************/
