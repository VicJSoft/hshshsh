/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.crud;


import Interfaces.Edicion_Registros;
import Interfaces.IValidateCRUD;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.RequiredFieldValidator;
import controllers.secundarios.TaxisController;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.Servicios;
import services.StringLengthValidator;
import services.sql.write.ConexionEscrituraTaxis;
import services.sql.read.ConexionLecturaTaxistas;
import services.sql.read.ConexionLecturaUnidades;

/**
 * FXML Controller class
 *
 * @author ESPINO
 */
public class TaxisCRUDController implements Initializable,IValidateCRUD {

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
    Button btn_Agregar;
    @FXML
    
    private JFXComboBox<String> comboBox_marca;
    @FXML
    private JFXComboBox<String> comboBox_taxista;
    @FXML
    private JFXTextField textField_unidad;

    @FXML
    private JFXTextField textField_placa;

    @FXML
    private JFXTextField textField_modelo;

    ArrayList<IFXValidatableControl> listaControles;  

    private final ConexionEscrituraTaxis conexionEscrituraTaxis = new ConexionEscrituraTaxis();
    private final ConexionLecturaTaxistas conexionLecturaTaxistas = new ConexionLecturaTaxistas();
    private final ConexionLecturaUnidades conexionLecturaUnidades = new ConexionLecturaUnidades();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
            
       
        
      
         this.listaControles = listControlsRequired();
                this.setFieldValidations();
                comboBox_marca.setItems(Statics.marcas);
                comboBox_taxista.setItems(conexionLecturaTaxistas.getTaxistas_id_name(Statics.getConnections()));
       
        
        
    }    

    @FXML
    private void btnCerrar_Click(ActionEvent event) {
        Servicios.cerrarVentana(event);
    }
    @FXML
    void btn_Agregar_Click(ActionEvent event) {
          // evaluar si escribio el la unidad marca  placa modelo 
          // en el modelo debe ser solo numero de 4 valores mayores que 2000 y menores 3000
      if(validarCampos()){
          
            String val[]=comboBox_taxista.getSelectionModel().getSelectedItem().split("  ");

            if(conexionLecturaUnidades.existeUnidad(Integer.parseInt(textField_unidad.getText()), Statics.getConnections()))
           {
                if(conexionEscrituraTaxis.insertTaxis(Integer.parseInt(textField_unidad.getText()), 
                                                      comboBox_marca.getSelectionModel().getSelectedItem(), 
                                                      Integer.parseInt(textField_modelo.getText()),
                                                      textField_placa.getText().toUpperCase(),
                                                      Integer.parseInt(val[0]), 
                                                      Statics.getConnections()))
                {
                          System.out.println("add");
                }
                else
                {
                       System.out.println("err");
                }
           }
           else
           {
               Servicios.crearVentanaError( this.btn_Agregar.getScene().getWindow(),                        
                            "Error de registro", 
                            "Esa unidad está registrada",
                            "Esa unidad ya esta registrada en el sistema.");


           }
      }
 
       
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

    @Override
    public ArrayList<IFXValidatableControl> listControlsRequired() {

        ArrayList<IFXValidatableControl> lista = new ArrayList<>();
        lista.add(this.textField_unidad);
        lista.add(this.comboBox_marca);
        lista.add(this.textField_modelo);
        lista.add(this.textField_placa);
        lista.add(this.comboBox_taxista);
        
        return lista;

    }

    @Override
    public void setFieldValidations() {

        this.setLengthValidation();
        this.setRequiredValidation();
        this.textField_modelo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if(!newValue.equals(""))
                {
                    if(newValue.charAt(newValue.length()-1)<48 ||  newValue.charAt(newValue.length()-1)>57)
                    {
                       textField_modelo.setText(oldValue);
                    }
                }
                else
                {
                    textField_modelo.setText("");
                }

            }
        });
        this.textField_unidad.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if(!newValue.equals(""))
                {
                    if(newValue.charAt(newValue.length()-1)<48 ||  newValue.charAt(newValue.length()-1)>57)
                    {
                       textField_unidad.setText(oldValue);
                    }
                }
                else
                {
                    textField_unidad.setText("");
                }

            }
        });
        
        
    }

    @Override
    public void setLengthValidation() {

        this.textField_modelo.getValidators().add(new StringLengthValidator("Únicamente 4 dígitos.", 4));
        this.textField_placa.getValidators().add(new StringLengthValidator("Únicamente 10 caracteres permitidos.", 10));
        
        this.textField_modelo.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //mandará el validate cada que presionen una tecla
                textField_modelo.validate();
            }
        });
        this.textField_placa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //mandará el validate cada que presionen una tecla
                textField_placa.validate();
            }
        });
 

    }

    @Override
    public void setRequiredValidation() {

        for(IFXValidatableControl actual:listaControles){
            
            actual.getValidators().add(new RequiredFieldValidator(Statics.textoValidaciones.CAMPO_REQUERIDO));
            
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
