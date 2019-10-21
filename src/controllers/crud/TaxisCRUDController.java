/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.crud;


import Resources.statics.Statics;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.Servicios;
import services.sql.write.ConexionEscrituraTaxis;
import services.sql.read.ConexionLecturaTaxistas;
import services.sql.read.ConexionLecturaUnidades;

/**
 * FXML Controller class
 *
 * @author ESPINO
 */
public class TaxisCRUDController implements Initializable {

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

    

    private final ConexionEscrituraTaxis conexionEscrituraTaxis = new ConexionEscrituraTaxis();
    private final ConexionLecturaTaxistas conexionLecturaTaxistas = new ConexionLecturaTaxistas();
    private final ConexionLecturaUnidades conexionLecturaUnidades = new ConexionLecturaUnidades();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
                        "Esa unidad ya esta registrada en el sistema");
       
           
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
   
     
}
