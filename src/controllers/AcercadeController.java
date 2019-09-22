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
import javafx.stage.Stage;
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
    private void window_drag(MouseEvent event) {
       Stage ventana = Servicios.getStageFromEvent(event);

        //si esta maximizada y se aplica un drag, entonces minimizará
        if(ventana.isMaximized()){
              
           
            minimizarVentanaHaciendoDrag(ventana,event);         
                     
            //return;
        }else{            
           Servicios.tittleBar_Drag(event);
        
        }

        
    }

    @FXML
    private void windows_Presed(MouseEvent event) {
         Servicios.tittleBar_Pressed(event);
    }
    private void minimizarVentanaHaciendoDrag(Stage ventana,MouseEvent event){
    
          //crear lógica, para que cuando se quite el maximizado, el raton 
            //se encuentre en la misma posición de proporción del toolbar.
           
            double clickSceneX = event.getSceneX();
            double clickSceneY = event.getSceneY();
            double clickScreenX =event.getScreenX();
            double clickScreenY =event.getScreenY();
            
            double widthMaximized = ventana.getScene().getWidth();
            double heightMaximized = ventana.getScene().getHeight();
            
            //obtiene la proporcion del la posición del click.(50%, 60% etc).//la proporcion siempre es la misma.
            double proporcionClickX =clickSceneX/widthMaximized;
            double proporcionClickY = clickSceneY/heightMaximized;
            

            
        
    }

    
}
