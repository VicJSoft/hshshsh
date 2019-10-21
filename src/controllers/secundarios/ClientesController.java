/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Models.Clientes;
import Models.Empleados;
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
import services.sql.read.ConexionLecturaClientes;


/**
 * FXML Controller class
 *
 * @author vicen
 */
public class ClientesController implements Initializable {

    @FXML
    private JFXTreeTableView<Clientes> table_clientes;

    @FXML
    private TreeTableColumn<Clientes, String> column_nombre;

    @FXML
    private TreeTableColumn<Clientes, String> column_telefono;

    @FXML
    private TreeTableColumn<Clientes, String> column_direccion;

    @FXML
    private TreeTableColumn<Clientes, String> column_observaciones;

    
    @FXML
    private JFXTextField textField_buscar;
    private final ConexionLecturaClientes conexionLecturaClientes= new ConexionLecturaClientes();
    private ObservableList<Clientes> listaClientesDefault = FXCollections.observableArrayList();
    private final ObservableList<Clientes> listaClientesFiltro = FXCollections.observableArrayList();     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            column_nombre.setCellValueFactory(new TreeItemPropertyValueFactory<>("nombre"));
            column_telefono.setCellValueFactory(new TreeItemPropertyValueFactory<>("telefono"));
            column_direccion.setCellValueFactory(new TreeItemPropertyValueFactory<>("direccion"));
            column_observaciones.setCellValueFactory(new TreeItemPropertyValueFactory<>("observaciones"));
           
            
            
            listaClientesDefault=conexionLecturaClientes.getClientes(Statics.getConnections());
           System.out.println(listaClientesDefault.get(0).getObservaciones());
            TreeItem<Clientes> root = new RecursiveTreeItem<>(listaClientesDefault, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
            table_clientes.setRoot(root);
            table_clientes.setShowRoot(false);
            cargarListaFiltrada(root);//filtra la lista que se carga por defecto y el filtro lo tranfiera al observable secundario 
        
    }    
  

    @FXML
    private void btnAdd_OnAction(ActionEvent event) throws IOException {
         Servicios.crearVentana(
               getClass().getResource("/views/crud/ClientesCRUD.fxml"),
               Servicios.getStageFromEvent(event));
    }
     private void cargarListaFiltrada(TreeItem<Clientes> root) {
            textField_buscar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
            {
                table_clientes.setRoot(null);
                if (newValue.equals("")) 
                {
                     table_clientes.setRoot(root); 
                } else 
                {
                    table_clientes.setRoot(null);
                    listaClientesFiltro.clear();
                    
                    for(int i=0;i<listaClientesDefault.size();i++)
                    {
                        if(listaClientesDefault.get(i).getNombre().contains(newValue.toUpperCase()))
                        {
                            listaClientesFiltro.add(listaClientesDefault.get(i));
                        }
                    }
                    TreeItem<Clientes> root1 = new RecursiveTreeItem<>(listaClientesFiltro, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
                    table_clientes.setRoot(root1);
                    table_clientes.setShowRoot(false);
                }
            
             });
    }
    
}
