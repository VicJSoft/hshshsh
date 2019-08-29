package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import persistencia.SharePreferences;

public class LoginController implements Initializable {

    @FXML
    private JFXCheckBox cb_recordar;
    @FXML
    private Button btn_login;
    @FXML
    private Button btn_cerrar;
    @FXML
    private AnchorPane fondoAP;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private JFXTextField txt_usuario;
    @FXML
    private JFXPasswordField txt_contrasena;
    
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
        SharePreferences sharePreferences = new SharePreferences();
        sharePreferences.setRecordar(cb_recordar.isSelected());
        sharePreferences.setUsuario(txt_usuario.getText());
        SharePreferences.setCredenciales(sharePreferences);
    }
    

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
    private void cbRecordar_Click(ActionEvent event)
    {
        
    }

    @FXML
    private void btnLogin_Click(ActionEvent event) 
    {
        if(cb_recordar.isSelected())
            setCredenciales();
        else
            SharePreferences.initConfig();
    }

    @FXML
    private void btnCerrar_Click(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
    
    
}
