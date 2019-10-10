/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources.statics;

import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import services.sql.ConexionSQL;

/**
 *
 * @author vicen
 */
public class Statics 
{
    public static ObservableList<String> sexo = FXCollections.observableArrayList("Masculino","Femenino");
    public static ObservableList<String> tipo_empledo = FXCollections.observableArrayList("Administrativo","Modulador");
    public static ObservableList<String> marcas = FXCollections.observableArrayList("APRIO","ATOS","ATTITUDE","AVEO","BEAT","CRUZE","FIESTA","i10",
            "MARCH","MATIZ","POINTER","SENTRA","SPARK","TIIDA","TSURU","VERSA");
    
    private static Connection connection;
    public static void setConnections(Connection connection)
    {
        Statics.connection=connection;
    }
    public static Connection getConnections()
    {
       return Statics.connection;
    }
}
