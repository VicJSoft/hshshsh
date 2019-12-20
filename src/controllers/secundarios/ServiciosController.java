/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Interfaces.IAbrir_Edicion_Registros;
import Models.Taxis;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import controllers.crud.ServiciosCRUDController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.input.MouseEvent;
import services.Servicios;
import services.sql.read.ConexionLecturaServicios;


/**
 * FXML Controller class
 *
 * @author vicen
 */
public class ServiciosController implements Initializable {

     @FXML
    private JFXTreeTableView<Models.Servicios> table_servicios;

    @FXML
    private TreeTableColumn<Models.Servicios, String> nombre;

    @FXML
    private TreeTableColumn<Models.Servicios, String> telefono;

    @FXML
    private TreeTableColumn<Models.Servicios, String> direccion;

    @FXML
    private TreeTableColumn<Models.Servicios, String> observaciones;

    @FXML
    private TreeTableColumn<Models.Servicios, String> notas;

    @FXML
    private TreeTableColumn<Models.Servicios, String> unidad;

    @FXML
    private JFXTextField  textField_buscar;
    @FXML
    private JFXButton btnAdd_Servicios;

    @FXML
    private JFXButton btnDelete_Servicios;

    @FXML
    private JFXButton btnEdit_Servicios;

    private final ConexionLecturaServicios conexionLecturaServicios = new ConexionLecturaServicios();
    private  ObservableList<Models.Servicios> listaServicios = FXCollections.observableArrayList();
    private  ObservableList<Models.Servicios> listaServiciosFiltro = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       nombre.setCellValueFactory(new TreeItemPropertyValueFactory<>("nombre"));
       telefono.setCellValueFactory(new TreeItemPropertyValueFactory<>("telefono"));
       direccion.setCellValueFactory(new TreeItemPropertyValueFactory<>("direccion"));
       observaciones.setCellValueFactory(new TreeItemPropertyValueFactory<>("observaciones"));
       notas.setCellValueFactory(new TreeItemPropertyValueFactory<>("notas"));
       unidad.setCellValueFactory(new TreeItemPropertyValueFactory<>("unidad"));
       
       listaServicios= conexionLecturaServicios.getServicios();
       TreeItem<Models.Servicios> root = new RecursiveTreeItem<>(listaServicios, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
       table_servicios.setRoot(root);
       table_servicios.setShowRoot(false);
       cargarListaFiltrada(root);//filtra la lista que se carga por defecto y el filtro lo tranfiera al observable secundario
       
       
       table_servicios.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                
                //si hay seleccionados enableará el row selected.
                if(!table_servicios.selectionModelProperty().get().isEmpty()){
                    btnDelete_Servicios.disableProperty().set(false);
                    btnEdit_Servicios.disableProperty().set(false);
                }else{
                    //disableará los botones, al salir de foco.
                    btnDelete_Servicios.disableProperty().set(true);
                    btnEdit_Servicios.disableProperty().set(true);
                }

            }
        });
               //definir una fila de fabrica.
        table_servicios.setRowFactory((TreeTableView<Models.Servicios> param) -> {
           // TableRow<Empleados> row = new TableRow<>();
            JFXTreeTableRow<Models.Servicios> row = new JFXTreeTableRow<>();
            
            row.setOnMouseClicked((MouseEvent event)->{
            
                //si un registro es seleccionado con 1 o 2 clic
                if(! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 2){
                    Models.Servicios clickedRow = row.getItem();
                    btnDelete_Servicios.disableProperty().set(false);
                    btnEdit_Servicios.disableProperty().set(false);
                    System.out.println(clickedRow.getIdEmpleado());  
                    //abrirá la ventana para edición TODO.

                    btnEdit_Servicios.fire();
                    
                }else
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 1) {

                  //  Empleados clickedRow = row.getItem();
                    btnDelete_Servicios.disableProperty().set(false);
                    btnEdit_Servicios.disableProperty().set(false);
                    //System.out.println(clickedRow.getNombre());                                        
                }
                    
                
            });
            
            return row;
        });   

    }   
    public void cargarListaFiltrada(TreeItem<Models.Servicios>  root)
    {
             textField_buscar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
            {
                table_servicios.setRoot(null);
                if (newValue.equals("")) 
                {
                     table_servicios.setRoot(root); 
                } else 
                {
                    table_servicios.setRoot(null);
                    listaServiciosFiltro.clear();
                    
                    for(Models.Servicios servicioActual : listaServicios)
                    {
                        if(newValue.contains( servicioActual.getIdServicio()+"" ) )
                        {
                            listaServiciosFiltro.add(servicioActual);
                        }
                    }
                    TreeItem<Models.Servicios> root1 = new RecursiveTreeItem<>(listaServiciosFiltro, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
                    table_servicios.setRoot(root1);
                    table_servicios.setShowRoot(false);
                }
            
             });
    }

    @FXML
    private void btnAdd_OnAction(ActionEvent event) throws IOException {
      
  
        ServiciosCRUDController serviciosCRUDController = (ServiciosCRUDController) Servicios.crearVentana(
               "/views/crud/ServiciosCRUD.fxml",
               Servicios.getStageFromEvent(event),
               getClass());
        /*serviciosCRUDController.setIAbrirEdicionRegistro(new IAbrir_Edicion_Registros() {
            @Override
            public void registroEditado(Object taxiModifcado) {
                
                Taxis taxiModified = (Taxis) taxiModifcado;
                listaTaxisDefault.add(taxiModified);
                table_taxis.refresh();
                
            
                
                
            }

            
        },null);*/
    }

    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
        
    }

    @FXML
    private void btnEdit_OnAction(ActionEvent event) {
    }
    
}
