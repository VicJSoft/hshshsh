/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Resources.interfaces.Cargar_Secundaria;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

    private Cargar_Secundaria cargar_Secundaria;
    @FXML
    private AnchorPane menu;
    
    @FXML
    private void empleados(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Empleados.fxml","Empleados");
    }
    @FXML
    private void taxistas(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Taxistas.fxml","Taxistas");
    }
    @FXML
    private void clientes(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Clientes.fxml","Clientes");
    }
    @FXML
    private void servicios(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Servicios.fxml","Servicios");
    }
    @FXML
    private void taxis(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Taxis.fxml","Taxis");
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
    public void setGuardarEnviarListener(Cargar_Secundaria cargar_Secundaria){
        this.cargar_Secundaria = cargar_Secundaria;
    }
    
}
