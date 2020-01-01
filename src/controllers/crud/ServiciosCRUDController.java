/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.crud;

import Interfaces.IValidateCRUD;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.Servicios;
import services.StringLengthValidator;

/**
 * FXML Controller class
 *
 * @author ESPINO
 */
public class ServiciosCRUDController implements Initializable,IValidateCRUD {

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
    private JFXTextField textField_telefono_buscar;
    @FXML
    private JFXTextField textField_telefono;

    @FXML
    private JFXTextField textField_nombre;

    @FXML
    private JFXTextField textField_calle;

    @FXML
    private JFXTextField textField_colonia;

    @FXML
    private JFXTextField textField_num_ext;

    @FXML
    private JFXTextField textField_numInt;

    @FXML
    private JFXTextField textField_notas;

    @FXML
    private JFXTextField textField_unidad;

    @FXML
    private JFXTextField textField_observaciones;
    @FXML
    private JFXRadioButton rb_Regular;
    @FXML
    private ToggleGroup tipoServicio;
    @FXML
    private JFXRadioButton rb_programado;
    @FXML
    private JFXRadioButton rb_personalizado;
    @FXML
    private ToggleGroup toggletipoProgramado;
    @FXML
    private JFXRadioButton rb_diario;
    @FXML
    private JFXCheckBox cb_lunes;
    @FXML
    private JFXCheckBox cb_martes;
    @FXML
    private JFXCheckBox cb_miercoles;
    @FXML
    private JFXCheckBox cb_jueves;
    @FXML
    private JFXCheckBox cb_viernes;
    @FXML
    private JFXCheckBox cb_sabado;
    @FXML
    private JFXCheckBox cb_domingo;
    @FXML
    private JFXTimePicker timePicker_horaServicio;
    @FXML
    private JFXDatePicker datePicker_dia;
    @FXML
    private JFXTextField txt_destino;
    @FXML
    private Button btn_aceptar;
    @FXML
    private Button btn_cancelar;
    
    private ArrayList<IFXValidatableControl> listControlsRequired = new ArrayList();
    private ArrayList<JFXCheckBox> listaCheckBox = new ArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.lbl_tittleBar.setText("Servicios");
        this.lbl_tittle.setText("Servicios");
        
        setClickedEventCheckBox();
        
        this.rb_Regular.setOnAction((ActionEvent event) -> {
            setChecksDays(false);
            datePicker_dia.setValue(LocalDate.now());
            timePicker_horaServicio.setValue(LocalTime.now());
            rb_personalizado.selectedProperty().set(false);
            rb_diario.selectedProperty().set(false);
        });
          
        this.rb_programado.setOnAction((ActionEvent event) -> {
            //rb_personalizado.selectedProperty().set(true);
            //datePicker_dia.setValue(null);
            //timePicker_horaServicio.setValue(null);
            rb_personalizado.fire();
        });
        this.rb_diario.setOnAction((ActionEvent event) -> {
            //toogleCheckBoxDias();
            setChecksDays(true);
            rb_programado.selectedProperty().set(true);
            datePicker_dia.setValue(null);
            timePicker_horaServicio.setValue(null);
        });
        
        this.rb_personalizado.setOnAction((ActionEvent event) -> {
            rb_programado.selectedProperty().set(true);
            datePicker_dia.setValue(null);
            timePicker_horaServicio.setValue(null);
        });
      
       this.listControlsRequired = listControlsRequired();
       this.setFieldValidations();
    }    

    /*
    
    */
    Thread hiloBusqueda ;
  
    @FXML
    private void txtBuscarTelefono_OnKeyPressed(KeyEvent event) {
        hiloBusqueda =  new Thread(new buscadorTelefono());
        hiloBusqueda.start();
    } 
    @FXML
    private void btnCerrar_Click(ActionEvent event) {
        Servicios.cerrarVentana(event);
    }
    @FXML
    private void btnAceptar_OnAction(ActionEvent event) {
        //llamar a los validate
        if(this.validarCampos()){
            //regresar instancia tipo servicios.
        }
    }

    @FXML
    private void btnCancelar_OnAction(ActionEvent event) {
        Servicios.cerrarVentana(event);
    }

    @FXML
    private void tittleBar_Released(MouseEvent event)
    {
    
    }

    @FXML
    private void tittleBarr_Drag(MouseEvent event) {
        Servicios.tittleBar_Drag(event);
    }

    @FXML
    private void tittleBar_Pressed(MouseEvent event) {
        Servicios.tittleBar_Pressed(event);
    }
    

    
    private void setChecksDays(boolean state){
        this.cb_lunes.selectedProperty().set(state);
        this.cb_martes.selectedProperty().set(state);
        this.cb_miercoles.selectedProperty().set(state);
        this.cb_jueves.selectedProperty().set(state);
        this.cb_viernes.selectedProperty().set(state);
        this.cb_sabado.selectedProperty().set(state);
        this.cb_domingo.selectedProperty().set(state);
    }
    
    private void setClickedEventCheckBox(){
        
        
        listaCheckBox.add(this.cb_lunes);
        listaCheckBox.add(this.cb_martes);
        listaCheckBox.add(this.cb_miercoles);
        listaCheckBox.add(this.cb_jueves);
        listaCheckBox.add(this.cb_viernes);
        listaCheckBox.add(this.cb_sabado);
        listaCheckBox.add(this.cb_domingo);
        
        for(JFXCheckBox actualCB : listaCheckBox){
            actualCB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    rb_programado.selectedProperty().set(true);
                    rb_personalizado.selectedProperty().set(true);

                }
            }
            );
        }
        

    }
 


    @Override
    public ArrayList<IFXValidatableControl> listControlsRequired() {

        ArrayList<IFXValidatableControl> lista = new ArrayList<>();

        lista.add(this.textField_telefono);
        lista.add(this.textField_nombre);
        lista.add(this.textField_calle);
        lista.add(this.textField_colonia);
        lista.add(this.textField_num_ext);
        lista.add(this.textField_unidad);
        
        return lista;
    }

    @Override
    public void setFieldValidations() {

        this.setRequiredValidation();
        this.setLengthValidation();
        
        
        //solo numeros al campo changeListener
        this.textField_telefono.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
        });
        
        this.textField_telefono_buscar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
        });
        
        this.textField_unidad.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
        });        

    }

    @Override
    public void setLengthValidation() {

        this.textField_telefono.getValidators().add(new StringLengthValidator("Únicamente 10 dígitos permitidos.", 10));
        this.textField_nombre.getValidators().add(new StringLengthValidator("Únicamente 100 carácteres permitidos.", 100));
        this.textField_unidad.getValidators().add(new StringLengthValidator("Únicamente 11 dígitos permitidos.", 11));

        
    }

    @Override
    public void setRequiredValidation() {
        this.textField_telefono.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.textField_nombre.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.textField_calle.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.textField_colonia.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.textField_num_ext.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.textField_unidad.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));

    }

    @Override
    public boolean validarCampos() {

        boolean datosValidos = true;
        
        for(IFXValidatableControl actual:listControlsRequired){
            actual.validate();
            datosValidos = datosValidos && actual.validate();
        }        
        return datosValidos;

    }

 

    
class buscadorTelefono implements Runnable
{
   
    Connection connection;
    
    public buscadorTelefono()
    {
        connection = Statics.getConnections();
    }
    @Override
    public void run() {
        
         //while(true){
            
            String sql = "select * from clientes where telefono like '"+textField_telefono_buscar.getText()+"%'";
            String  nombre="", telefono="", calle ="",colonia ="",numEx ="",numInt="";
            
            try
            {
                
                PreparedStatement ps= connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                 while(rs.next())
                    {
                        telefono=rs.getString(1);
                        nombre=rs.getString(2);
                        calle=rs.getString(3);
                        colonia=rs.getString(4);
                        numEx=rs.getString(5);
                        numInt=rs.getString(6);
                   }
                 //sino hago esto cambio de variable  tengo que hacer las primeras variables string despues de la query finales y no puedo porque tiene
                 //que estar cambiando en el while del rs.next();
                String nombre1=nombre, telefono1=telefono, calle1 =calle,colonia1 =colonia,numEx1 =numEx,numInt1=numInt;
                Platform.runLater(() -> {
                    textField_telefono.setText(telefono1);
                    textField_nombre.setText(nombre1);
                    textField_calle.setText(calle1);
                    textField_colonia.setText(colonia1);
                    textField_numInt.setText(numInt1);
                    textField_num_ext.setText(numEx1);
                });
                if(textField_telefono_buscar.getText().equals(""))
                {
                    Platform.runLater(() -> {
                    textField_telefono.setText("");
                    textField_nombre.setText("");
                    textField_calle.setText("");
                    textField_colonia.setText("");
                    textField_numInt.setText("");
                    textField_num_ext.setText("");
                });
                }
                  
                    
                  //  break;
            }
            catch(Exception ex){
                System.out.println("entre");
            }
     
     //}
    }
    
}
}
