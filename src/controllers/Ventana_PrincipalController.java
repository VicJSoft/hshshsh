/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.events.JFXDrawerEvent;
import controllers.Ventana_ErrorController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import services.Servicios;

/**
 * FXML Controller class
 *
 * @author VicEspino
 */
public class Ventana_PrincipalController implements Initializable {

    @FXML
    private AnchorPane ap_tittleBar;
    @FXML
    private ImageView iv_iconBar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Label lbl_tittleBar;
    @FXML
    private Button btn_Hamburguesa;
    @FXML
    private JFXDrawer drawer_Menu;
    @FXML
    private Button btn_maximizar;
    @FXML
    private Button btn_minimizar;

 

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
    
    @FXML
    private void btnHamburguesa_Click(ActionEvent event){
        
         if (drawer_Menu.isOpened()) {
            drawer_Menu.close();
        } else {
            drawer_Menu.open();
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawer_Menu.setOnDrawerClosed((event) -> {
           drawer_Menu.toBack();
        });
        drawer_Menu.setOnDrawerOpening(new EventHandler<JFXDrawerEvent>() {
            @Override
            public void handle(JFXDrawerEvent event) {

                drawer_Menu.toFront();
            }
        });
            
        try {
            FXMLLoader drawerLoader = new FXMLLoader(getClass().getResource("/views/DrawerMenu.fxml"));
            VBox vbox_menu = drawerLoader.load();
            //Controlador propio de la vista.
            DrawerMenuController drawerController = drawerLoader.getController();
            drawer_Menu.setSidePane(vbox_menu);

        } catch (IOException ex) {
            Logger.getLogger(Ventana_PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void btnMaximizar_Click(ActionEvent event) {
    }

    @FXML
    private void btnMinimizar_Click(ActionEvent event) {
    }
    
}
