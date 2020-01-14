/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Resources.persistencia.SharePreferencesDB;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.Servicios;

/**
 *
 * @author VicEspino
 */
public class main extends Application {
   
    private static ServerSocket SERVER_SOCKET;
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
      // Parent root = FXMLLoader.load(getClass().getResource("/views/Ventana_Principal.fxml"));
        //Scene scene = new Scene(new Ventana_ErrorController(null, new Exception("Erro xd"), STYLESHEET_MODENA, STYLESHEET_CASPIAN));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getScene().getRoot().setEffect(new DropShadow());
        
        primaryStage.show();

        SharePreferencesDB.getConfiguracion();
        primaryStage.getIcons().add(new Image("/Resources/imagenes/iconos/Taxi/taxi.png" ));
        
    }
    
    public static void main(String[] args){
                 try {
            
                    SERVER_SOCKET = new ServerSocket(1334);
              
                } catch (IOException ex) {
                    Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
                   Servicios.crearVentanaError(null, "Error RunTime", "Ejecución fallida", "Se intenta duplicar el proceso.\nCierre primero la instancia existente de la aplicación, antes de intentar iniciar otra."+
                           "\nPuede terminar el proceso residual anterior en el administrador de tareas.");

                }
 
            launch(args);
            
    }
    
}
