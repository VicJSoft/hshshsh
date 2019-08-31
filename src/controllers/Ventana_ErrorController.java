/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.Servicios;

/**
 * FXML Controller class
 *
 * @author VicEspino
 */
public class Ventana_ErrorController extends AnchorPane{

    @FXML
    private ImageView iv_icon;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Label lbl_tittleBar;
    @FXML
    private AnchorPane ap_tittleBar;
    @FXML
    private Label lbl_tittleContent;
    @FXML
    private JFXTextArea txt_contentError;
    @FXML
    private Button btn_aceptar;

    public Ventana_ErrorController(String tittleBarMessage,String tittleContentMessage, String contentMessage){
       
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Ventana_Error.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(Ventana_ErrorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.lbl_tittleBar.setText(tittleBarMessage);
        this.lbl_tittleContent.setText(tittleContentMessage);
        this.txt_contentError.setText(contentMessage);
        
    }

    @FXML
    private void btnCerrar_Click(ActionEvent event) {
        
        Servicios.cerrarVentana(event);
        
    }

    @FXML
    private void tittleBarr_Drag(MouseEvent event) {
        Servicios.tittleBar_Drag(event);
        
    }

    @FXML
    private void tittleBar_Pressed(MouseEvent event) {
        Servicios.tittleBar_Pressed(event);
    }

   
    
    
}
