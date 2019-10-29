/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Models.Clientes;
import Models.Empleados;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseButton;
import services.Servicios;
import services.sql.delete.ConexionEliminacionCliente;
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
    private final ConexionEliminacionCliente conexionEliminacionCliente = new ConexionEliminacionCliente();

    private ObservableList<Clientes> listaClientesDefault = FXCollections.observableArrayList();
    private final ObservableList<Clientes> listaClientesFiltro = FXCollections.observableArrayList();     
    @FXML
    private JFXButton btnAdd_Cliente;
    @FXML
    private JFXButton btnDelete_Cliente;
    @FXML
    private JFXButton btnEdit_Cliente;
    
    
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
        
        table_clientes.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                
                //si hay seleccionados enableará el row selected.
                if(!table_clientes.selectionModelProperty().get().isEmpty()){
                    btnDelete_Cliente.disableProperty().set(false);
                    btnEdit_Cliente.disableProperty().set(false);
                }else{
                    //disableará los botones, al salir de foco.
                    btnDelete_Cliente.disableProperty().set(true);
                    btnEdit_Cliente.disableProperty().set(true);
                }

            }
        });
        
                //definir una fila de fabrica.
        table_clientes.setRowFactory((param) -> {
           // TableRow<Empleados> row = new TableRow<>();
            JFXTreeTableRow<Clientes> row = new JFXTreeTableRow<>();
            
            row.setOnMouseClicked(event->{
            
                //si un registro es seleccionado con 1 o 2 clic
                if(! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 2){
                    Clientes clickedRow = row.getItem();
                    btnDelete_Cliente.disableProperty().set(false);
                    btnEdit_Cliente.disableProperty().set(false);
                    System.out.println(clickedRow.getNombre());  
                    //abrirá la ventana para edición TODO.
                    btnEdit_Cliente.fire();
                    
                }else
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 1) {

                  //  Empleados clickedRow = row.getItem();
                    btnDelete_Cliente.disableProperty().set(false);
                    btnEdit_Cliente.disableProperty().set(false);
                    //System.out.println(clickedRow.getNombre());                                        
                }
                    
                
            });
            
            return row;
        });
            
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

    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
        
        String telefonoCliente =  table_clientes.getSelectionModel().getSelectedItem().getValue().getTelefono();
        System.out.println("Delete");
    
        try {
            conexionEliminacionCliente.deleteCliente(telefonoCliente, Statics.getConnections() ) ;
            listaClientesDefault.remove(table_clientes.getSelectionModel().getSelectedItem().getValue());
           
        } catch (SQLException ex) {
            services.Servicios.crearVentanaError(
                    this.btnAdd_Cliente.getScene().getWindow(), 
                    "Error SQL", 
                    "Error al eliminar registro",
                    "No fue posible eliminar el regitro. \n Asegure que el servidor de la base de datos, se encuentre activo.\nMensaje error:\n"
                            + ex.getMessage());
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnEdit_OnAction(ActionEvent event) {
        System.out.println("Edit");
    }
    
}
