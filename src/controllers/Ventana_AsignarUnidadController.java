/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Interfaces.IAbrir_Edicion_Registros;
import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private JFXTextField textField_notas;
    @FXML
    private JFXTextField textField_observaciones;
    @FXML
    private Button btnAceptar;
    private IAbrir_Edicion_Registros abrir_Edicion_Registros;
    private final ConexionLecturaUnidades conexionLecturaUnidades = new ConexionLecturaUnidades();
    int focus=-1;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cb_unidad.setItems(conexionLecturaUnidades.getUnidades());
        focus++;
        JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();
        autoCompletePopup.getSuggestions().addAll(cb_unidad.getItems());
        autoCompletePopup.getStyleClass().add("combo-box-popup");
        autoCompletePopup.getStyleClass().add("jfx-combo-box");
         //autoCompletePopup.getStyleClass().add();
        autoCompletePopup.getStyleClass().add("-fx-border-color: #000000;");
        
        //SelectionHandler sets the value of the comboBox
        autoCompletePopup.setSelectionHandler(event -> {
            cb_unidad.setValue(event.getObject());
        });
        TextField editor = cb_unidad.getEditor();
        editor.textProperty().addListener(observable -> {
            //The filter method uses the Predicate to filter the Suggestions defined above
            //I choose to use the contains method while ignoring cases
            autoCompletePopup.filter(item -> item.toLowerCase().contains(editor.getText().toLowerCase()));
            //Hide the autocomplete popup if the filtered suggestions is empty or when the box's original popup is open
            if (autoCompletePopup.getFilteredSuggestions().isEmpty() || cb_unidad.showingProperty().get()) {
                autoCompletePopup.hide();
            } 
            else {
                autoCompletePopup.show(editor);
                
            }
        });
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
            if(cb_unidad.getSelectionModel().getSelectedItem().split(" ")[0].equals("0"))
                return;
            Integer idUnidad = new Integer(cb_unidad.getSelectionModel().getSelectedItem().split(" ")[0]);
            String nota= textField_notas.getText().toUpperCase().trim();
            String observaciones = textField_observaciones.getText().toLowerCase().trim();
            Object informacion = idUnidad+":"+nota+":"+observaciones;
            abrir_Edicion_Registros.registroEditNuevo(informacion);
            this.btn_cerrar.fire();
  
        
            
        }
        
    }
    

    public void setIAbrirEdicionRegistro(IAbrir_Edicion_Registros abrir_Edicion_Registros){
        this.abrir_Edicion_Registros = abrir_Edicion_Registros;
    }
    //cambiar este desmadre, por un switch , en key realeased, comparando
    //if eventKeyCode == enter->switch(event.getSource) case txtcombobox{notas.request...}, case txtnotas(observaciones.request)
    @FXML
    void focusable_OnKey(KeyEvent event) 
    {
         if(event.getCode() == KeyCode.ENTER)
         {  
           switch(focus)
           {
               case -1:
                   cb_unidad.requestFocus();
                   focus++;
                   break;
               case 0:
                   textField_notas.requestFocus();
                   focus++;
                   break;
                default:
                   //textField_observaciones.requestFocus();
                   //focus=-1;
                    btnAceptar.fire();
                   break;
                
                
           }
        }else{
             if(event.getCode()==KeyCode.ESCAPE){
                 btn_cerrar.fire();
             }
         }
    }
      @FXML
    void DetectFocusable_OnMouse(MouseEvent event) 
    {
        if(cb_unidad.isFocused())
        {
            focus=0;
        }
        else if(textField_notas.isFocused())
        {
            focus=1;
        }
        else if(textField_observaciones.isFocused())
        {
            focus=-1;
        }
    }
    
}
