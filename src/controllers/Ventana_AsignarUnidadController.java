/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Interfaces.IAbrir_Edicion_Registros;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.Servicios;
import services.sql.read.ConexionLecturaUnidades;

/**
 * FXML Controller class
 *
 * @author VAPESIN
 */
public class Ventana_AsignarUnidadController implements Initializable {

    @FXML
    private AnchorPane ap_tittleBar;
    @FXML
    private ImageView iv_icon;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Label lbl_tittleBar;
    @FXML
    private JFXComboBox<String> cb_unidad;
    @FXML
    private Button btnAceptar;
    private IAbrir_Edicion_Registros abrir_Edicion_Registros;
    private final ConexionLecturaUnidades conexionLecturaUnidades = new ConexionLecturaUnidades();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cb_unidad.setItems(conexionLecturaUnidades.getUnidades());
    }    

    @FXML
    private void btnCerrar_Click(ActionEvent event) {
        
        Servicios.cerrarVentana(event);
        
    }

    @FXML
    private void tittleBarr_Drag(MouseEvent event) {
    }

    @FXML
    private void tittleBar_Pressed(MouseEvent event) {
    }

    @FXML
    private void btnAceptar_OnAction(ActionEvent event) {
        
        if(!cb_unidad.getSelectionModel().isEmpty()){
            //retornar valor seleccionado.
            Integer idUnidad = new Integer(cb_unidad.getSelectionModel().getSelectedItem().split(" ")[0]);
            abrir_Edicion_Registros.registroEditNuevo(idUnidad);
            this.btn_cerrar.fire();
        }
        
    }
    
    public void setIAbrirEdicionRegistro(IAbrir_Edicion_Registros abrir_Edicion_Registros){
        this.abrir_Edicion_Registros = abrir_Edicion_Registros;
    }
    
}
