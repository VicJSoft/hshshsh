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
public class ServiciosController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAdd_OnAction(ActionEvent event) throws IOException {
       /* Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/crud/ServiciosCRUD.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        
        
        stage.initOwner(Servicios.getStageFromEvent(event));            
        stage.initModality(Modality.WINDOW_MODAL);
        
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
*/    
       Servicios.crearVentana(
               "/views/crud/ServiciosCRUD.fxml",
               Servicios.getStageFromEvent(event),
               getClass());
    }

    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
    }

    @FXML
    private void btnEdit_OnAction(ActionEvent event) {
    }
    
}
