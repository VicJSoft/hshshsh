/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.crud;

import Resources.statics.Statics;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import services.sql.write.ConexionEscrituraTaxistas;

/**
 * FXML Controller class
 *
 * @author ESPINO
 */
public class TaxistasCRUDController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane ap_tittleBar;
    @FXML
    private ImageView iv_iconBar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Label lbl_tittleBar;
    @FXML
    private Label lbl_tittle;

     @FXML
    private JFXTextField textField_telefono;

    @FXML
    private JFXTextField textField_nombre;

    @FXML
    private JFXTextField textField_calle;

    @FXML
    private JFXTextField textField_colonia;

    @FXML
    private JFXTextField textField_numExt;

    @FXML
    private JFXTextField textField_numInt;

    @FXML
    private JFXTextField textField_observaciones;

    @FXML
    private JFXComboBox<String> comboBox_sexo;

    @FXML
    private JFXDatePicker datePicker_nacimiento;
    
    private final ConexionEscrituraTaxistas conexionEscrituraTaxistas = new ConexionEscrituraTaxistas();
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        comboBox_sexo.setItems(Statics.sexo);
    }    
    @FXML
    void btn_Agregar_Click(ActionEvent event)
    {
        // obligatorios nombre, telefono, fecha nacimiento, sexo, calle, colonia
        //telefono maximo 10 digitos  nombre maximo 150 caracteres calle 50 colonia 45 numext 6 numint 5
        if(conexionEscrituraTaxistas.insertTaxistas(textField_nombre.getText().toUpperCase(),  textField_telefono.getText(),datePicker_nacimiento.getValue() ,comboBox_sexo.getSelectionModel().getSelectedItem(),textField_calle.getText().toUpperCase(), textField_colonia.getText().toUpperCase(), textField_numExt.getText().toUpperCase(), textField_numInt.getText().toUpperCase(), textField_observaciones.getText().toUpperCase(),Statics.getConnections()))
       {
                 System.out.println("add");
       }
       else
       {
              System.out.println("err");
       }

    }
    @FXML
    private void btnCerrar_Click(ActionEvent event) 
    {
        Servicios.cerrarVentana(event);
    }
    @FXML
    void btn_Cancelar_Click(ActionEvent event) 
    {
        Servicios.cerrarVentana(event);
    }
    @FXML
    private void tittleBar_Released(MouseEvent event) {
    }

    @FXML
    private void tittleBarr_Drag(MouseEvent event) {
        Servicios.tittleBar_Drag(event);
    }

    @FXML
    private void tittleBar_Pressed(MouseEvent event) {
    
        Servicios.tittleBar_Pressed(event);
    }
    //metodo para limpiar campos
    public void vaciar()
    {
        
    }
}
