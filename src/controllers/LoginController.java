package controllers;

/*** IMPORTS/****************************************************************************************************************************************/
import services.sql.read.ConexionLecturaEmpleados;
import services.sql.ConexionSQL;
import com.jfoenix.validation.RequiredFieldValidator;
import Resources.persistencia.SharePreferences;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXPasswordField;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXCheckBox;
import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import services.Servicios;
/**************************************************************************************************************************************************/

/*** CLASS/****************************************************************************************************************************************/


public class LoginController implements Initializable {

/*** VARIABLES OR INSTANCES GLOBALS****************************************************************************************************************/
    @FXML
    private JFXPasswordField txt_contrasena;
    @FXML
    private JFXTextField txt_usuario;
    @FXML
    private JFXCheckBox cb_recordar;
    @FXML
    private AnchorPane fondoAP;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_cerrar;
    ConexionLecturaEmpleados conexionLectura = new ConexionLecturaEmpleados();
    ConexionSQL conexionSQL = new ConexionSQL();
    Connection connection = conexionSQL.getConexion();
/**************************************************************************************************************************************************/
    
/*** OVERRIDE PUBLIC METHODS/**********************************************************************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {

        setValidatorsRequired();
        try
        {
             getCredeciales();
             //si no falla, entonces obtuvo el usuario, por lo tanto enfoca el campo contraseña directamente.
             txt_contrasena.requestFocus();
        }
        catch(NullPointerException e)
        {   
            
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);

        }   
    }
/**************************************************************************************************************************************************/
/*** FXML METHODS/*********************************************************************************************************************************/
    @FXML
    private void window_Drag(MouseEvent event) {

        Servicios.tittleBar_Drag(event);
    }
    @FXML
    private void windows_Pressed(MouseEvent event) {
       Servicios.tittleBar_Pressed(event);
    }


    @FXML
    private void btnLogin_Click(ActionEvent event) throws IOException, SQLException 
    {
        
        
        if(connection!=null)
        {
            
            //captura de excepción, para un mejor manejo si hay error de conexion.
            if(conexionLectura.obtenerEmpleado(txt_usuario.getText(), txt_contrasena.getText(),connection))
            {
                //conexion a la siguiente pantalla
                System.out.println("Entre");
                Statics.setConnections(connection);
                System.out.println(connection);
                        
               Servicios.crearVentana(
               getClass().getResource("/views/Ventana_Principal.fxml"),
               null);
              
                if(cb_recordar.isSelected())
                    setCredenciales();
                else
                    SharePreferences.initConfig();
                 ((Stage)this.btn_login.getScene().getWindow()).close();
            }
            else
            {
                //mensaje de no conexion
                // TODO generar ventana de error con descricion de credenciales no correctas
                Servicios.crearVentanaError(
                        this.btn_login.getScene().getWindow(),                        
                        "Error Credenciales", 
                        "Usuario/Contraseña incorrecto(s)",
                        "Credenciales incorrectas, si sigue teniendo problemas, contacte al administrador"
                                + "del sistema.");
                
                System.out.println("Error de credenciales");
                
            }
        }
        else
        {
        //TODO Generar ventana de error con esta exception.
            //TODO Crear ventana de error.
            Servicios.crearVentanaError(
                    this.btn_login.getScene().getWindow(),
                    "Error SQL", 
                    "Error conexión de base de datos",
                    "\n\nAsegurese de que el servidor de la base de datos este activo."
                            + "\nSi sigue presentando el problema, contacte al desarrollador.");
        }
    }

    @FXML
    private void btnCerrar_Click(ActionEvent event) {
        Servicios.cerrarVentana(event);
    }
    
    
    @FXML
    private void txtUsuario_ReleasedKey(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            txt_contrasena.requestFocus();
        }
    }

    @FXML
    private void txtPassword_ReleasedKey(KeyEvent event) {
         if(event.getCode() == KeyCode.ENTER){
            btn_login.fire();
            txt_usuario.requestFocus();
        }
    }
    /**************/
    
/**************************************************************************************************************************************************/
/***CUZTOMIZED PUBLIC METHODS/*********************************************************************************************************************************/
    private void setValidatorsRequired(){
        txt_usuario.getValidators().add(new RequiredFieldValidator("Este campo no debe estar vacío..."));
        txt_usuario.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                  txt_usuario.validate();
            }
        });
        txt_contrasena.getValidators().add(new RequiredFieldValidator("Este campo no debe estar vacío..."));
        txt_contrasena.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue)
                    txt_contrasena.validate();
            }
        }); 
    }
    private void getCredeciales()
    {
        SharePreferences sharePreferences = SharePreferences.getCredenciales();
        if(sharePreferences.getRecordar())
        {
            cb_recordar.setSelected(true);
            txt_usuario.setText(sharePreferences.getUsuario());
        }
        else
        {
            cb_recordar.setSelected(false);
        }
    }
    private void setCredenciales()
    {
        SharePreferences sharePreferences = new SharePreferences(cb_recordar.isSelected(),txt_usuario.getText());
        SharePreferences.setCredenciales(sharePreferences);
    }


    /**************************************************************************************************************************************************/
}
/******************************************************************************************************************************************************/
