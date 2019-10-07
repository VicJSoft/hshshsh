/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import services.Servicios;

/**
 * FXML Controller class
 *
 * @author vicen
 */
public class TaxisController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAdd_OnAction(ActionEvent event) throws IOException 
    {
         Servicios.crearVentana(
               getClass().getResource("/views/crud/TaxisCRUD.fxml"),
               Servicios.getStageFromEvent(event));
    
    }
    
}
