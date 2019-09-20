/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//ventana prueba

package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.Servicios;

/**
 * FXML Controller class
 *
 * @author HP V2
 */
public class AcercadeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane ap_tittleBar;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void btnCerrar_Click(ActionEvent event) {
        
        Servicios.cerrarVentana(event);
    }

    @FXML
    private void window_Released(MouseEvent event) {
    }

    @FXML
    private void window_drag(MouseEvent event) {
    }

    @FXML
    private void windows_Presed(MouseEvent event) {
    }

    
}
