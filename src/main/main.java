/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import persistencia.SharePreferences;

/**
 *
 * @author VAPESIN
 */
public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.getScene().getRoot().setEffect(new DropShadow());
        
        primaryStage.show();

        SharePreferences.initConfig();// genera el archivo por defecto .json donde se almacenarán las credenciales para el inicio de decisión
    }
    
    public static void main(String[] args){
        launch(args);
    }
    
}
