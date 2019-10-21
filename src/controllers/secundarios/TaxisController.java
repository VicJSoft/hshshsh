/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Models.Taxis;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import services.Servicios;
import services.sql.read.ConexionLecturaTaxis;

/**
 * FXML Controller class
 *
 * @author vicen
 */
public class TaxisController implements Initializable {

    @FXML
    private JFXTreeTableView<Taxis> table_taxis;

    @FXML
    private TreeTableColumn<Taxis, Integer> column_unidad;

    @FXML
    private TreeTableColumn<Taxis, String> column_marca;

    @FXML
    private TreeTableColumn<Taxis, String> column_modelo;

    @FXML
    private TreeTableColumn<Taxis, String> column_placa;
    @FXML
    private TreeTableColumn<Taxis, String> column_taxista;
    @FXML
    private JFXTextField textField_buscar;
    private ObservableList<Taxis> listaTaxisDefault = FXCollections.observableArrayList();
    private final ObservableList<Taxis> listaTaxisFiltro = FXCollections.observableArrayList();
    private final ConexionLecturaTaxis conexionLecturaTaxis = new ConexionLecturaTaxis();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
            column_unidad.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
            column_marca.setCellValueFactory(new TreeItemPropertyValueFactory<>("marca"));
            column_modelo.setCellValueFactory(new TreeItemPropertyValueFactory<>("modelo"));
            column_placa.setCellValueFactory(new TreeItemPropertyValueFactory<>("placa"));
            column_taxista.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_taxista"));
            
            
            listaTaxisDefault=conexionLecturaTaxis.getTaxis(Statics.getConnections());
           
            TreeItem<Taxis> root = new RecursiveTreeItem<>(listaTaxisDefault, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
            table_taxis.setRoot(root);
            table_taxis.setShowRoot(false);
            cargarListaFiltrada(root);//filtra la lista que se carga por defecto y el filtro lo tranfiera al observable secundario 
            
           
    
    }    

    @FXML
    private void btnAdd_OnAction(ActionEvent event) throws IOException 
    {
         Servicios.crearVentana(
               getClass().getResource("/views/crud/TaxisCRUD.fxml"),
               Servicios.getStageFromEvent(event));
    
    }
    
    public void cargarListaFiltrada(TreeItem<Taxis>  root)
    {
            textField_buscar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
            {
                table_taxis.setRoot(null);
                if (newValue.equals("")) 
                {
                     table_taxis.setRoot(root); 
                } else 
                {
                    table_taxis.setRoot(null);
                    listaTaxisFiltro.clear();
                    
                    for(int i=0;i<listaTaxisDefault.size();i++)
                    {
                        if(listaTaxisDefault.get(i).getId().contains(newValue))
                        {
                            listaTaxisFiltro.add(listaTaxisDefault.get(i));
                        }
                    }
                    TreeItem<Taxis> root1 = new RecursiveTreeItem<>(listaTaxisFiltro, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
                    table_taxis.setRoot(root1);
                    table_taxis.setShowRoot(false);
                }
            
             });
    }
   
       
    
}
