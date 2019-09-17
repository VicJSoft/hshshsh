/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Resources.interfaces.Cargar_Secundaria;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.events.JFXDrawerEvent;
import controllers.secundarios.ClientesController;
import controllers.secundarios.EmpleadosController;
import controllers.secundarios.ServiciosController;
import controllers.secundarios.TaxistasController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.Servicios;

/**
 * FXML Controller class
 *
 * @author VicEspino
 */
public class Ventana_PrincipalController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    public AnchorPane container;
    @FXML
    private AnchorPane ap_tittleBar;
    @FXML
    private ImageView iv_iconBar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Label lbl_tittleBar;
    @FXML
    private Label lbl_title;
    @FXML
    private Button btn_Hamburguesa;
    @FXML
    private JFXDrawer drawer_Menu;
    @FXML
    private Button btn_maximizar;
    @FXML
    private Button btn_minimizar;

  
  @Override
    public void initialize(URL location, ResourceBundle resources) {
       
        drawer_Menu.setOnDrawerClosed((event) -> {
           drawer_Menu.toBack();
        });
        drawer_Menu.setOnDrawerOpening(new EventHandler<JFXDrawerEvent>() {
            @Override
            public void handle(JFXDrawerEvent event) {

                drawer_Menu.toFront();
            }
        });
        try 
        {
            /*Seccion por defecto*/
            lbl_tittleBar.setText(Cargar_Secundaria.SECCION[3]);
            lbl_title.setText(Cargar_Secundaria.SECCION[3]);
            FXMLLoader empleadosLoader = new FXMLLoader(getClass().getResource(Cargar_Secundaria.SECCIONPATH[3]));
            container.getChildren().add( empleadosLoader.load());
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Ventana_PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            FXMLLoader drawerLoader = new FXMLLoader(getClass().getResource("/views/DrawerMenu.fxml"));
            AnchorPane menu = drawerLoader.load();
            //Controlador propio de la vista.
            //TODO setear eventos con interfaces a cada boton.
            DrawerMenuController drawerController = drawerLoader.getController();
            drawerController.setGuardarEnviarListener(
            new Cargar_Secundaria() {
                @Override
                public void ventana(int pos) {
                    container.getChildren().clear();
                    try
                    {
                        switch (pos) 
                        {
                            case 0:
                                lbl_tittleBar.setText(Cargar_Secundaria.SECCION[0]);
                                lbl_title.setText(Cargar_Secundaria.SECCION[0]);
                                FXMLLoader empleadosLoader = new FXMLLoader(getClass().getResource(Cargar_Secundaria.SECCIONPATH[0]));
                                container.getChildren().add( empleadosLoader.load());
                                //EmpleadosController empleadosController = empleadosLoader.getController();
                                break;
                            case 1:
                                lbl_tittleBar.setText(Cargar_Secundaria.SECCION[1]);
                                lbl_title.setText(Cargar_Secundaria.SECCION[1]);
                                FXMLLoader taxistasLoader = new FXMLLoader(getClass().getResource(Cargar_Secundaria.SECCIONPATH[1]));
                                container.getChildren().add(taxistasLoader.load());
                                //TaxistasController taxistasController = taxistasLoader.getController();
                                break;
                            case 2:
                                lbl_tittleBar.setText(Cargar_Secundaria.SECCION[2]);
                                lbl_title.setText(Cargar_Secundaria.SECCION[2]);
                                FXMLLoader clientesLoader = new FXMLLoader(getClass().getResource(Cargar_Secundaria.SECCIONPATH[2]));
                                container.getChildren().add(clientesLoader.load());
                                //ClientesController clientesController = clientesLoader.getController();
                                break;    
                            case 3:
                                lbl_tittleBar.setText(Cargar_Secundaria.SECCION[3]);
                                lbl_title.setText(Cargar_Secundaria.SECCION[3]);
                                FXMLLoader serviciosLoader = new FXMLLoader(getClass().getResource(Cargar_Secundaria.SECCIONPATH[3]));
                                container.getChildren().add( serviciosLoader.load());
                                //ServiciosController serviciosController = serviciosLoader.getController();
                                break;
                            case 4:
                                lbl_tittleBar.setText(Cargar_Secundaria.SECCION[4]);
                                lbl_title.setText(Cargar_Secundaria.SECCION[4]);
                                FXMLLoader taxisLoader = new FXMLLoader(getClass().getResource(Cargar_Secundaria.SECCIONPATH[4]));
                                container.getChildren().add( taxisLoader.load());
                                //ServiciosController serviciosController = serviciosLoader.getController();
                                break;
                            default:
                                break;
                        }
                        
                    } 
                    catch (IOException ex) 
                    {
                      Logger.getLogger(Ventana_PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    drawer_Menu.close();
                }
            });
            
            drawer_Menu.setSidePane(menu);
        } catch (IOException ex) {
            Logger.getLogger(Ventana_PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }


    @FXML
    private void btnCerrar_Click(ActionEvent event) {
        
        Servicios.cerrarVentana(event);
        
    }

    @FXML
    private void tittleBarr_Drag(MouseEvent event) {
               
        Stage ventana = Servicios.getStageFromEvent(event);

        //si esta maximizada y se aplica un drag, entonces minimizará
        if(ventana.isMaximized()){
              
            //crear lógica, para que cuando se quite el maximizado, el raton 
            //se encuentre en la misma posición de proporción del toolbar.
           
            double clickSceneX = event.getSceneX();
            double clickSceneY = event.getSceneY();
            double clickScreenX =event.getScreenX();
            double clickScreenY =event.getScreenY();
            
            double widthMaximized = ventana.getScene().getWidth();
            double heightMaximized = ventana.getScene().getHeight();
            
            //obtiene la proporcion del la posición del click.(50%, 60% etc).//la proporcion siempre es la misma.
            double proporcionClickX =clickSceneX/widthMaximized;
            double proporcionClickY = clickSceneY/heightMaximized;
            

            maximizarVentana(event, false);
            // //coords del raton una vez minimizado ( ya con el nuevo tamaño de ventana)//(ventana.getScene().getWidth()*proporcionClickX)                     
           
            //Posicion donde deberia encontrarse mi raton en ventana desmaximizada
            double nuevaPosMouseClicX = (ventana.getScene().getWidth()*proporcionClickX);
            double nuevaPosMouseClicY = (ventana.getScene().getHeight()*proporcionClickY);
            //clickScreenX-12, pone la ventana en la posicion del raton, y si se resta la nueva pos del raton, entonces
            //obtenemos que la ventana se desplaza en proporcion a donde estaba maximizada
            ventana.setX( clickScreenX- 12 -(nuevaPosMouseClicX)  );
            ventana.setY( clickScreenY -12 - (nuevaPosMouseClicY)  );
            
            //Se guardan las coords de referencia nuevas.
            Servicios.setOffsets(
                    clickScreenX -12 -(nuevaPosMouseClicX),
                    clickScreenY +12 - (nuevaPosMouseClicY) );
                     
            //return;
        }else{            
           Servicios.tittleBar_Drag(event);
        
        }
    }
    @FXML
    private void tittleBar_Released(MouseEvent event) {
        
        if(event.getScreenY()<=1){
            maximizarVentana(event,true);
            Stage ventana =Servicios.getStageFromEvent(event); 
            ventana.setX(0);
            ventana.setY(0);
        }
        
    }

    @FXML
    private void tittleBar_Pressed(MouseEvent event) {
        
        Servicios.tittleBar_Pressed(event);
    }
    
        @FXML
    private void btnMaximizar_Click(ActionEvent event) {
                
        Stage ventana =Servicios.getStageFromEvent(event); 
        
        if(ventana.isMaximized()){                       /*
            Screen screen = Screen.getPrimary();
            Rectangle2D sbounds = screen.getBounds();

            double sw = sbounds.getWidth() ;
            double sh = sbounds.getHeight();
            maximizarVentana(event, false);
          
            ventana.setX((sw/2)-(ventana.getWidth()/2));
            ventana.setY((sh/2)-(ventana.getHeight()/2));*/
            maximizarVentana(event, false);
            ventana.centerOnScreen();
        }else{
            maximizarVentana(event, true);
            ventana.setX(0);
            ventana.setY(0);
            
        }
    
    }

    @FXML
    private void btnMinimizar_Click(ActionEvent event) {
        Servicios.minimizeWindows(event);
    }
    
    @FXML
    private void btnHamburguesa_Click(ActionEvent event){
        
         if (drawer_Menu.isOpened()) {
            drawer_Menu.close();
            
        } else {
            drawer_Menu.open();
        }
        
    }

   

    private void maximizarVentana(Event event,boolean state){
        Stage ventana = Servicios.getStageFromEvent(event);
        if(state){            
            ventana.setMaximized(true);
            root.getStyleClass().remove("ventana");
        }
        else{
            ventana.setMaximized(false);
            root.getStyleClass().add("ventana");
            
        }
    }

    
}
