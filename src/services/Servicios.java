/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import controllers.LoginController;
import controllers.Ventana_ErrorController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
/**
 * Esta clase proporciona metódos generalers para el código de todo el proyecto,
 * para no repetir el código, teniendo un manejor m´a sencillo.
 * @author VicEspino
 */
public class Servicios {
    
    private static double xOffset;
    private static double yOffset;
    
    /**
     * Cierra una ventna de donde provenga el evento proporcionado.
     * @param event 
     */
    public static void cerrarVentana(Event event){
        Servicios.cerrarVentana( (Stage) ((Node)event.getSource()).getScene().getWindow());
    }
    /**
     * Cierra la ventana del stage proporcionado.
     * @param stage 
     */
    public static void cerrarVentana(Stage stage){
        stage.close();
    }
    /**
     * Crea una ventana de error, de estilo modal. Necesita indicar quien es la ventana padre, 
     * para hacerla modeal.
     * @param ownerWindowError
     * Ventana padre de la ventana error a crear.
     * @param tittleBar
     * Titulo que llevara´ la ventana de error.
     * @param tittleContent
     * Titulo interno de la ventana.
     * @param contentMessage 
     * Texto que informara´ sobre el error especificado.
     */
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
    /**
     * Retorna el stage dueño del evento proporcionado.
     * @param event
     * Evento de donde se obtendra´ el stage.
     * @return 
     * 
     */
    public static Stage getStageFromEvent(Event event){
        return ((Stage)((Node)event.getSource()).getScene().getWindow());
    }
    

    /**
     * Crea ventana modal dado un owner, y un URL a partir de getClass().getResources();
     * @param urlXML
     * Recurso a crear ventana.
     * @param ownerWindow
     * Si es nulo el owner, sigifica que será una ventana normal
     * @param aClass
     * Se necesita para no cargar la interfaz de manera statica, para así obtener el controlador, por medio de una
     * instancia FXMLLoader (omitiendo FXMLLoader.load(URL)).
     * @return 
     * Retorna el controlador correspondiente en forma de Object (para castearlo al que le corresponda, desde donde se llamó el metodo(opcional) ).
     * @throws IOException 
     */
    public static Object crearVentana(String urlXML,Stage ownerWindow,Class<?> aClass)  {
            FXMLLoader loader = new FXMLLoader (aClass.getResource(urlXML));
            Object controladorObject =null;
        try {
            Parent root = loader.load();
            controladorObject = loader.getController();
            Stage stage = new Stage();
            
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            
            if(ownerWindow!= null){
                stage.initOwner(ownerWindow);
                stage.initModality(Modality.WINDOW_MODAL);
            }
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controladorObject;
    }
    /**
     * Cambiar´a el alor de los offsets, para el uso del servicio drag.
     * @param event 
     */
    public static void tittleBar_Pressed(MouseEvent event){
        Servicios.xOffset = event.getSceneX();//guarda coord iniciales del clic.        
        Servicios.yOffset = event.getSceneY();
    }
    
    /**
     * Mueve la ventana de donde proviene el MouseEvent, 
     * para desplazarlo en la ventana.
     * @param event 
     */
    public static void tittleBar_Drag(MouseEvent event){
        
        Stage primaryStage = getStageFromEvent(event);
        primaryStage.setX(event.getScreenX()- xOffset);
        primaryStage.setY(event.getScreenY() - yOffset );
    }
    
    /**
     * Setea offsets de referencía que ser´an usados principalmente para desplazar una ventana
     * en la pantalla.
     * @param x
     * Coord X de referencia.
     * @param y 
     * Coord Y de referencia.
     */
    public static void setOffsets(double x, double y){
        xOffset = x;
        yOffset = y;
    }
    
    /**
     * Minimizara´ la ventana de donde proviene este evento.
     * @param event 
     * Evento que debera´ venir del clic del botón de minimizar de una ventana.
     */
    public static void minimizeWindows(ActionEvent event) {
        
        getStageFromEvent(event).setIconified(true);
        
    }

  

    
    
    
}
