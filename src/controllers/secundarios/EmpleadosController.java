/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableRow;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.SwipeEvent;
import javafx.util.Callback;
import services.Servicios;
import services.sql.delete.ConexionEliminacionEmpleado;
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
    private final ConexionEliminacionEmpleado conexionEliminacionEmpleados = new ConexionEliminacionEmpleado();
    private ObservableList<Empleados> listaEmpleadosDefault = FXCollections.observableArrayList();
    private final ObservableList<Empleados> listaEmpleadosFiltro = FXCollections.observableArrayList();     
    
    @FXML
    private JFXButton btnAdd_Empleado;
    @FXML
    private JFXButton btnDelete_Empleado;
    @FXML
    private JFXButton btnEdit_Empleado;
    
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
        
        
        table_empleados.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                    //si hay seleccionados enableará el row selected.
                    if(!table_empleados.selectionModelProperty().get().isEmpty()){
                        btnDelete_Empleado.disableProperty().set(false);
                        btnEdit_Empleado.disableProperty().set(false);
                    }else{
                        //disableará los botones, al salir de foco.
                        btnDelete_Empleado.disableProperty().set(true);
                        btnEdit_Empleado.disableProperty().set(true);
                    }

            }
        });
        
        //definir una fila de fabrica.
        table_empleados.setRowFactory((param) -> {
           // TableRow<Empleados> row = new TableRow<>();
            JFXTreeTableRow<Empleados> row = new JFXTreeTableRow<>();
            
            row.setOnMouseClicked(event->{
            
                //si un registro es seleccionado con 1 o 2 clic
                if(! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 2){
                    Empleados clickedRow = row.getItem();
                    btnDelete_Empleado.disableProperty().set(false);
                    btnEdit_Empleado.disableProperty().set(false);
                    System.out.println(clickedRow.getNombre());  
                    //abrirá la ventana para edición TODO.
                    btnEdit_Empleado.fire();
                    
                }else
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 1) {

                  //  Empleados clickedRow = row.getItem();
                    btnDelete_Empleado.disableProperty().set(false);
                    btnEdit_Empleado.disableProperty().set(false);
                    //System.out.println(clickedRow.getNombre());                                        
                }
                    
                
            });
            
            return row;
        });
       /* 
        table_empleados.setRowFactory(new Callback<TreeTableView<Empleados>, TreeTableRow<Empleados>>() {
            @Override
            public TreeTableRow<Empleados> call(TreeTableView<Empleados> param) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        */

            
    }    

    @FXML
    private void btnAdd_OnAction(ActionEvent event) throws IOException {
        Servicios.crearVentana(
               getClass().getResource("/views/crud/EmpleadosCRUD.fxml"),
               Servicios.getStageFromEvent(event));
    }
    
    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
        int id = Integer.parseInt( table_empleados.getSelectionModel().getSelectedItem().getValue().getId_empleado());
        System.out.println("Delete");
    
        try {
            conexionEliminacionEmpleados.deleteEmpleado(id, Statics.getConnections() ) ;
                listaEmpleadosDefault.remove(table_empleados.getSelectionModel().getSelectedItem().getValue());
           
        } catch (SQLException ex) {
            services.Servicios.crearVentanaError(
                    this.btnAdd_Empleado.getScene().getWindow(), 
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
