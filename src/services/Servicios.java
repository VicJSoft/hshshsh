/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import controllers.Ventana_ErrorController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import java.io.IOException;
/**
 *
 * @author VicEspino
 */
public class Servicios {
    
    private static double xOffset;
    private static double yOffset;
    
    
    public static void cerrarVentana(Event event){
        Servicios.cerrarVentana( (Stage) ((Node)event.getSource()).getScene().getWindow());
    }
    
    public static void cerrarVentana(Stage stage){
        stage.close();
    }
    
    public static void crearVentanaError(Window ownerWindowError,String tittleBar,String tittleContent,String contentMessage){
        //TODO Crear XML de la ventana error.
        System.out.println("Error presente.");
        
        Ventana_ErrorController ventanaError = new Ventana_ErrorController( tittleBar, tittleContent , contentMessage);
        Stage stage = new Stage();
        Scene scene = new Scene(ventanaError);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initOwner(ownerWindowError);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }
    
    
    public static void maximizeWindow(Stage stage,boolean state){
        stage.setMaximized(state);
    }

    public static void toggleMaximizeWindow(Stage stage){
        if(stage.isMaximized()){
            stage.setMaximized(false);
        }
        else{
            stage.setMaximized(true);
        }
        
    }
    
    public static void toggleMaximizeWindow(Event event){
        
        toggleMaximizeWindow(Servicios.getStageFromEvent(event)); 
        
    }
    
    public static Stage getStageFromEvent(Event event){
        return ((Stage)((Node)event.getSource()).getScene().getWindow());
    }
    
    public static void crearVentana(AnchorPane ventana){
        
        Stage stage = new Stage();
        Scene scene = new Scene(ventana);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        
        
    }
    
    public static void crearVentana(String rutaXML) throws IOException {
       
    }
    
    public static void tittleBar_Pressed(MouseEvent event){
        Servicios.xOffset = event.getSceneX();//guarda coord iniciales del clic.        
        Servicios.yOffset = event.getSceneY();
    }
    
    
    public static void tittleBar_Drag(MouseEvent event){
        
        Stage primaryStage = getStageFromEvent(event);
        primaryStage.setX(event.getScreenX()- xOffset);
        primaryStage.setY(event.getScreenY() - yOffset );
    }
    
    public static void setOffsets(double x, double y){
        xOffset = x;
        yOffset = y;
    }
    
    public static void minimizeWindows(ActionEvent event) {
        
        getStageFromEvent(event).setIconified(true);
        
    }

    
    
    
}
