/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Resources.persistencia.SharePreferencesDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author VicEspino
 */
public class main extends Application {

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
        
    }
    
    public static void main(String[] args){
        launch(args);
    }
    
}
