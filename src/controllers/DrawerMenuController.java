/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.secundarios.EmpleadosController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.Servicios;

/**
 * FXML Controller Drawer
 *
 * @author VicEspino
 */
public class DrawerMenuController implements Initializable {

   public static Ventana_PrincipalController ventana_PrincipalController;
    @FXML
    private AnchorPane menu;
    
    @FXML
    private void empleados(ActionEvent event)
    {
        try {
            FXMLLoader empleadosLoader = new FXMLLoader(getClass().getResource("/views/secundarios/Empleados.fxml"));
           
            AnchorPane menu= empleadosLoader.load();
            //Controlador propio de la vista.
            //TODO setear eventos con interfaces a cada boton.
            EmpleadosController empleadosController = empleadosLoader.getController();
           
        } catch (IOException ex) {
          
            Logger.getLogger(Ventana_PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML 
    private void cerrarSesion(ActionEvent event) throws IOException
    {
        Parent ventana = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(ventana);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
                
        Servicios.cerrarVentana(event);
               
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
