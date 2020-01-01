/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Interfaces.IValidateCRUD;
import Resources.persistencia.SharePreferencesDB;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.IntegerValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import services.Servicios;

/**
 * FXML Controller class
 *
 * @author ESPINO
 */
public class Ventana_ConfiguracionController implements Initializable,IValidateCRUD {

    @FXML
    private AnchorPane ap_tittleBar;
    @FXML
    private ImageView iv_iconBar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Label lbl_tittleBar;
    @FXML
    private JFXTextField txt_IP;
    @FXML
    private JFXTextField txt_Puerto;
    @FXML
    private Button btn_Probar;
    @FXML
    private Button btnAceptar;
    @FXML
    private Button btnCancelar;
    @FXML
    private JFXTextField txt_Usuario;
    @FXML
    private JFXPasswordField txt_Contrasena;
    


    private boolean conexionSatisfactoria = false;
    private SharePreferencesDB configuracion;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Recupera información de conexion para la DB.
        this.configuracion = SharePreferencesDB.getConfiguracion();
        this.setRequiredValidation();
        
        this.txt_IP.setText(configuracion.getIp());
        this.txt_Puerto.setText(configuracion.getPuerto());
        this.txt_Usuario.setText(configuracion.getUser());
        this.txt_Contrasena.setText(configuracion.getPass());
    }    


    @FXML
    private void btnCerrar_Click(ActionEvent event) {
        
        Servicios.cerrarVentana(event);
        
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
    private void btn_Cancelar_Click(ActionEvent event) {
        this.btn_cerrar.fire();
    }

    @FXML
    private void btn_Aceptar_Click(ActionEvent event) {
        
        if(this.conexionSatisfactoria){
            
            //guardar configuracion en un json
            SharePreferencesDB.setConfiguracion(this.getVentanaConfiguracion());
            this.btn_cerrar.fire();
        }
        
    }

    @FXML
    private void btn_Probar_Click(ActionEvent event) {
        if(validarCampos()){
            
            String path = "jdbc:mysql://";
            path = path + this.txt_IP.getText() + ":" + this.txt_Puerto.getText() + "/sitio_taxi";
            String user = this.txt_Usuario.getText();
            String pass = this.txt_Contrasena.getText();
 
            Connection connection;
            try {
               Class.forName("com.mysql.jdbc.Driver").newInstance();
               connection = DriverManager.getConnection(path,user,pass);
               this.conexionSatisfactoria = true;
            } 
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) 
            {
                Servicios.crearVentanaError(this.btnAceptar.getScene().getWindow(), 
                        "Error de conexión de Base de Datos",
                        "No fue posible conectar a la base de datos", 
                        ex.getMessage()
                );
                ex.getMessage(); 
                this.conexionSatisfactoria = false;
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            } 

            
        }
    }

    @Override
    public ArrayList<IFXValidatableControl> listControlsRequired() {
        return null;
    }

    @Override
    public void setFieldValidations() {
    }

    @Override
    public void setLengthValidation() {
    }

    @Override
    public void setRequiredValidation() {
        this.txt_IP.getValidators().add(new RequiredFieldValidator("Campo requerido."));
        this.txt_Puerto.getValidators().add(new RequiredFieldValidator("Campo requerido."));
        this.txt_Usuario.getValidators().add(new RequiredFieldValidator("Campo requerido."));
     //   this.txt_Contrasena.getValidators().add(new RequiredFieldValidator("Campo requerido."));
        
        this.txt_Puerto.getValidators().add(new IntegerValidator("Ingrese solo números."));
        
        
    }

    @Override
    public boolean validarCampos() {
        boolean valido = true;
        
        this.txt_IP.validate();
        this.txt_Puerto.validate();
        this.txt_Usuario.validate();
        this.txt_Contrasena.validate();
        
        valido = valido &&  
                this.txt_IP.validate() &&
                this.txt_Puerto.validate() &&
                this.txt_Usuario.validate() &&
                this.txt_Contrasena.validate();
        
        return valido;
        
    }

    private SharePreferencesDB getVentanaConfiguracion() {

        return new SharePreferencesDB(this.txt_IP.getText(), this.txt_Puerto.getText(), 
                this.txt_Usuario.getText(), this.txt_Contrasena.getText()
        );

    }
    
}
