/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Interfaces.Cargar_Secundaria;
import Resources.statics.Statics;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import services.Servicios;

/**
 * FXML Controller Drawer
 *
 * @author VicEspino
 */
public class DrawerMenuController implements Initializable {

    private Cargar_Secundaria cargar_Secundaria;
    @FXML
    private AnchorPane menu;
    
    @FXML
    private Button btnEmpleados;
    @FXML
    private Button btnClientes;
    @FXML
    private Button btnServicios;
    @FXML
    private Button btnTaxistas;
    @FXML
    private Button btnTaxis;
    @FXML
    private Button btnEstad;
    
    
    @FXML
    private Button btn_configuracion;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        identificarTipoUsuario();
    } 
    
    @FXML
    private void empleados(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Empleados.fxml","Empleados");
    }
    @FXML
    private void taxistas(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Taxistas.fxml","Taxistas");
    }
    @FXML
    private void clientes(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Clientes.fxml","Clientes");
    }
    @FXML
    private void servicios(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Servicios.fxml","Servicios");
    }
    @FXML
    private void taxis(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Taxis.fxml","Taxis");
    }
    @FXML
    private void estadisticas(ActionEvent event)
    {  
        cargar_Secundaria.ventana("/views/secundarios/Estadisticas.fxml","Estadísticas y Reportes");
    }
    
     @FXML
    private void btnAcercaDe_Click(ActionEvent event) {
            cargar_Secundaria.ventana(null, null);
            Servicios.crearVentana(
                    "/views/Acercade.fxml", Servicios.getStageFromEvent(event),getClass());        
    }
    
    @FXML 
    private void cerrarSesion(ActionEvent event) throws IOException
    {
        Servicios.crearVentana(
               "/views/Login.fxml",
               null,getClass());
        Servicios.cerrarVentana(event);
               
    }

    public void setGuardarEnviarListener(Cargar_Secundaria cargar_Secundaria){
        this.cargar_Secundaria = cargar_Secundaria;
    }

    public void actualizarPantalla(String routeXML,String titulo){
        this.cargar_Secundaria.ventana(routeXML, titulo);
    }
    
    @FXML
    private void btnConfiguracion_OnAction(ActionEvent event) throws IOException {
        
        Servicios.crearVentana("/views/Ventana_Configuracion.fxml", Servicios.getStageFromEvent(event),getClass() );
        
    }

    private void identificarTipoUsuario() {

        //admin
        if(Statics.EMPLEADO_SESION_ACTUAL.getTipo_empleado().equalsIgnoreCase(Statics.tipo_empledo.get(0)))
        {
            
        }//modulador
        else if(Statics.EMPLEADO_SESION_ACTUAL.getTipo_empleado().equalsIgnoreCase(Statics.tipo_empledo.get(1))){
            this.btnEmpleados.disableProperty().set(true);
            this.btnTaxis.disableProperty().set(true);
            this.btnTaxistas.disableProperty().set(true);
            this.btnEstad.disableProperty().set(true);
            
            this.btnEmpleados.setVisible(false);
            this.btnTaxis.setVisible(false);
            this.btnTaxistas.setVisible(false);
            this.btnEstad.setVisible(false);
        }


    }
    
}
