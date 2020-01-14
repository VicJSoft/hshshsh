/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Interfaces.IAbrir_Edicion_Registros;
import Models.Taxis;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import controllers.crud.TaxisCRUDController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
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
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseButton;
import services.Servicios;
import services.sql.delete.ConexionEliminacionUnidad;
import services.sql.read.ConexionLecturaTaxis;
import services.sql.update.ConexionUpdateTaxi;

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
    private final ConexionEliminacionUnidad conexionEliminacionUnidad = new ConexionEliminacionUnidad();
    private final ConexionLecturaTaxis conexionLecturaTaxis = new ConexionLecturaTaxis();
    private final ConexionUpdateTaxi conexionUpdateTaxi = new ConexionUpdateTaxi();
    
    
    private final ObservableList<Taxis> listaTaxisFiltro = FXCollections.observableArrayList();
    @FXML
    private JFXButton btnAdd_Taxi;
    @FXML
    private JFXButton btnDelete_Taxi;
    @FXML
    private JFXButton btnEdit_Taxi;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        column_unidad.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        column_marca.setCellValueFactory(new TreeItemPropertyValueFactory<>("marca"));
        column_modelo.setCellValueFactory(new TreeItemPropertyValueFactory<>("modelo"));
        column_placa.setCellValueFactory(new TreeItemPropertyValueFactory<>("placa"));
        column_taxista.setCellValueFactory(new TreeItemPropertyValueFactory<>("taxista"));


        listaTaxisDefault=conexionLecturaTaxis.getTaxis();

        TreeItem<Taxis> root = new RecursiveTreeItem<>(listaTaxisDefault, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
        table_taxis.setRoot(root);
        table_taxis.setShowRoot(false);
        cargarListaFiltrada(root);//filtra la lista que se carga por defecto y el filtro lo tranfiera al observable secundario 

        table_taxis.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                
                //si hay seleccionados enableará el row selected.
                if(!table_taxis.selectionModelProperty().get().isEmpty()){
                    btnDelete_Taxi.disableProperty().set(false);
                    btnEdit_Taxi.disableProperty().set(false);
                }else{
                    //disableará los botones, al salir de foco.
                    btnDelete_Taxi.disableProperty().set(true);
                    btnEdit_Taxi.disableProperty().set(true);
                }

            }
        });
        
                //definir una fila de fabrica.
        table_taxis.setRowFactory((TreeTableView<Taxis> param) -> {
           // TableRow<Empleados> row = new TableRow<>();
            JFXTreeTableRow<Taxis> row = new JFXTreeTableRow<>();
            
            row.setOnMouseClicked(event->{
            
                //si un registro es seleccionado con 1 o 2 clic
                if(! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 2){
                    Taxis clickedRow = row.getItem();
                    btnDelete_Taxi.disableProperty().set(false);
                    btnEdit_Taxi.disableProperty().set(false);
                    System.out.println(clickedRow.getPlaca());  
                    //abrirá la ventana para edición TODO.

                    btnEdit_Taxi.fire();
                    
                }else
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 1) {

                  //  Empleados clickedRow = row.getItem();
                    btnDelete_Taxi.disableProperty().set(false);
                    btnEdit_Taxi.disableProperty().set(false);
                    //System.out.println(clickedRow.getNombre());                                        
                }
                    
                
            });
            
            return row;
        });   
    
    }    

    @FXML
    private void btnAdd_OnAction(ActionEvent event) throws IOException 
    {
        
        TaxisCRUDController taxisCRUDController =(TaxisCRUDController)Servicios.crearVentana(
               "/views/crud/TaxisCRUD.fxml",
               Servicios.getStageFromEvent(event),getClass());
    
          taxisCRUDController.setIAbrirEdicionRegistro(new IAbrir_Edicion_Registros() {
            @Override
            public void registroEditNuevo(Object taxiModifcado) {
                
                Taxis taxiModified = (Taxis) taxiModifcado;
                listaTaxisDefault.add(taxiModified);
                table_taxis.refresh();
                
            
                
                
            }

            
        },null);
        
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
                    
                    for(Taxis taxiActual : listaTaxisDefault)
                    {
                        if(newValue.contains( taxiActual.getId() +"" ) )
                        {
                            listaTaxisFiltro.add(taxiActual);
                        }
                    }
                    TreeItem<Taxis> root1 = new RecursiveTreeItem<>(listaTaxisFiltro, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
                    table_taxis.setRoot(root1);
                    table_taxis.setShowRoot(false);
                }
            
             });
    }

    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
        
        int id_unidad = table_taxis.getSelectionModel().getSelectedItem().getValue().getId();
        System.out.println("Delete");
    
        try {
            conexionEliminacionUnidad.deleteUnidad(id_unidad) ;
            listaTaxisDefault.remove(table_taxis.getSelectionModel().getSelectedItem().getValue());
           
        } catch (SQLException ex) {
            services.Servicios.crearVentanaError(
                    this.btnAdd_Taxi.getScene().getWindow(), 
                    "Error SQL", 
                    "Error al eliminar registro",
                    "No fue posible eliminar el regitro. \nAsegurese que el servidor de la base de datos, se encuentre activo, si ya lo está, contacte con el desarrollador.\nMensaje error:\n"
                            + ex.getMessage());
            Logger.getLogger(EmpleadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void btnEdit_OnAction(ActionEvent event) throws IOException 
    {
       //listaTaxisDefault.get(1).setPlaca("Placa modifed");
      
        
        TaxisCRUDController taxisCRUDController =(TaxisCRUDController) Servicios.crearVentana(
              "/views/crud/TaxisCRUD.fxml",
              Servicios.getStageFromEvent(event),getClass()
        );
        taxisCRUDController.setIAbrirEdicionRegistro(new IAbrir_Edicion_Registros() {
            @Override
            public void registroEditNuevo(Object taxiModifcado) {
                
                Taxis taxiModified = (Taxis) taxiModifcado;
                
                int idTaxiModificado = taxiModified.getId();
                
                if(conexionUpdateTaxi.update(taxiModified)){
                    
                    for(Taxis taxiActual : listaTaxisDefault){
                   
                        if(taxiActual.getId()== idTaxiModificado){
                            taxiActual.setMarca(taxiModified.getMarca());
                            taxiActual.setModelo(taxiModified.getModelo());
                            taxiActual.setPlaca(taxiModified.getPlaca());
                            taxiActual.setTaxista(taxiActual.getTaxista());
                            table_taxis.refresh();
                            break;
                        }
                    }
                    
                }
                
            }

            
        },table_taxis.getSelectionModel().getSelectedItem().getValue());
  
        
    }

       
    
}
