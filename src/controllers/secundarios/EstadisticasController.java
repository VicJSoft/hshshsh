/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Models.Servicio;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import services.sql.read.ConexionLecturaClientes;
import services.sql.read.ConexionLecturaEmpleados;
import services.sql.read.ConexionLecturaServicios;
import services.sql.read.ConexionLecturaTaxistas;

/**
 * FXML Controller class
 *
 * @author vicen
 */
public class EstadisticasController implements Initializable {

    private final ConexionLecturaTaxistas conexionLecturaTaxistas = new ConexionLecturaTaxistas();
    private final ConexionLecturaClientes conexionLecturaClientes = new ConexionLecturaClientes();
    private final ConexionLecturaEmpleados conexionLecturaEmpleados = new ConexionLecturaEmpleados();
    private final ConexionLecturaServicios conexionLecturaServicios = new ConexionLecturaServicios();
    @FXML
    private LineChart<String, Integer> linechart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @FXML
    private LineChart.Series <String, Integer> series;
    
    @FXML
    private JFXComboBox<String> comboBox_tipoReporte;
    @FXML
    private JFXComboBox<String> comboBox_multiple;
    
    ObservableList<XYChart.Series<String, Integer>> lineChartData = FXCollections.observableArrayList();

    @FXML
    void generar(ActionEvent event) 
    {
        
    }
    @FXML
    void imprimir(ActionEvent event) 
    {
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        
       comboBox_tipoReporte.setItems(Statics.reportes);
       comboBox_tipoReporte.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
              evaluarReporte(comboBox_tipoReporte.getSelectionModel().getSelectedItem());
           }
       });
       comboBox_multiple.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {

                ObservableList<Servicio> servicios;
                servicios = conexionLecturaServicios.getServicios( getTipoReporte(),comboBox_multiple.getSelectionModel().getSelectedItem().split(" ")[0]);
                separarServiciosPorFecha(servicios);
           }
       });
      
       
        
        x.setLabel("Mes");       
        y.setLabel("Cantidad");  
  
    }    
    private String getTipoReporte(){
        String tipoReporte;
        String cb_selection = comboBox_tipoReporte.getSelectionModel().getSelectedItem();
        
        if(cb_selection.equals(Statics.reportes.get(0)))            
            tipoReporte = "idUnidad";
        else  if(cb_selection.equals(Statics.reportes.get(1)))            
            tipoReporte = "telefono";
        else  if(cb_selection.equals(Statics.reportes.get(2)))            
            tipoReporte = "idEmpleado";
        else if(cb_selection.equals(Statics.reportes.get(3)))
            tipoReporte = "programado";
        else
            tipoReporte = "ERROR";
        
        return tipoReporte;
    }
    
    private void separarServiciosPorFecha(ObservableList<Servicio> servicios){
        
        //contadores = new Set<Map<String,Integer>>[12];
       // ArrayList<XYChart.Data<String,Double>> 
        ArrayList<Integer> contadoresMes = new ArrayList<>();
       //ArrayList< Map<String,Integer>> contadores = new ArrayList<>();
        ObservableList<XYChart.Data<String, Integer>> listaData = FXCollections.observableArrayList();
        for(int i = 0;i<12;i++ ){
            
            contadoresMes.add(i, 0);
            
        }
        //cuenta los servicios por mes y los mete a cada posicion que le corresponde en el array de contadores.
        for(Servicio servicio:servicios){
           
            int numMes = servicio.getFecha_inicio().getMonthValue();

            contadoresMes.set(numMes-1, contadoresMes.get(numMes-1)+1);
            //contadoresMes.add(numMes-1, 

        }
        series = new LineChart.Series<>();
      //  series.getData().clear();
       // series.getData().addAll(listaData);
        series.setName(getTipoReporte());
        
        //ya que se contó cada servicio de cada mes, se debe guardar esa cantidad como un XYCHART.Data
        for(int i = 0;i<12;i++){
            XYChart.Data<String,Integer> punto = new XYChart.Data(getNombreMes(i+1),contadoresMes.get(i));
           // punto.setNode(new HoveredThresholdNode(punto.getYValue()));
            series.getData().add(punto);
            /////series.getData().add(new XYChart.Data(getNombreMes(i+1),contadoresMes.get(i)) );
            listaData.add(punto);
        }
        for(XYChart.Data<String,Integer> a:series.getData()){
            a.setNode(new HoveredThresholdNode(a.getYValue()));
        }
        lineChartData.add(series);
        
        linechart.setCreateSymbols(true);
        linechart.setTitle("Reporte");
        linechart.setAnimated(true);
        linechart.setLegendSide(Side.BOTTOM);
        linechart.setLegendVisible(true);
        
        linechart.setData(lineChartData);

    }
    
    private String getNombreMes(int mes){
        
        String mesStr ="";
        switch(mes){
            case 1:
                mesStr = "Ene";
                break;
            case 2:
                mesStr = "Feb";
                break;
            case 3:
                mesStr = "Marz";
                break;
            case 4:
                mesStr = "Abr";
                break;
            case 5:
                mesStr = "May";
                break;
            case 6:
                mesStr = "Jun";
                break;
            case 7:
                mesStr = "Jul";
                break;
            case 8:
                mesStr = "Ago";
                break;
            case 9:
                mesStr = "Sep";
                break;
            case 10:
                mesStr = "Oct";
                break;
            case 11:
                mesStr = "Nov";
                break;
            case 12:
                mesStr = "Dic";
                break;
            
        }
        return mesStr;
    }
    private void evaluarReporte(String selectedItem)
    {      
       /* ObservableList<XYChart.Data<String, Double>> data = series.getData();
        for(XYChart.Data<String,Double> d : data){
            double a = 0;
            d.getYValue();
            double c = d.getYValue().doubleValue();
            //Double b = d.getYValue();
            HoveredThresholdNode esta = new HoveredThresholdNode(a,c);
            d.setNode( esta);
        }
        */
       if(selectedItem.equals(Statics.reportes.get(0)))
       {
           comboBox_multiple.setPromptText("Seleccione Taxista");
           comboBox_multiple.setItems(conexionLecturaTaxistas.getTaxistas_id_name());
           comboBox_multiple.setVisible(true);
       }
       else if(selectedItem.equals(Statics.reportes.get(1)))
       {
           comboBox_multiple.setPromptText("Seleccione Cliente");
           comboBox_multiple.setItems(conexionLecturaClientes.getClientes_id_name());
           comboBox_multiple.setVisible(true);
           
       }
       else if(selectedItem.equals(Statics.reportes.get(4)))
       {
           comboBox_multiple.setPromptText("Seleccione Modulador");
           comboBox_multiple.setItems(conexionLecturaClientes.getClientes_id_name());
           comboBox_multiple.setVisible(true);
           
       }
       else
       {
           comboBox_multiple.setVisible(false);
       }
       
    }
    
  /** a node which displays a value on hover, but is otherwise empty */
  class HoveredThresholdNode extends StackPane {
      
    HoveredThresholdNode(  double value) {
      setPrefSize(15, 15);

      final Label label = createDataThresholdLabel(  value);

      setOnMouseEntered(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          getChildren().setAll(label);
          setCursor(Cursor.NONE);
          toFront();
        }
      });
      setOnMouseExited(new EventHandler<MouseEvent>() {
        @Override public void handle(MouseEvent mouseEvent) {
          getChildren().clear();
          setCursor(Cursor.CROSSHAIR);
        }
      });
    }

    private Label createDataThresholdLabel( double value) {
      final Label label = new Label((int)value + "");
      label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
      label.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-background-radius:50; -fx-border-radius:50;");

      label.setTextFill(Color.DARKGRAY);
     
      label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
      return label;
    }
  }
 
}
