/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.crud;

import Resources.statics.Statics;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.Servicios;

/**
 * FXML Controller class
 *
 * @author ESPINO
 */
public class ServiciosCRUDController implements Initializable {

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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.lbl_tittleBar.setText("Servicios");
        this.lbl_tittle.setText("Servicios");
        
    }    

    /*
    
    */
    Thread Hilo ;
    @FXML
    void buscarTelefono_OnKey(KeyEvent event) 
    {
        Hilo =  new Thread(new buscadorTelefono());
        Hilo.start();
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
    
class buscadorTelefono implements Runnable
{
   
    Connection connection;
    
    public buscadorTelefono()
    {
        connection = Statics.getConnections();
    }
    @Override
    public void run() {
        
         while(true){
            
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
                  
                    
                    break;
            }
            catch(Exception ex){
                System.out.println("entre");
            }
     
     }
    }
    
}
}
