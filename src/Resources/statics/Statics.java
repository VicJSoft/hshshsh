/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources.statics;

import Models.Empleados;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author vicen
 */
public class Statics 
{
    public static ObservableList<String> sexo = FXCollections.observableArrayList("MASCULINO","FEMENINO");
    public static ObservableList<String> tipo_empledo = FXCollections.observableArrayList("Administrativo","Modulador");
    public static ObservableList<String> marcas = FXCollections.observableArrayList("APRIO","ATOS","ATTITUDE","AVEO","BEAT","CRUZE","FIESTA","i10",
            "MARCH","MATIZ","POINTER","SENTRA","SPARK","TIIDA","TSURU","VERSA");
    public static ObservableList<String> reportes=FXCollections.observableArrayList("Servicios por Unidad Específica","Servicios por Cliente","Servicios por Modulador","Servicios Programados","Servicios Foráneos");
    
    private static Connection connection;
    
    public static int LUNES = 1;
    public static int MARTES = 2;
    public static int MIERCOLES = 3;
    public static int JUEVES = 4;
    public static int VIERNES = 5;
    public static int SABADO = 6;
    public static int DOMINGO = 7;
    public static int ID_EMPLEADO_SESION_ACTUAL;
    public static Empleados EMPLEADO_SESION_ACTUAL;
    
    public static void setConnections(Connection connection)
    {
        Statics.connection=connection;
    }
    public static Connection getConnections()
    {
       return Statics.connection;
    }
    
    public static class textoValidaciones{
        
      public static String CAMPO_REQUERIDO  = "Este campo es requerido.";
  
    }
    
}
