package controllers;

/*** IMPORTS/****************************************************************************************************************************************/
import Resources.conexionBBDD.ConexionLectura;
import Resources.conexionBBDD.ConexionSQL;
import com.jfoenix.validation.RequiredFieldValidator;
import Resources.persistencia.SharePreferences;
import com.jfoenix.controls.JFXPasswordField;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXML;
import java.net.URL;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
/**************************************************************************************************************************************************/
    
/*** OVERRIDE PUBLIC METHODS/**********************************************************************************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {

        setValidatorsRequired();
        try
        {
             getCredeciales();
        }
        catch(NullPointerException e)
        {   
        }   
    }
/**************************************************************************************************************************************************/
/*** FXML METHODS/*********************************************************************************************************************************/
    @FXML
    private void dragWindow_Click(MouseEvent event) {

        Stage primaryStage = (Stage)( ((Node) (event.getSource() ) ).getScene().getWindow() );
        primaryStage.setX(event.getScreenX()- xOffset);
        primaryStage.setY(event.getScreenY() - yOffset );
    }
    @FXML
    private void pressedWindow_Click(MouseEvent event) {
        xOffset = event.getSceneX();//guarda coord iniciales del clic
        yOffset = event.getSceneY();
    }


    @FXML
    private void btnLogin_Click(ActionEvent event) 
    {
        
        ConexionLectura conexionLectura = new ConexionLectura();
        ConexionSQL conexionSQL = new ConexionSQL();
        if(conexionLectura.obtenerEmpleado(txt_usuario.getText(), txt_contrasena.getText(), conexionSQL.getConexion()))
        {
            //conexion a la siguiente pantalla
            System.out.println("Entre");
            if(cb_recordar.isSelected())
                setCredenciales();
            else
                SharePreferences.initConfig();
        }
        else
        {    
            //mensaje de no conexion
            System.out.println("Error de credenciales");
            
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
