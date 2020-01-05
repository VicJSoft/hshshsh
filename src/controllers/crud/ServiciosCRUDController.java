/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.crud;

import Interfaces.IAbrir_Edicion_Registros;
import Interfaces.IValidateCRUD;
import Models.Servicio;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sun.webkit.dom.KeyboardEventImpl;
import java.awt.RenderingHints.Key;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.Servicios;
import services.StringLengthValidator;
import services.TimeValidator;

/**
 * FXML Controller class
 *
 * @author ESPINO
 */
public class ServiciosCRUDController implements Initializable,IValidateCRUD {

    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane ap_tittleBar;
    @FXML
    private ImageView iv_iconBar;
    @FXML
    private Button btn_cerrar;
    @FXML
    private Label lbl_tittleBar;
    @FXML
    private Label lbl_tittle;
    @FXML
    private JFXTextField textField_telefono_buscar;
    @FXML
    private JFXTextField textField_telefono;

    @FXML
    private JFXTextField textField_nombre;

    @FXML
    private JFXTextField textField_calle;

    @FXML
    private JFXTextField textField_colonia;

    @FXML
    private JFXTextField textField_num_ext;

    @FXML
    private JFXTextField textField_numInt;

    @FXML
    private JFXTextField textField_notas;

    @FXML
    private JFXTextField textField_unidad;

    @FXML
    private JFXTextField textField_observaciones;
    @FXML
    private JFXRadioButton rb_Regular;
    @FXML
    private ToggleGroup tipoServicio;
    @FXML
    private JFXRadioButton rb_programado;
    @FXML
    private JFXRadioButton rb_personalizado;
    @FXML
    private ToggleGroup toggletipoProgramado;
    @FXML
    private JFXRadioButton rb_diario;
    @FXML
    private JFXCheckBox cb_lunes;
    @FXML
    private JFXCheckBox cb_martes;
    @FXML
    private JFXCheckBox cb_miercoles;
    @FXML
    private JFXCheckBox cb_jueves;
    @FXML
    private JFXCheckBox cb_viernes;
    @FXML
    private JFXCheckBox cb_sabado;
    @FXML
    private JFXCheckBox cb_domingo;
    @FXML
    private JFXTimePicker timePicker_horaServicio;
    @FXML
    private JFXDatePicker datePicker_dia;
    @FXML
    private JFXTextField txt_destino;
    @FXML
    private Button btn_aceptar;
    @FXML
    private Button btn_cancelar;
    @FXML
    private Label lbl_errorConfigTipoServicio;
    @FXML
    private JFXToggleButton togglebtn_ServiciosPendientes;
    
    private ArrayList<IFXValidatableControl> listControlsRequired = new ArrayList();
    private ArrayList<JFXCheckBox> listaCheckBox = new ArrayList();
    private IAbrir_Edicion_Registros iAbrir_Edicion_Registros;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.lbl_tittleBar.setText("Servicios");
        this.lbl_tittle.setText("Servicios");
        
        setClickedEventCheckBox();
        
        this.rb_Regular.setOnAction((ActionEvent event) -> {
            setChecksDays(false);
            datePicker_dia.setValue(LocalDate.now());
            timePicker_horaServicio.setValue(LocalTime.now());
            rb_personalizado.selectedProperty().set(false);
            rb_diario.selectedProperty().set(false);
        });
          
        this.rb_programado.setOnAction((ActionEvent event) -> {
            //rb_personalizado.selectedProperty().set(true);
            //datePicker_dia.setValue(null);
            //timePicker_horaServicio.setValue(null);
            rb_personalizado.fire();
        });
        this.rb_diario.setOnAction((ActionEvent event) -> {
            //toogleCheckBoxDias();
            setChecksDays(true);
            rb_programado.selectedProperty().set(true);
            datePicker_dia.setValue(LocalDate.now());
            timePicker_horaServicio.setValue(LocalTime.now());
        });
        
        this.rb_personalizado.setOnAction((ActionEvent event) -> {
            rb_programado.selectedProperty().set(true);
            
            
            datePicker_dia.setValue(null);
            timePicker_horaServicio.setValue(null);
          //  datePicker_dia.setValue(LocalDate.now());
          //  timePicker_horaServicio.setValue(LocalTime.now());
            //porque va de 0 a 6 
            //listaCheckBox.get(LocalDate.now().getDayOfWeek().getValue()-1).selectedProperty().set(true);
            
        });
      
       this.listControlsRequired = listControlsRequired();
       this.setFieldValidations();
       rb_personalizado.fire();
       rb_Regular.fire();
    }    

    /*
    
    */
    Thread hiloBusqueda ;
  
    @FXML
    private void txtBuscarTelefono_OnKeyPressed(KeyEvent event) {
        hiloBusqueda =  new Thread(new buscadorTelefono());
        hiloBusqueda.start();
    } 
    @FXML
    private void btnCerrar_Click(ActionEvent event) {
        Servicios.cerrarVentana(event);
    }
    @FXML
    private void btnAceptar_OnAction(ActionEvent event) {
        //llamar a los validate
        if(this.validarCampos()){
            //regresar instancia tipo servicios.
            if(this.iAbrir_Edicion_Registros!=null){
                
                this.iAbrir_Edicion_Registros.registroEditNuevo(getVentanaServicio());
                this.btn_cerrar.fire();
                
            }
        }
    }

    @FXML
    private void btnCancelar_OnAction(ActionEvent event) {
        Servicios.cerrarVentana(event);
    }

    @FXML
    private void tittleBar_Released(MouseEvent event)
    {
    
    }

    @FXML
    private void tittleBarr_Drag(MouseEvent event) {
        Servicios.tittleBar_Drag(event);
    }

    @FXML
    private void tittleBar_Pressed(MouseEvent event) {
        Servicios.tittleBar_Pressed(event);
    }
    

    
    private void setChecksDays(boolean state){
        this.cb_lunes.selectedProperty().set(state);
        this.cb_martes.selectedProperty().set(state);
        this.cb_miercoles.selectedProperty().set(state);
        this.cb_jueves.selectedProperty().set(state);
        this.cb_viernes.selectedProperty().set(state);
        this.cb_sabado.selectedProperty().set(state);
        this.cb_domingo.selectedProperty().set(state);
    }
    
    private void setClickedEventCheckBox(){
        
        
        listaCheckBox.add(this.cb_lunes);
        listaCheckBox.add(this.cb_martes);
        listaCheckBox.add(this.cb_miercoles);
        listaCheckBox.add(this.cb_jueves);
        listaCheckBox.add(this.cb_viernes);
        listaCheckBox.add(this.cb_sabado);
        listaCheckBox.add(this.cb_domingo);
        
        for(JFXCheckBox actualCB : listaCheckBox){
            actualCB.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    rb_programado.selectedProperty().set(true);
                    rb_personalizado.selectedProperty().set(true);
                    calcularFechaInicioServicioProgramado();
                    
                    if(isAllDaysChecked()){
                        rb_diario.fire();
                    }
                }
            }
            );
        }
        

    }
 

    private boolean isAllDaysChecked(){
        boolean check = true;
        
        for(JFXCheckBox cb_actual : listaCheckBox){
            check &= cb_actual.isSelected();
        }
        return check;
    }
    
    @Override
    public ArrayList<IFXValidatableControl> listControlsRequired() {

        ArrayList<IFXValidatableControl> lista = new ArrayList<>();

        lista.add(this.textField_telefono);
        lista.add(this.textField_nombre);
        lista.add(this.textField_calle);
        lista.add(this.textField_colonia);
        lista.add(this.textField_num_ext);
        lista.add(this.textField_unidad);
        lista.add(this.datePicker_dia);
        lista.add(this.timePicker_horaServicio);
        
        return lista;
    }

    @Override
    public void setFieldValidations() {

        this.setRequiredValidation();
        this.setLengthValidation();
        
        
        //solo numeros al campo changeListener
        this.textField_telefono.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(!newValue.equals(""))
            {
                if(newValue.charAt(newValue.length()-1)<48 ||  newValue.charAt(newValue.length()-1)>57)
                {
                    textField_telefono.setText(oldValue);
                }
            }
            else
            {
                textField_telefono.setText("");
            }
        });
        
        this.textField_telefono_buscar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(!newValue.equals(""))
            {
                if(newValue.charAt(newValue.length()-1)<48 ||  newValue.charAt(newValue.length()-1)>57)
                {
                    textField_telefono.setText(oldValue);
                }
            }
            else
            {
                textField_telefono.setText("");
            }
        });
        
        this.textField_unidad.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if(!newValue.equals(""))
            {
                if(newValue.charAt(newValue.length()-1)<48 ||  newValue.charAt(newValue.length()-1)>57)
                {
                    textField_unidad.setText(oldValue);
                }
            }
            else
            {
                textField_unidad.setText("");
            }
        });        

    }

    @Override
    public void setLengthValidation() {

        this.textField_telefono.getValidators().add(new StringLengthValidator("Únicamente 10 dígitos permitidos.", 10));
        this.textField_nombre.getValidators().add(new StringLengthValidator("Únicamente 100 carácteres permitidos.", 100));
        this.textField_unidad.getValidators().add(new StringLengthValidator("Únicamente 11 dígitos permitidos.", 11));

        
    }

    @Override
    public void setRequiredValidation() {
       /* 
        this.textField_telefono.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.textField_nombre.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.textField_calle.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.textField_colonia.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.textField_num_ext.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.textField_unidad.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        this.datePicker_dia.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
        */
        for(IFXValidatableControl controlActual:listControlsRequired){
            controlActual.getValidators().add(new RequiredFieldValidator("Este campo es requerido."));
            
            ((Node)controlActual).focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if(!newValue){
                        controlActual.validate();
                    }
                }
            });  
        }

    }

    @Override
    public boolean validarCampos() {

        boolean datosValidos = true;

        for(IFXValidatableControl actual:listControlsRequired){
            actual.validate();
            datosValidos = datosValidos && actual.validate();
        }        
        
        boolean checkBoxes = true;
        //servicios programado?
        if(rb_programado.isSelected()){
            if(rb_personalizado.isSelected()){
                boolean checkingBoxes = 
                        cb_lunes.isSelected() || cb_martes.isSelected()||cb_miercoles.isSelected()
                        || cb_jueves.isSelected() || cb_viernes.isSelected()||cb_sabado.isSelected()
                        || cb_domingo.isSelected()                        
                        ;
                
                checkBoxes = checkBoxes && checkingBoxes;
            }
        }else{
            LocalTime horaServicio = LocalTime.now();
            
            //cuando es un servicio regular.Da un margen de 5 minutos (así podrá mostrar el mensaje de error)
            //mientras sea menor a TimeNow -5, no dará error label.
            timePicker_horaServicio.getValidators().add(new TimeValidator(horaServicio.minusMinutes(5), "La hora del servicio debe ser mayor o igual a la hora actual.") );
            datosValidos &= timePicker_horaServicio.validate();
            timePicker_horaServicio.validate();
        }
        if(!(datosValidos && checkBoxes)){
            this.lbl_errorConfigTipoServicio.setVisible(true);
        }else{
            this.lbl_errorConfigTipoServicio.setVisible(false);
        }
        return datosValidos && checkBoxes;

    }
    /**
     * 
     */
    public void calcularFechaInicioServicioProgramado(){
        int dia = LocalDate.now().getDayOfWeek().getValue();
        int year = LocalDate.now().getYear();
        int dayOfYear = LocalDate.now().getDayOfYear();

        LocalDate fechaASetear = null;
        
        
        ArrayList<Integer> diasSeleccionados = diasSeleccionados();
        ArrayList<LocalDate> diasSeleccionadosLD = new ArrayList<>();

            //Así guarda las fechas de cada dia, para despues solo comparar la lista entre sí,
            //la fecha menor, será la que se pondrá en el datepicker
            for(int diaActualCiclo:diasSeleccionados){
                diasSeleccionadosLD.add(fechaDiaDado(year, dayOfYear, diaActualCiclo));
            }
            for(LocalDate localDateActual : diasSeleccionadosLD){
                
                //LocalDate fechaMenor = localDateActual;
                fechaASetear = localDateActual;
                //si una de las fechas es igual a hoy, inmediatamente, ese será el valor de inicio.
                if(localDateActual.isEqual(LocalDate.now())){
                    fechaASetear = localDateActual;
                    break;
                }
                //si X fecha de la lista es "menor" a fechaASetear, entonces, fechaASetear será ese nuevo valor X
                else if(localDateActual.isBefore(fechaASetear)){
                    fechaASetear  = localDateActual;
                }
            }
            
        datePicker_dia.setValue(fechaASetear);
        
    } 
    
    public void calcularFechaInicioServicioProgramado2(){
        
        
        
        
    }

    private ArrayList<Integer> diasSeleccionados(){
        
        ArrayList<Integer> seleccion = new ArrayList<>();
        
        for(int i = 0; i<7;i++){
            //la lista esta ordenada empezando en lunes, por lo tanto si uno está seleccionado
            //se almacenará el numero de i + 1
            int diaSeleccionadoActual = listaCheckBox.get(i).isSelected()?i+1:-1;
            if(diaSeleccionadoActual!=-1){
                seleccion.add(diaSeleccionadoActual);
               // break;
            }
            
        }
        
        return seleccion;
    }

    private String diasSeleccionadosCadena(){
        String diasSeleccionados = "";
                
        for(int i = 0; i<7;i++){

            int diaSeleccionadoActual = listaCheckBox.get(i).isSelected()?1:0;
                diasSeleccionados += diaSeleccionadoActual;
            
            
        }
        return diasSeleccionados;
    }
    
    /**
     * busca la fecha más proxima para el diaBuscar dado, Busca X dia, apartir de hoy, incluyendo hoy.
     * Y si el dia proximo más cercano es del año siguiente, aumenta el año y empieza a contar desde ahí.
     * @param year
     * Año en el cual se buscará (siempre será el actual).
     * @param dayOfYear
     * Es el dia actual del 1 al 365 (366).
     * @param diaBuscar
     * Numero del 0 al 7, que representa Lunes ... Domingo. que se buscará.
     * @return 
     * La fecha más proxima del dia dado.
    */
    private LocalDate fechaDiaDado(int year, int dayOfYear, int diaBuscar){
        
        LocalDate fechaABuscar = null;
        //PARA UN MAXIMO DE 7 ITERACIONES
        for(int i = 0; i<7 ;i++){
            
            try{
                fechaABuscar = LocalDate.ofYearDay(year, dayOfYear);
            }catch(DateTimeException ex){
                //año bisiesto, tira exeption en dayOfYear = 367+,  
                //año no bisiesto, tira exeption en dayOfYear = 366+,  
               // if(LocalDate.ofYearDay(year, dayOfYear-1).isLeapYear()){}
               //si tira exception, significa que excedio el numero de dia del año.
               //por lo tanto, para encontrar el dia siguiente, se suma 1 año, y dayOfYear = 1, (1 de Enero del Year+);
                dayOfYear = 1;
                year= year + 1;
                fechaABuscar = LocalDate.ofYearDay(year, dayOfYear);
                
            }
            //si el dia de fechaBuscar es igual al dia a buscar, retornará esa fecha
            if(fechaABuscar.getDayOfWeek().getValue() == diaBuscar)
                break;
            
            //para que genere la siguiente fecha y comparar si ya es el dia que se busca.
            dayOfYear++;
        }
        
        return fechaABuscar;
    }
    
    private void setVentanaServicio(Servicio servicio){
        //un servicio es editable?, yo digo que nel,
        //tampoco debería ser borrable, para que no se modifiquen intencionalmente las  estadisticas.
        //por lo tanto no se ocuparia este metodo
    }
    private Servicio getVentanaServicio(){
        Servicio servicio = new Servicio(
                0/*no nescesario*/, this.textField_telefono.getText(), this.textField_nombre.getText(),
                this.textField_calle.getText(), this.textField_colonia.getText(), this.textField_num_ext.getText(), this.textField_numInt.getText(), 
                this.textField_observaciones.getText(), this.textField_notas.getText(),
                Integer.parseInt(this.textField_unidad.getText()),/*id empleado*/ Statics.ID_EMPLEADO_SESION_ACTUAL,
                true, this.txt_destino.getText(), this.datePicker_dia.getValue(), timePicker_horaServicio.getValue(),
                this.rb_diario.isSelected(), diasSeleccionadosCadena(), this.rb_programado.isSelected());
                
        return servicio;
    }
    
    public void setIAbrirEdicionRegistros(IAbrir_Edicion_Registros iAbrir_Edicion_Registros){
        this.iAbrir_Edicion_Registros = iAbrir_Edicion_Registros;
    }
    
        
class buscadorTelefono implements Runnable
{
   
    Connection connection;
    
    public buscadorTelefono()
    {
        connection = Statics.getConnections();
    }
    @Override
    public void run() {
        
         //while(true){
            
            String sql = "select * from clientes where telefono like '"+textField_telefono_buscar.getText()+"%'";
            String  nombre="", telefono="", calle ="",colonia ="",numEx ="",numInt="";
            
            try
            {
                
                PreparedStatement ps= connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                 while(rs.next())
                    {
                        telefono=rs.getString(1);
                        nombre=rs.getString(2);
                        calle=rs.getString(3);
                        colonia=rs.getString(4);
                        numEx=rs.getString(5);
                        numInt=rs.getString(6);
                   }
                 //sino hago esto cambio de variable  tengo que hacer las primeras variables string despues de la query finales y no puedo porque tiene
                 //que estar cambiando en el while del rs.next();
                String nombre1=nombre, telefono1=telefono, calle1 =calle,colonia1 =colonia,numEx1 =numEx,numInt1=numInt;
                Platform.runLater(() -> {
                    textField_telefono.setText(telefono1);
                    textField_nombre.setText(nombre1);
                    textField_calle.setText(calle1);
                    textField_colonia.setText(colonia1);
                    textField_numInt.setText(numInt1);
                    textField_num_ext.setText(numEx1);
                });
                if(textField_telefono_buscar.getText().equals(""))
                {
                    Platform.runLater(() -> {
                    textField_telefono.setText("");
                    textField_nombre.setText("");
                    textField_calle.setText("");
                    textField_colonia.setText("");
                    textField_numInt.setText("");
                    textField_num_ext.setText("");
                });
                }
                  
                    
                  //  break;
            }
            catch(Exception ex){
                System.out.println("entre");
            }
     
     //}
    }
    
}


}
