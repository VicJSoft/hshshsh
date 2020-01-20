/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Interfaces.IAbrir_Edicion_Registros;
import Models.Clientes;
import Models.Servicio;
import Resources.statics.Statics;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.Servicios;
import services.sql.read.ConexionLecturaClientes;
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
    
    @FXML
    private JFXTextField txt_servicioRapido;

    @FXML
    private JFXTextField txt_cantidad;
    

    private final ConexionLecturaServicios conexionLecturaServicios = new ConexionLecturaServicios();
    private final ConexionEscrituraServicios conexionEscrituraServicios = new ConexionEscrituraServicios();
    private final ConexionLecturaClientes conexionLecturaClientes = new ConexionLecturaClientes();
    
    
    private  ObservableList<Models.Servicio> listaServicios = FXCollections.observableArrayList();
    private  ObservableList<Models.Servicio> listaServiciosFiltro = FXCollections.observableArrayList();
    private  ObservableList<Models.Servicio> listaServiciosPendientes = FXCollections.observableArrayList();
    TreeItem<Models.Servicio> root;
    @FXML
    private JFXToggleButton togglebtn_ServiciosPendientes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       this.btnAdd_Servicios.setTooltip(new Tooltip("Nuevo servicio.(F1)"));
       this.btnEdit_Servicios.setTooltip(new Tooltip("Servicio aplicado.(F2)"));
       this.btnDelete_Servicios.setTooltip(new Tooltip("Cancelar servicio.(F3)"));
       this.txt_servicioRapido.setTooltip(new Tooltip("Servicio rápido. (F4)"));
       
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
        table_servicios.setOnKeyReleased(new EventHandler<KeyEvent>() {
              @Override
              public void handle(KeyEvent event) {

                    if(null!=  event.getCode())switch (event.getCode()) {
                      case ENTER:
                          //el boton de edicion está desactivado hasta que no se seleccione algo, por lo tanto, si este se llama con el boton disableado, no pasa nada.
                          btnEdit_Servicios.fire();
                          break;
                      case F1:
                          btnAdd_Servicios.fire();
                          break;
                      case F2:
                          btnEdit_Servicios.fire();
                          break;
                      case F3:
                          btnDelete_Servicios.fire();
                          break;
                          
                      case F4:
                          txt_servicioRapido.requestFocus();
                          break;
                      default:
                          break;
                  }

              }
        });
        table_servicios.scrollTo( table_servicios.getCurrentItemsCount()-1);
        togglebtn_ServiciosPendientes.fire();
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
        textField_buscar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if(event.getCode()==KeyCode.ESCAPE){
                    textField_buscar.clear();
                }

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
                    listaServicios.add(servicio);
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
        Servicio servicioSeleccionado = table_servicios.getSelectionModel().getSelectedItem().getValue();
        //Cuando está sin unidad (pendiente)(siempre)(si el idUnidad es 0 (cancelado) no entra 7u7)
        if(servicioSeleccionado.getIdUnidad()==null){
            
            Ventana_AsignarUnidadController ventanaAsignarUnidad = 
                    (Ventana_AsignarUnidadController) Servicios.crearVentana("/views/Ventana_AsignarUnidad.fxml", (Stage) this.btnAdd_Servicios.getScene().getWindow(), getClass());

            ventanaAsignarUnidad.setIAbrirEdicionRegistro(new IAbrir_Edicion_Registros() {
                @Override
                public void registroEditNuevo(Object registro) {
                    String registroS = (String) registro;
                    String informacion[] = registroS.split(":");
                    Integer idUnidad = Integer.parseInt(informacion[0]);
                    servicioSeleccionado.setIdUnidad(idUnidad);
                        try{
                            String nota= informacion[1];
                            servicioSeleccionado.setNotas(nota);
                        }catch(IndexOutOfBoundsException ex){
                            System.out.println("No hubo uno de esos index.");
                        }
                        try{
                            String observaciones= informacion[2];
                            servicioSeleccionado.setObservaciones(observaciones);                                                                                        
                        }catch(IndexOutOfBoundsException ex){

                            System.out.println("No hubo uno de esos index.");

                        }
                  
                    if(conexionEscrituraServicios.asignarUnidad(servicioSeleccionado)){
                        
                       table_servicios.refresh();
                       if(togglebtn_ServiciosPendientes.isSelected()){
                            listaServiciosPendientes.remove(servicioSeleccionado);
                        }
                    }


                }
            });
        }
        
     }

    private void cancelarServicio(String color){
        Servicio servicioSeleccionado = table_servicios.getSelectionModel().getSelectedItem().getValue();
        
        Servicio servicioSeleccionadoLista = listaServicios.get( listaServicios.indexOf(servicioSeleccionado) );
        
        int idSelected = servicioSeleccionadoLista.getId_servicio();
        if(!servicioSeleccionadoLista.isServicioActivo() && servicioSeleccionadoLista.getIdUnidad()!=null)
            if(servicioSeleccionadoLista.getIdUnidad()>0)
                return;//cuando el servicio está incactivo y tiene unidad asignado, sigifica que ya fue aplicado.
        if(!servicioSeleccionadoLista.isProgramadow()){//cancelar servicio regular
            servicioSeleccionadoLista.setIdUnidad(0);//asigna unidad 0 de cancelado, y se hace la consulta para cambiar la unidad.
            if(conexionEscrituraServicios.asignarUnidad(servicioSeleccionadoLista)) {
                //servicioSeleccionado.setServicioActivo(false);  
               // servicioSeleccionadoLista.cb_estado.fire();

            }           
        } 
        //todos los marca como inactivos;
        if(conexionEscrituraServicios.cancelarServicio(idSelected)){
            servicioSeleccionadoLista.setServicioActivo(false);                 
        }
        if(togglebtn_ServiciosPendientes.isSelected()){
          listaServiciosPendientes.remove(servicioSeleccionado);
        }
        //table_servicios.refresh();
         
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
    
    
    //ServiciosCRUDController crud = new ServiciosCRUDController();
    @FXML
    public void txtServicioRapido_OnKeyReleased(KeyEvent event){
       /* if(event.getCode() == KeyCode.ENTER){
            ServiciosCRUDController.buscadorTelefono busquedaTelefono;
            busquedaTelefono = crud.new buscadorTelefono(this.txt_servicioRapido.getText());
            //pasarlo al thread, pero alv, así no se hace xd.//no se sabria si el trhead haria todo correcto. sería mejor usarlo en el thread main
            Clientes cliente = busquedaTelefono.getCliente();
           // ServiciosCRUDController.buscadorTelefono bTelefono =
             //       new ServiciosCRUDController.buscadorTelefono("asd");
            
        }*/
       
       if(event.getCode() == KeyCode.ENTER && !this.txt_servicioRapido.getText().equals("")){
          txt_cantidad.requestFocus();
       }

        
    }
    
    @FXML
    public void txtCantidad_OnKeyReleased(KeyEvent event){
        Clientes clientByNumber = conexionLecturaClientes.getClientByNumber(this.txt_servicioRapido.getText());
        if(clientByNumber==null){
            //si no trae ningun cliente, significa que no existe, entonces ->
            btnAdd_Servicios.fire();
        }else if(event.getCode()==KeyCode.ENTER){
             int cantidad = 1;
            try{
                cantidad= Integer.parseInt(this.txt_cantidad.getText());
            }catch(NumberFormatException ex){
                System.out.println("Error, ingresar numeros.");
                //si la conversion falla, por defecto será = 1;
                cantidad = 1;
            }
                // cantidad = cantidad==0?1:cantidad;//xd, para setearlo en 1, por si metieron 0
                for (int j=0;j<cantidad;j++){
                    Servicio servicio = 
                            new Servicio(0, clientByNumber.getTelefono(), clientByNumber.getNombre(), 
                                    clientByNumber.getCalle(), clientByNumber.getColonia(), clientByNumber.getNumeroExt(), clientByNumber.getNumeroInt(),
                                    clientByNumber.getObservaciones(), ""/*notas*/,null/*idUnidad siempre pendientes*/, Statics.EMPLEADO_SESION_ACTUAL.getId_empleado(), 
                                    true/*1, para que lo marque pendiente*/, ""/*destino*/,
                                    LocalDate.now(), LocalTime.now(), false/*diario*/, null, false, null/*fechafin*/);
                    if(conexionEscrituraServicios.insertServicio(servicio)){
                        listaServicios.add(servicio);
                        listaServiciosPendientes.add(servicio);
                    }
                }
                txt_cantidad.clear();
                txt_servicioRapido.clear();
                txt_servicioRapido.requestFocus();
        }
    }
    
 }
