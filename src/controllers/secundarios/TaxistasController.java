/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Models.Taxis;
import Models.Taxistas;
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
import services.sql.read.ConexionLecturaTaxistas;

/**
 * FXML Controller class
 *
 * @author vic
 */
public class TaxistasController implements Initializable {

    @FXML
    private JFXTreeTableView<Taxistas> table_taxistas;

    @FXML
    private TreeTableColumn<Taxistas, String> column_nombre;

    @FXML
    private TreeTableColumn<Taxistas, String> column_telefono;

    @FXML
    private TreeTableColumn<Taxistas, String> column_direccion;

    @FXML
    private TreeTableColumn<Taxistas, String> column_obsevaciones;

    @FXML
    private TreeTableColumn<Taxistas, String> column_nacimiento;
    @FXML
    private JFXTextField textField_buscar;
    private final ConexionLecturaTaxistas conexionLecturaTaxistas= new ConexionLecturaTaxistas();
    private ObservableList<Taxistas> listaTaxistasDefault = FXCollections.observableArrayList();
    private final ObservableList<Taxistas> listaTaxistasFiltro = FXCollections.observableArrayList();        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
            column_nombre.setCellValueFactory(new TreeItemPropertyValueFactory<>("nombre"));
            column_telefono.setCellValueFactory(new TreeItemPropertyValueFactory<>("telefono"));
            column_direccion.setCellValueFactory(new TreeItemPropertyValueFactory<>("direccion"));
            column_obsevaciones.setCellValueFactory(new TreeItemPropertyValueFactory<>("observaciones"));
            column_nacimiento.setCellValueFactory(new TreeItemPropertyValueFactory<>("fecha_nacimiento"));
            
            
            listaTaxistasDefault=conexionLecturaTaxistas.getTaxistas(Statics.getConnections());
            TreeItem<Taxistas> root = new RecursiveTreeItem<>(listaTaxistasDefault, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
            table_taxistas.setRoot(root);
            table_taxistas.setShowRoot(false);
            cargarListaFiltrada(root);//filtra la lista que se carga por defecto y el filtro lo tranfiera al observable secundario 
    }    

    @FXML
    private void btnAdd_OnAction(ActionEvent event) throws IOException {
         Servicios.crearVentana(
               getClass().getResource("/views/crud/TaxistasCRUD.fxml"),
               Servicios.getStageFromEvent(event));
    }

    private void cargarListaFiltrada(TreeItem<Taxistas> root) {
            textField_buscar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
            {
                table_taxistas.setRoot(null);
                if (newValue.equals("")) 
                {
                     table_taxistas.setRoot(root); 
                } else 
                {
                    table_taxistas.setRoot(null);
                    listaTaxistasFiltro.clear();
                    
                    for(int i=0;i<listaTaxistasDefault.size();i++)
                    {
                        if(listaTaxistasDefault.get(i).getNombre().contains(newValue.toUpperCase()))
                        {
                            listaTaxistasFiltro.add(listaTaxistasDefault.get(i));
                        }
                    }
                    TreeItem<Taxistas> root1 = new RecursiveTreeItem<>(listaTaxistasFiltro, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
                    table_taxistas.setRoot(root1);
                    table_taxistas.setShowRoot(false);
                }
            
             });
    }
    
}
