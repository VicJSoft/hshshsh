/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vicen
 */
public class SharePreferences 
{
    public static final String CREFENCIALES="config.json";// este archivo se guarda en la raiz de la carperta
    private boolean recordar;
    private String usuario;

    public SharePreferences()
    {
        
    }
    public SharePreferences(boolean recordar, String usuario) {
        this.recordar = recordar;
        this.usuario = usuario;
    }
    public void setRecordar(boolean recordar)
    {
        this.recordar=recordar;
    }
    public boolean getRecordar()
    {
        return recordar;
    }
    public void setUsuario(String usuario)
    {
        this.usuario=usuario;
    }
    public String getUsuario()
    {
        return usuario;
    }
    public static void initConfig()
    {
        Writer writer =null;
        SharePreferences sharePreferences = new SharePreferences(false, "");
        Gson gson = new Gson();
        try
        {
            writer  = new FileWriter(CREFENCIALES);
            gson.toJson(sharePreferences,writer);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(SharePreferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(SharePreferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
  
    public static SharePreferences getCredenciales()
    {
        Gson gson = new Gson();
        SharePreferences sharedPreferences=null;
        try
        {
            sharedPreferences= gson.fromJson(new FileReader(CREFENCIALES),SharePreferences.class);
        } 
        catch (FileNotFoundException ex) 
        {
            initConfig();// genera el archivo por defecto .json donde se almacenarán las credenciales para el inicio de decisión
            Logger.getLogger(SharePreferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sharedPreferences;
    }
    public static void setCredenciales(SharePreferences sharePreferences)
    {
        Gson gson = new Gson();
        Writer writer=null;
        try
        {
            writer = new FileWriter(CREFENCIALES);
            gson.toJson(sharePreferences, writer);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(SharePreferences.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                writer.close();
            }
            catch (IOException ex)
            {
                Logger.getLogger(SharePreferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
}
