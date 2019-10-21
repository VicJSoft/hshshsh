/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

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
import services.sql.read.ConexionLecturaEmpleados;

/**
 * FXML Controller class
 *
 * @author vicen
 */
public class EmpleadosController implements Initializable {

    @FXML
    private JFXTreeTableView<Empleados> table_empleados;

    @FXML
    private TreeTableColumn<Empleados, String> column_nombre;

    @FXML
    private TreeTableColumn<Empleados, String> column_telefono;

    @FXML
    private TreeTableColumn<Empleados, String> column_direccion;

    @FXML
    private TreeTableColumn<Empleados, String> column_obsevaciones;

    @FXML
    private TreeTableColumn<Empleados, String> column_nacimiento;
    @FXML
    private JFXTextField textField_buscar;
    private final ConexionLecturaEmpleados conexionLecturaEmpleados= new ConexionLecturaEmpleados();
    private ObservableList<Empleados> listaEmpleadosDefault = FXCollections.observableArrayList();
    private final ObservableList<Empleados> listaEmpleadosFiltro = FXCollections.observableArrayList();     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            column_nombre.setCellValueFactory(new TreeItemPropertyValueFactory<>("nombre"));
            column_telefono.setCellValueFactory(new TreeItemPropertyValueFactory<>("telefono"));
            column_direccion.setCellValueFactory(new TreeItemPropertyValueFactory<>("direccion"));
            column_obsevaciones.setCellValueFactory(new TreeItemPropertyValueFactory<>("observaciones"));
            column_nacimiento.setCellValueFactory(new TreeItemPropertyValueFactory<>("fecha_nacimiento"));
            
            
            listaEmpleadosDefault=conexionLecturaEmpleados.getEmpleados(Statics.getConnections());
        
            TreeItem<Empleados> root = new RecursiveTreeItem<>(listaEmpleadosDefault, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
            table_empleados.setRoot(root);
            table_empleados.setShowRoot(false);
            cargarListaFiltrada(root);//filtra la lista que se carga por defecto y el filtro lo tranfiera al observable secundario 
        
    }    

    @FXML
    private void btnAdd_OnAction(ActionEvent event) throws IOException {
        Servicios.crearVentana(
               getClass().getResource("/views/crud/EmpleadosCRUD.fxml"),
               Servicios.getStageFromEvent(event));
    }
    private void cargarListaFiltrada(TreeItem<Empleados> root) {
            textField_buscar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
            {
                table_empleados.setRoot(null);
                if (newValue.equals("")) 
                {
                     table_empleados.setRoot(root); 
                } else 
                {
                    table_empleados.setRoot(null);
                    listaEmpleadosFiltro.clear();
                    
                    for(int i=0;i<listaEmpleadosDefault.size();i++)
                    {
                        if(listaEmpleadosDefault.get(i).getNombre().contains(newValue.toUpperCase()))
                        {
                            listaEmpleadosFiltro.add(listaEmpleadosDefault.get(i));
                        }
                    }
                    TreeItem<Empleados> root1 = new RecursiveTreeItem<>(listaEmpleadosFiltro, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
                    table_empleados.setRoot(root1);
                    table_empleados.setShowRoot(false);
                }
            
             });
    }

    
}
