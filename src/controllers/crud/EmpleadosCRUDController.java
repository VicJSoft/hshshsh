/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.crud;

import Resources.statics.Statics;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.Servicios;
import services.sql.write.ConexionEscrituraEmpleados;

/**
 * FXML Controller class
 *
 * @author ESPINO
 */
public class EmpleadosCRUDController implements Initializable {

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
    

    /*Elementos del crud*/
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
    private JFXTextField textField_observ;

   @FXML
    private JFXComboBox<String> comboBox_sexo;

    @FXML
    private JFXComboBox<String> comboBox_tipo_empleado;

    @FXML
    private JFXDatePicker datePicker_nacimiento;

    @FXML
    private JFXPasswordField textField_password;

    private final ConexionEscrituraEmpleados conexionEscrituraEmpleados = new ConexionEscrituraEmpleados();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBox_sexo.setItems(Statics.sexo);
        comboBox_tipo_empleado.setItems(Statics.tipo_empledo);
        // para que el campo telefono solo acepte numeros
        textField_telefono.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                
                if(!newValue.equals(""))
                {
                    if(newValue.charAt(newValue.length()-1)<48 ||  newValue.charAt(newValue.length()-1)>57)
                    {
                       textField_telefono.setText(oldValue);
                    }
                }
                else
                {
                    textField_telefono.setText("");
                }
            }
        });
    }    
    @FXML
    void btn_Agregar_Click(ActionEvent event) {
            //todos los datos son requeridos menos observaciones y numint la contraseña max 12 caracteres  si es admon puede  llevar password si  es modulador de afuerzas el password
      
       if(conexionEscrituraEmpleados.insertEmpleados(textField_nombre.getText().toUpperCase(),  datePicker_nacimiento.getValue(), textField_telefono.getText(),comboBox_sexo.getSelectionModel().getSelectedItem(),comboBox_tipo_empleado.getSelectionModel().getSelectedItem(),textField_calle.getText().toUpperCase(), textField_colonia.getText().toUpperCase(), textField_numExt.getText().toUpperCase(), textField_numInt.getText().toUpperCase(), textField_observ.getText().toUpperCase(),textField_password.getText(),Statics.getConnections()))
       {
                 System.out.println("add");
       }
       else
       {
              System.out.println("err");
       }
    }

    @FXML
    private void btnCerrar_Click(ActionEvent event) {
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
    private void vaciar()
    {
        
    }
}
