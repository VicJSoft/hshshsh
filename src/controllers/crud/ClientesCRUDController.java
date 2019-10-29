/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.crud;

import Interfaces.IValidateCRUD;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.Servicios;
import services.StringLengthValidator;
import services.sql.write.ConexionEscrituraClientes;

/**
 * FXML Controller class
 *
 * @author ESPINO
 */
public class ClientesCRUDController implements Initializable, IValidateCRUD{

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
    /*elementos del crud*/
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
     
    private final ConexionEscrituraClientes conexionEscrituraClientes = new ConexionEscrituraClientes();
    
    ArrayList<IFXValidatableControl> listaControles;    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       this.listaControles = listControlsRequired();
       this.setFieldValidations();
    }    
    
    @FXML
    private void btnCerrar_Click(ActionEvent event) {
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
    @FXML
    void btn_Agregar_Click(ActionEvent event) 
    {
       // los datos num int y observaciones son opcionales
       // el telefono debe de tener como maximo 10 caracteres
       if(validarCampos()){
            if(conexionEscrituraClientes.insertClientes(textField_telefono.getText(), textField_nombre.getText().toUpperCase(), textField_calle.getText().toUpperCase(), textField_colonia.getText().toUpperCase(), textField_numExt.getText().toUpperCase(), textField_numInt.getText().toUpperCase(), textField_observ.getText().toUpperCase(),Statics.getConnections()))
            {

            }
            else
            {

            }
       }     
               
    }
    
    @FXML
    void btn_Cancelar_Click(ActionEvent event) 
    {
        Servicios.cerrarVentana(event);
    }

    //metodo para limpiar campos
    private void vaciar()
    {
       
    }

    @Override
    public ArrayList<IFXValidatableControl> listControlsRequired() {

        ArrayList<IFXValidatableControl> lista = new ArrayList<>();
        lista.add(this.textField_telefono);
        lista.add(this.textField_nombre);
        lista.add(this.textField_calle);
        lista.add(this.textField_colonia);
        lista.add(this.textField_numExt);
        lista.add(this.textField_numInt);
        
        //observaciones no necesita ningún tipo de validación
        //por esa razón on se añade a la lista.
        return lista;
        
    }

    @Override
    public void setFieldValidations() {

        this.setLengthValidation();
        this.setRequiredValidation();
        
        //solo numeros al campo
        this.textField_telefono.textProperty().addListener(new ChangeListener<String>() {
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

    @Override
    public void setLengthValidation() {

        this.textField_telefono.getValidators().add(new StringLengthValidator("Únicamente 10 dígitos permitidos.", 10));
        this.textField_nombre.getValidators().add(new StringLengthValidator("Únicamente 70 caracteres permitidos.", 70));
        this.textField_calle.getValidators().add(new StringLengthValidator("Únicamente 50 caracteres permitidos.", 50));
        this.textField_colonia.getValidators().add(new StringLengthValidator("Únicamente 45 caracteres permitidos.", 45));
        this.textField_numExt.getValidators().add(new StringLengthValidator("Únicamente 6 caracteres permitidos.", 6));
        this.textField_numInt.getValidators().add(new StringLengthValidator("Únicamente 5 caracteres permitidos.", 5));

        for(IFXValidatableControl actual :listaControles){
            ((TextField)actual).textProperty().addListener(new ChangeListener<String>() {
               @Override
               public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                   //mandará el validate cada que presionen una tecla
                   actual.validate();
               }
           });
        }
        
    }

    @Override
    public void setRequiredValidation() {

        for(IFXValidatableControl actual:listaControles){
            if(actual == this.textField_numInt){
                continue;
            }
            //listener RequiredValidator.
            actual.getValidators().add(new RequiredFieldValidator(Statics.textoValidaciones.CAMPO_REQUERIDO));
            
         
            
            //listener de foco
            ((Node)actual).focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(!newValue){
                        actual.validate();
                    }
                }
            }); 
        }

    }

    @Override
    public boolean validarCampos() {

        boolean datosValidos = true;
        
        for(IFXValidatableControl actual:listaControles){
            actual.validate();
            datosValidos = datosValidos && actual.validate();
        }        
        return datosValidos;

    }
 

}
