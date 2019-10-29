/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Models.Clientes;
import Models.Taxis;
import Models.Taxistas;
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
import services.sql.delete.ConexionEliminacionTaxista;
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
    private final ConexionEliminacionTaxista conexionEliminacionTaxista = new ConexionEliminacionTaxista();
    private ObservableList<Taxistas> listaTaxistasDefault = FXCollections.observableArrayList();
    private final ObservableList<Taxistas> listaTaxistasFiltro = FXCollections.observableArrayList();        
    @FXML
    private JFXButton btnAdd_Taxista;
    @FXML
    private JFXButton btnDelete_Taxista;
    @FXML
    private JFXButton btnEdit_Taxista;
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
            
            
        table_taxistas.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                
                //si hay seleccionados enableará el row selected.
                if(!table_taxistas.selectionModelProperty().get().isEmpty()){
                    btnDelete_Taxista.disableProperty().set(false);
                    btnEdit_Taxista.disableProperty().set(false);
                }else{
                    //disableará los botones, al salir de foco.
                    btnDelete_Taxista.disableProperty().set(true);
                    btnEdit_Taxista.disableProperty().set(true);
                }

            }
        });
        
                //definir una fila de fabrica.
        table_taxistas.setRowFactory((param) -> {
           // TableRow<Empleados> row = new TableRow<>();
            JFXTreeTableRow<Taxistas> row = new JFXTreeTableRow<>();
            
            row.setOnMouseClicked(event->{
            
                //si un registro es seleccionado con 1 o 2 clic
                if(! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 2){
                    Taxistas clickedRow = row.getItem();
                    btnDelete_Taxista.disableProperty().set(false);
                    btnEdit_Taxista.disableProperty().set(false);
                    System.out.println(clickedRow.getNombre());  
                    //abrirá la ventana para edición TODO.
                    btnEdit_Taxista.fire();
                    
                }else
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 1) {

                  //  Empleados clickedRow = row.getItem();
                    btnDelete_Taxista.disableProperty().set(false);
                    btnEdit_Taxista.disableProperty().set(false);
                    //System.out.println(clickedRow.getNombre());                                        
                }
                    
                
            });
            
            return row;
        });    
            
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

    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
        
        int telefonoCliente = Integer.parseInt(table_taxistas.getSelectionModel().getSelectedItem().getValue().getId_taxista());
        System.out.println("Delete");
    
        try {
            conexionEliminacionTaxista.deleteTaxista(telefonoCliente, Statics.getConnections() ) ;
            listaTaxistasDefault.remove(table_taxistas.getSelectionModel().getSelectedItem().getValue());
           
        } catch (SQLException ex) {
            services.Servicios.crearVentanaError(
                    this.btnAdd_Taxista.getScene().getWindow(), 
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
