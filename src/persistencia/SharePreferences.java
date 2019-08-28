/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import com.google.gson.Gson;
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
    public static final String CREFENCIALES="config.json";
    private boolean recordar;
    private String usuario;

    public SharePreferences(boolean recordar, String usuario) {
        this.recordar = recordar;
        this.usuario = usuario;
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
    
    
    
}
