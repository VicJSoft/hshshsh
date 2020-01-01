package Resources.persistencia;


import java.io.FileNotFoundException;
import java.util.logging.Logger;
import java.util.logging.Level;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;

public class SharePreferencesDB 
{
    public static final String CONFIGURACION_DB="configDB.json";

    private String ip;
    private String puerto;
    private String user;
    private String pass;

    public SharePreferencesDB(String ip, String puerto, String user, String pass) {
        this.ip = ip;
        this.puerto = puerto;
        this.user = user;
        this.pass = pass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    
    
    
    public static SharePreferencesDB initConfig()
    {
        Writer writer =null;
        SharePreferencesDB sharePreferencesDB = new SharePreferencesDB("localhost","3306","root","");
        Gson gson = new Gson();
        try
        {
            writer  = new FileWriter(CONFIGURACION_DB);
            gson.toJson(sharePreferencesDB,writer);
            
        } 
        catch (IOException ex)
        {
            Logger.getLogger(SharePreferencesDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(SharePreferencesDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //HACER que las preferencias sean estaticas para desde aquí devolver la instancia sharepreferences por defecto.
        return sharePreferencesDB;
    }
  
    public static SharePreferencesDB getConfiguracion()
    {
        Gson gson = new Gson();
        SharePreferencesDB sharedPreferences=null;
        try
        {
            sharedPreferences= gson.fromJson(new FileReader(CONFIGURACION_DB),SharePreferencesDB.class);
        } 
        catch (FileNotFoundException ex) 
        {//si no encuentra el archivo, genera las configuraciones por defecto.
            sharedPreferences = initConfig();// genera el archivo por defecto .json donde se almacenarán las credenciales para el inicio de decisión
            Logger.getLogger(SharePreferencesDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sharedPreferences;
    }
    
    public static void setConfiguracion(SharePreferencesDB sharePreferences)
    {
        Gson gson = new Gson();
        Writer writer=null;
        try
        {
            writer = new FileWriter(CONFIGURACION_DB);
            gson.toJson(sharePreferences, writer);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(SharePreferencesDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(SharePreferencesDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 

}
