/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.crud;

import Interfaces.IAbrir_Edicion_Registros;
import Interfaces.IValidateCRUD;
import Models.Empleados;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
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
import services.sql.write.ConexionEscrituraEmpleados;

/**
 * FXML Controller class
 *
 * @author ESPINO
 */
public class EmpleadosCRUDController implements Initializable,IValidateCRUD {

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
    
    ArrayList<IFXValidatableControl> listaControles;
    
    private IAbrir_Edicion_Registros iAbrir_Edicion_Registros;
    private boolean isEdicion;
    private int idEmpleadoEdicion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBox_sexo.setItems(Statics.sexo);
        comboBox_tipo_empleado.setItems(Statics.tipo_empledo);
        // para que el campo telefono solo acepte numeros
        this.listaControles = listControlsRequired();
        setFieldValidations();        
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
    //metodo para limpiar campos
    private void vaciar()
    {
        
    }
    
    @FXML
    void btn_Agregar_Click(ActionEvent event) {
        //todos los datos son requeridos menos observaciones 
        //y numint la contraseña max 12 caracteres  si es admon puede  llevar password si  es modulador de afuerzas el password    
        if(validarCampos()){
            
            if(isEdicion && iAbrir_Edicion_Registros!=null){
                
                this.iAbrir_Edicion_Registros.registroEditado(getEmpleadoVentana());
                this.btn_cerrar.fire();
                return;
            }
            
            if(conexionEscrituraEmpleados.insertEmpleados(
                textField_nombre.getText().toUpperCase(),  datePicker_nacimiento.getValue(),
                textField_telefono.getText(),comboBox_sexo.getSelectionModel().getSelectedItem(),
                comboBox_tipo_empleado.getSelectionModel().getSelectedItem(),textField_calle.getText().toUpperCase(),
                textField_colonia.getText().toUpperCase(), textField_numExt.getText().toUpperCase(),
                textField_numInt.getText().toUpperCase(), textField_observ.getText().toUpperCase(),textField_password.getText()
                ))
            {
                System.out.println("add");
                this.iAbrir_Edicion_Registros.registroEditado(getEmpleadoVentana());
                this.btn_cerrar.fire();
            }
            else
            {
                System.out.println("err");
            }
        }
            
    }
    
    @FXML
    void btn_Cancelar_Click(ActionEvent event) 
    {
        Servicios.cerrarVentana(event);
    }

    /**
     * @{inheritdoc}
    */
    @Override
    public ArrayList<IFXValidatableControl> listControlsRequired(){
    
        ArrayList<IFXValidatableControl> lista = new ArrayList<>();
        lista.add(this.textField_nombre);
        lista.add(this.textField_telefono);
        lista.add(this.textField_password);
        lista.add(this.textField_calle);
        lista.add(this.textField_colonia);
        lista.add(this.textField_numExt);
        lista.add(this.textField_numInt);
        lista.add(this.datePicker_nacimiento);
        lista.add(this.comboBox_sexo);
        lista.add(this.comboBox_tipo_empleado);
        
        //Observaciones no es requerido, ni tiene limitante de caracteres
        return lista;
        
    } 
    /**
     * @{inheritdoc}
     */
    @Override
    public void setFieldValidations() {

        this.setRequiredValidation();
        this.setLengthValidation();
        
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
 

    /**
     * @{inheritdoc}
     */
    @Override
    public void setRequiredValidation(){
        //Esta validación se puede meter en el FocusProperty, para que si cambia de foco, detecte,
        //y no espere a validar en el boton aceptar.        
        
        //debe validar requerido todos, excepto numInt, numExt y Observaciones.
        for(IFXValidatableControl actual : listaControles){
            
            if(actual == this.textField_numInt || actual == this.textField_observ){
                continue;
            }
            //añade validador de campo requerido
            actual.getValidators().add(new RequiredFieldValidator(Statics.textoValidaciones.CAMPO_REQUERIDO));            
            
            //Añade listener de foco al control, para hacer la validacion en cambio de foco.Vic Espino
            //Nodo es más general que IFXValidatableControl.
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
    /**
     * @{inheritdoc}
     */
    @Override
    public void setLengthValidation(){
                                       
        this.textField_nombre.getValidators().add(new StringLengthValidator("Únicamente 150 caracteres permitidos.", 150));
        this.textField_telefono.getValidators().add(new StringLengthValidator("Únicamente 10 dígitos permitidos.", 10));
        
        this.textField_password.getValidators().add(new StringLengthValidator("Únicamente 12 caracteres permitidos.", 12));
        //solo necesita el RequiredValidation para que no quede vacio, su edición está desactivada.
        //datePicker_nacimiento.getValidators().add( );   
        //combobox no requiere length, ya que no es editable, 
        //comboBox_sexo.getValidators().add();
        //comboBox_tipo_empleado.getValidators().add();
        this.textField_calle.getValidators().add(new StringLengthValidator("Únicamente 50 caracteres permitidos.", 50));
        this.textField_colonia.getValidators().add(new StringLengthValidator("Únicamente 45 caracteres permitidos.", 45));
        
        this.textField_numExt.getValidators().add(new StringLengthValidator("Únicamente 6 dígitos permitidos.", 6));
        this.textField_numInt.getValidators().add(new StringLengthValidator("Únicamente 6 dígitos permitidos.", 6));
        
        //JFXPassword y JFXTextField, implementan IFXValidatableControl //IFXLabelFloatControl,
        //udiendo ser tratados de forma general, para llamar a su interno .validate(); en común
        for(IFXValidatableControl actual:listaControles){
            
            if(actual == this.datePicker_nacimiento  || actual == this.comboBox_sexo || actual == this.comboBox_tipo_empleado ){
                continue;
            }
            //JFXPassword y JFXTextField decienden de textfield.
            //Para añadir listener al cambio de texto.
            ((TextField)actual).textProperty().addListener(new ChangeListener<String>() {
               @Override
               public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                   //mandará el validate cada que presionen una tecla
                   actual.validate();
               }
           });
        
        }
       
        
        
    }


    /**
     * @{inheritdoc}
     */
    @Override
    public boolean validarCampos() {
        
        boolean datosValidos = true;
        
        for(IFXValidatableControl actual:listaControles){
            actual.validate();
            datosValidos = datosValidos && actual.validate();
        }        
        return datosValidos;

    }

    public void setiAbrir_Edicion_Registros(IAbrir_Edicion_Registros iAbrir_Edicion_Registros,Empleados empleadoAEditar) {
        this.iAbrir_Edicion_Registros = iAbrir_Edicion_Registros;
        if(empleadoAEditar!=null)
        {
            this.isEdicion = true;
            this.setEmpleadoVentana(empleadoAEditar);
            this.idEmpleadoEdicion = empleadoAEditar.getId_empleado();
        }
        else{
            this.isEdicion = false;
            
            
        }
        
    }

    private void setEmpleadoVentana(Empleados empleadoVentana) {

        this.idEmpleadoEdicion = empleadoVentana.getId_empleado();
        this.textField_nombre.setText(empleadoVentana.getNombre());
        this.datePicker_nacimiento.setValue(
                empleadoVentana.getFecha_nacimiento().toLocalDate()
        );
        this.textField_telefono.setText(empleadoVentana.getTelefono());
        this.comboBox_sexo.valueProperty().set(empleadoVentana.getSexo());
        
        if(empleadoVentana.getTipo_empleado().equalsIgnoreCase(Statics.tipo_empledo.get(0)))//es un administrador
        {
            this.comboBox_tipo_empleado.getSelectionModel().select(0);
        }
        else//es un modulador
        {
            this.comboBox_tipo_empleado.getSelectionModel().select(1);
        }
        this.textField_calle.setText(empleadoVentana.getCalle());
        this.textField_colonia.setText(empleadoVentana.getColonia());               
        this.textField_numInt.setText(empleadoVentana.getNum_int());
        this.textField_numExt.setText(empleadoVentana.getNum_ext());
        this.textField_observ.setText(empleadoVentana.getObservaciones());
        this.textField_password.setText(empleadoVentana.getPassword());
    }
    
    private Empleados getEmpleadoVentana(){
        
        Empleados empleadoVentana = new Empleados(
        
                this.idEmpleadoEdicion,
                this.textField_nombre.getText().toUpperCase().trim(),
                this.datePicker_nacimiento.getValue(),
                this.textField_telefono.getText().trim(),
                this.comboBox_sexo.getValue(),
                this.comboBox_tipo_empleado.getValue().toUpperCase(),
                this.textField_calle.getText().toUpperCase().trim(),
                this.textField_colonia.getText().toUpperCase().trim(),
                this.textField_numExt.getText().trim(),
                this.textField_numInt.getText().trim(),
                this.textField_observ.getText().toUpperCase().trim(),
                this.textField_password.getText()     
        
        );
        
        return empleadoVentana;
    }
    
}
