/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.crud;

import Interfaces.IAbrir_Edicion_Registros;
import Interfaces.IValidateCRUD;
import Models.Clientes;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
     
    
    ArrayList<IFXValidatableControl> listaControles;   

    private IAbrir_Edicion_Registros iAbrir_Edicion_Registros;
    private boolean isEdicion;
    private int focus=-1;//indica a quien le hara el focus

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       this.listaControles = listControlsRequired();
       this.setFieldValidations();
       focus++;
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
           
           if(iAbrir_Edicion_Registros!=null){
               iAbrir_Edicion_Registros.registroEditNuevo(getClienteVentana());
               this.btn_cerrar.fire();
                System.out.println("edit");
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
     @FXML
    void focusable_OnKey(KeyEvent event) 
    {
         if(event.getCode() == KeyCode.ENTER)
         {  
           switch(focus)
           {
               case -1:
                   textField_telefono.requestFocus();
                   focus++;
                   break;
               case 0:
                   textField_nombre.requestFocus();
                   focus++;
                   break;
                case 1:
                   textField_calle.requestFocus();
                   focus++;
                   break;
                case 2:
                   textField_colonia.requestFocus();
                   focus++;
                   break;
                case 3:
                   textField_numExt.requestFocus();
                   focus++;
                   break;
               case 4:
                   textField_numInt.requestFocus();
                   focus++;
                   break;
                default:
                   textField_observ.requestFocus();
                   focus=-1;
                   break;   
           }
        }
    }
      @FXML
    void DetectFocusable_OnMouse(MouseEvent event) 
    {
        if(textField_telefono.isFocused())
        {
            focus=0;
            System.out.println(focus);
        }
        else if(textField_nombre.isFocused())
        {
            focus=1;
            System.out.println(focus);
        }
        else if(textField_calle.isFocused())
        {
            focus=2;
            System.out.println(focus);
        }
        else if(textField_colonia.isFocused())
        {
            focus=3;
            System.out.println(focus);
        }
        else if(textField_numExt.isFocused())
        {
            focus=4;
            System.out.println(focus);
        }
        else if(textField_numInt.isFocused())
        {
            focus=5;
            System.out.println(focus);
        }
        else if(textField_observ.isFocused())
        {
            focus=-1;
            System.out.println(focus);
        }

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

    /**
     * Invoca a los validadores de cada control de esta ventana.
     * 
     * @return 
     * True: 
     * Sí todos los campos son correctos.
     * 
     * False: 
     * Cuando todos o algún campo es incorrecto/invalido.
     * 
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

    /**
     * Setea el evento para hacer el nuevo registro o edición:/n
     * 
     * 
     * 
     * @param iAbrir_Edicion_Registros
     * Objeto interno anonimo, que se llamará como resultado del evento, cuando se clickee en aceptar. Para devolver
     * el registro nuevo o editado.
     * 
     * 
     * @param clienteAEditar 
     * En caso de que se requiera la edición de un registro, será este el registro que se editará y devolverá por medio
     * de la @param iAbrir_Edicion_Registros .
     * 
     * 
     */
    public void setiAbrir_Edicion_Registros(IAbrir_Edicion_Registros iAbrir_Edicion_Registros,Clientes clienteAEditar) {
        this.iAbrir_Edicion_Registros = iAbrir_Edicion_Registros;
        if(clienteAEditar!=null)
        {
            this.isEdicion = true;
            this.textField_telefono.editableProperty().set(false);
            setClienteVentana(clienteAEditar);
        }
        else{
            this.isEdicion = false; 
            this.textField_telefono.editableProperty().set(true);
        }
    }
       
    /**
     * Mapea la ventana clientes, para generar un objeto, con los datos disponibles en la ventana.
     * Este método debe llamarse despues de validar los campos, para no tenereun campo nulo.
     * @return 
     * Retorna objeto cliente, del resultado de la extracción de los datos de la ventana Clientes.
     */
    private Clientes getClienteVentana(){
        Clientes clienteVentana = new Clientes(
                this.textField_telefono.getText().trim(), 
                this.textField_nombre.getText().toUpperCase().trim(), 
                this.textField_calle.getText().toUpperCase().trim(), 
                this.textField_colonia.getText().toUpperCase().trim(),
                this.textField_numExt.getText().trim(), 
                this.textField_numInt.getText().trim(), 
                this.textField_observ.getText().toUpperCase().trim()
        );
        return clienteVentana;
    }
    /**
     * Mapea la información de un objeto cliente en la ventana.
     * @param clienteAEditar 
     * Objeto a mapear.
     */
    private void setClienteVentana(Clientes clienteAEditar){
        
        this.textField_nombre.setText(clienteAEditar.getNombre());
        this.textField_telefono.setText(clienteAEditar.getTelefono());
        this.textField_calle.setText(clienteAEditar.getCalle());
        this.textField_colonia.setText(clienteAEditar.getColonia());
        this.textField_numExt.setText(clienteAEditar.getNumeroExt());
        this.textField_numInt.setText(clienteAEditar.getNumeroInt());
        this.textField_observ.setText(clienteAEditar.getObservaciones());
        
    }
    
 

}
