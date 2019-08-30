/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;
/**
 *
 * @author VicEspino
 */
public class Servicios {
    
    public static void cerrarVentana(ActionEvent event){
        Servicios.cerrarVentana( (Stage) ((Node)event.getSource()).getScene().getWindow());
    }
    
    public static void cerrarVentana(Stage stage){
        stage.close();
    }
    
    public static void crearVentanaError(Window ownerWindowError,Exception error,String tittleBar,String tittleContent){
        //TODO Crear XML de la ventana error.
        System.out.println("Error presente.");
    }
    
    
}
