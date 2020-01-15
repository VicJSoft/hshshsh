/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Interfaces.IAbrir_Edicion_Registros;
import Models.Servicio;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableRow;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import controllers.Ventana_AsignarUnidadController;
import controllers.crud.ServiciosCRUDController;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.Servicios;
import services.sql.read.ConexionLecturaServicios;
import services.sql.write.ConexionEscrituraServicios;


/**
 * FXML Controller class
 *
 * @author vicen
 */
public class ServiciosController implements Initializable {

    @FXML
    private JFXTreeTableView<Models.Servicio> table_servicios;

    @FXML
    private TreeTableColumn<Models.Servicio, LocalDateTime> fecha;

    @FXML
    private TreeTableColumn<Models.Servicio, String> nombre;

    @FXML
    private TreeTableColumn<Models.Servicio, String> telefono;

    @FXML
    private TreeTableColumn<Models.Servicio, String> direccion;

    @FXML
    private TreeTableColumn<Models.Servicio, String> observaciones;

    @FXML
    private TreeTableColumn<Models.Servicio, String> notas;

    @FXML
    private TreeTableColumn<Models.Servicio, String> unidad;
    
    @FXML
    private TreeTableColumn<Models.Servicio, String> destino;
    
    @FXML
    private TreeTableColumn<Models.Servicio, String> diasSeleccion;
    
    @FXML
    private TreeTableColumn<Models.Servicio, String> estado;

    @FXML
    private JFXTextField  textField_buscar;
    @FXML
    private JFXButton btnAdd_Servicios;

    @FXML
    private JFXButton btnDelete_Servicios;

    @FXML
    private JFXButton btnEdit_Servicios;
    

    private final ConexionLecturaServicios conexionLecturaServicios = new ConexionLecturaServicios();
    private final ConexionEscrituraServicios conexionEscrituraServicios = new ConexionEscrituraServicios();
    
    
    private  ObservableList<Models.Servicio> listaServicios = FXCollections.observableArrayList();
    private  ObservableList<Models.Servicio> listaServiciosFiltro = FXCollections.observableArrayList();
    private  ObservableList<Models.Servicio> listaServiciosPendientes = FXCollections.observableArrayList();
    TreeItem<Models.Servicio> root;
    @FXML
    private JFXToggleButton togglebtn_ServiciosPendientes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       this.btnDelete_Servicios.setTooltip(new Tooltip("Cancelar servicio."));
       this.btnEdit_Servicios.setTooltip(new Tooltip("Servicio aplicado."));
       this.btnAdd_Servicios.setTooltip(new Tooltip("Nuevo servicio."));
        
       estado.setCellValueFactory(new TreeItemPropertyValueFactory<>("cb_estado"));
       fecha.setCellValueFactory(new TreeItemPropertyValueFactory<>("dateTime"));
       nombre.setCellValueFactory(new TreeItemPropertyValueFactory<>("nombre"));
       telefono.setCellValueFactory(new TreeItemPropertyValueFactory<>("telefono"));
       direccion.setCellValueFactory(new TreeItemPropertyValueFactory<>("direccion"));
       observaciones.setCellValueFactory(new TreeItemPropertyValueFactory<>("observaciones"));
       notas.setCellValueFactory(new TreeItemPropertyValueFactory<>("notas"));
       unidad.setCellValueFactory(new TreeItemPropertyValueFactory<>("idUnidad"));
       destino.setCellValueFactory(new TreeItemPropertyValueFactory<>("destino"));
       diasSeleccion.setCellValueFactory(new TreeItemPropertyValueFactory<>("diasSeleccion"));
 
     /*  fecha.setComparator(new Comparator<LocalDateTime>() {
           @Override
           public int compare(LocalDateTime o1, LocalDateTime o2) {

               if(o1.isBefore(o2))
                   return -1;
               if(o1.isEqual(o2))
                   return 0;
               else
                   return 1;//mayor
           }
       });*/
 /*        Callback<Servicio,Observable[]> cb = new Callback<Servicio, Observable[]>() {
           @Override
           public Observable[] call(Servicio param) {

               return new Observable[]{
                   param.getDateTime(),//tiene que ser un property, se tendria que agregar al model
               };
               
           }
       };
   */      
        /**/
       listaServicios= conexionLecturaServicios.getServicios();
        root = new RecursiveTreeItem<>(listaServicios, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
       table_servicios.setRoot(root);
       table_servicios.setShowRoot(false);
//       table_servicios.setSortMode(TreeSortMode.ONLY_FIRST_LEVEL);
       cargarListaFiltrada();//filtra la lista que se carga por defecto y el filtro lo tranfiera al observable secundario
       
       
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
        table_servicios.setRowFactory((TreeTableView<Models.Servicio> param) -> {
           // TableRow<Empleados> row = new TableRow<>();
            JFXTreeTableRow<Models.Servicio> row = new JFXTreeTableRow<>();
            
            row.setOnMouseClicked((MouseEvent event)->{
            
                //si un registro es seleccionado con 1 o 2 clic
                if(! row.isEmpty() && event.getButton()==MouseButton.PRIMARY && event.getClickCount() == 2){
                    Models.Servicio clickedRow = row.getItem();
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
     //   setCancelledGraphic();
        table_servicios.scrollTo( table_servicios.getCurrentItemsCount()-1);
    }   
    /**
     * Marca los registros que su campo son "servicioActivo = false", 
     * Si el servicio es regular, se marca un punto verde,
     * Si el servicio es programado, se marca un punto rojo (cancelado).
     * @deprecated 
     * Afectaba mucho el rendimiento.
     */
    private void setCancelledGraphic(){
        int i = 0;
        for(TreeItem<Servicio> treeItem : table_servicios.getRoot().getChildren())
            if(!treeItem.getValue().isServicioActivo()){
                treeItem.setGraphic(new Circle(5, Paint.valueOf(treeItem.getValue().isProgramadow()?ROJO:VERDE)));
             //   treeItem.getGraphic().setLayoutY(10);
            }
        
        //table_servicios.getSelectionModel().getModelItem(0);
       // table_servicios.getRoot().getChildren();
    }
    public void cargarListaFiltrada( )
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

               for(Models.Servicio servicioActual : listaServicios)
               {
                   if(servicioActual.getTelefono().contains(newValue))
                   {
                       listaServiciosFiltro.add(servicioActual);
                   }
               }
               TreeItem<Models.Servicio> root1 = new RecursiveTreeItem<>(listaServiciosFiltro, (recursiveTreeObject) -> recursiveTreeObject.getChildren());
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
        serviciosCRUDController.setIAbrirEdicionRegistros(new IAbrir_Edicion_Registros() {
            @Override
            public void registroEditNuevo(Object registro) {
                
                Servicio servicio = (Servicio) registro;
                
                
                if(conexionEscrituraServicios.insertServicio(servicio)){
                    listaServicios.add(0,servicio);
                    table_servicios.scrollTo(table_servicios.getCurrentItemsCount()==0?0:table_servicios.getCurrentItemsCount()-1);
                  // table_servicios.sort();
                   /* listaServicios.sort(new Comparator<Servicio>() {
                        @Override
                        public int compare(Servicio o1, Servicio o2) {
                            
                        }
                    });*/
                      //fecha.sortTypeProperty().set(TreeTableColumn.SortType.ASCENDING);
                   // listaServicios.sort(c);
                }

            }
        });
    }

    private final String ROJO = "CB3234";
    private final String VERDE = "5AB444";
    @FXML
    private void btnDelete_OnAction(ActionEvent event) {
        //rojo
        cancelarServicio(ROJO);
    }

    @FXML
    private void btnEdit_OnAction(ActionEvent event) {
        //verde
        Servicio servicioSeleccionado = table_servicios.getSelectionModel().getSelectedItem().getValue();
        //Cuando está pendiente y sin unidad
        if(servicioSeleccionado.getIdUnidad()==null&&!servicioSeleccionado.isProgramadow()){
            
            Ventana_AsignarUnidadController ventanaAsignarUnidad = 
                    (Ventana_AsignarUnidadController) Servicios.crearVentana("/views/Ventana_AsignarUnidad.fxml", (Stage) this.btnAdd_Servicios.getScene().getWindow(), getClass());

            ventanaAsignarUnidad.setIAbrirEdicionRegistro(new IAbrir_Edicion_Registros() {
                @Override
                public void registroEditNuevo(Object registro) {
                    
                    Integer idUnidad = (Integer) registro; 
                    servicioSeleccionado.setIdUnidad(idUnidad);
                    if(conexionEscrituraServicios.asignarUnidad(servicioSeleccionado)){
                        
                       
                        cancelarServicio(VERDE);
                        table_servicios.refresh();
                    }


                }
            });
        }else
        //cuando solo está pendiente, para ser marcado como enviado
        if(!servicioSeleccionado.isProgramadow()){
            cancelarServicio(VERDE);
            
        }else if(servicioSeleccionado.isProgramadow()&&servicioSeleccionado.getIdUnidad()==null){//solo aplica para cuando no se le asignó unidad al servicio programado
            Ventana_AsignarUnidadController ventanaAsignarUnidad = 
                    (Ventana_AsignarUnidadController) Servicios.crearVentana("/views/Ventana_AsignarUnidad.fxml", (Stage) this.btnAdd_Servicios.getScene().getWindow(), getClass());
                    ventanaAsignarUnidad.setIAbrirEdicionRegistro(new IAbrir_Edicion_Registros() {
                                    @Override
                                    public void registroEditNuevo(Object registro) {

                                        Integer idUnidad = (Integer) registro; 
                                        servicioSeleccionado.setIdUnidad(idUnidad);
                                        if(conexionEscrituraServicios.asignarUnidad(servicioSeleccionado)){
                                            table_servicios.refresh();
                                        }


                                    }
                                });
        }
     }

    private void cancelarServicio(String color){
        Servicio servicioSeleccionado = table_servicios.getSelectionModel().getSelectedItem().getValue();
        int idSelected = servicioSeleccionado.getId_servicio();
       // table_servicios.getSelectionModel().getSelectedItem().setGraphic(new Rectangle(110, 80, Paint.valueOf("CB3234")));
       
         if(conexionEscrituraServicios.cancelarServicio(idSelected)){
            for(Servicio servicioActual : listaServicios){
                if(servicioActual.getId_servicio() == idSelected){
                    servicioActual.setServicioActivo(false);                 
                }
            }
        }
    }
    TreeItem<Models.Servicio> rootPendientes = new RecursiveTreeItem<>(listaServiciosPendientes, (recursiveTreeObject) -> recursiveTreeObject.getChildren());

    @FXML
    private void tooglebtn_OnAction(ActionEvent event) {
        
        JFXToggleButton toggle = (JFXToggleButton)event.getSource();
        if(toggle.isSelected()){
            
            //listaServiciosFiltro = FXCollections.observableArrayList();            
           // ObservableList<TreeItem<Servicio>> children = table_servicios.getRoot().getChildren();
            listaServiciosPendientes.clear();
            for(Servicio servicioActual: listaServicios){
               //Un servicio es activo cuando:
               //Servicio Regular: cuando no se ha mandado taxi.
               //Servicio Programado: cuando aún se espera que los dias siguientes, se siga mandando una unidad.
                if(servicioActual.isServicioActivo() == true /*si no es cancelado no aparece*/  ){
                    listaServiciosPendientes.add(servicioActual);
                }
                //el setRoot estaba aquí, por eso se congelaba tanto la interfaz
                //   setCancelledGraphic();
            }
            table_servicios.setRoot(null);
            table_servicios.setRoot(rootPendientes);
            table_servicios.setShowRoot(false);                   
                   
        }else{
            table_servicios.setRoot(null);
            table_servicios.setRoot(root);
            table_servicios.setShowRoot(false);
           // table_servicios.refresh();
          //  setCancelledGraphic();
        }
    }
 }
