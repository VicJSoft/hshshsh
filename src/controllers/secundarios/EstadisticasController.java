/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Interfaces.ComboBoxCallback;
import Interfaces.IModelReport;
import Resources.statics.Statics;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXNodesList;
import java.net.URL;
import java.util.ResourceBundle;
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
import services.sql.read.ConexionLecturaTaxistas;

/**
 * FXML Controller class
 *
 * @author vicen
 */
public class EstadisticasController implements Initializable,ComboBoxCallback {

    private final ConexionLecturaTaxistas conexionLecturaTaxistas = new ConexionLecturaTaxistas();
    private final ConexionLecturaClientes conexionLecturaClientes = new ConexionLecturaClientes();
    private final ConexionLecturaEmpleados conexionLecturaEmpleados = new ConexionLecturaEmpleados();
    @FXML
    private LineChart<String, Double> linechart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    
    @FXML private LineChart.Series <String, Double> series;
    
    @FXML
    private JFXComboBox<String> comboBox_tipoReporte;
    @FXML
    private JFXComboBox<IModelReport> comboBox_multiple;
    
    ObservableList<XYChart.Series<String, Double>> lineChartData = FXCollections.observableArrayList();

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

               
               graficar();

           }
       });

        
        comboBox_multiple.setButtonCell(cellFactory.call(null));
        comboBox_multiple.setCellFactory(cellFactory);
          x.setLabel("Mes");       
        y.setLabel("Cantidad");  
        
      
                                
        // Iniciamos el objeto series
        series = new LineChart.Series<>();
        series.setName("My portfolio");
        
        series.getData().add(new XYChart.Data("Jan", 23d));  
        series.getData().add(new XYChart.Data("Feb", 14d));
        series.getData().add(new XYChart.Data("Mar", 15d));
        series.getData().add(new XYChart.Data("Apr", 24d));
        series.getData().add(new XYChart.Data("May", 34d));
        series.getData().add(new XYChart.Data("Jun", 36d));
        series.getData().add(new XYChart.Data("Jul", 22d));
        series.getData().add(new XYChart.Data("Aug", 45d));
        series.getData().add(new XYChart.Data("Sep", 500d));
        series.getData().add(new XYChart.Data("Oct", 17d));
        //series.getData().add(new XYChart.Data("Nov", 29d));
        
        XYChart.Data<String,Double> data = new XYChart.Data("Nov",42d);
        data.setNode(new HoveredThresholdNode(data.getYValue()));
        
        series.getData().add(data);
        series.getData().add(new XYChart.Data("Dec", 25d));
        
         // Guardamos todos los puntos de la función que hemos obtenido
        lineChartData.add(series);
        
        // Si No quereis que se pinten los puntos, poner a false
        linechart.setCreateSymbols(true);
        linechart.setTitle("Reporte");
        linechart.setAnimated(true);
        linechart.setLegendSide(Side.BOTTOM);
        linechart.setLegendVisible(true);
        
        
        
        // Ponemos los puntos en la gráfica
        linechart.setData(lineChartData);
       
      
    
        
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
//           comboBox_multiple.setItems(conexionLecturaClientes.getClientes_id_name());
           comboBox_multiple.setVisible(true);
           
       }
       else if(selectedItem.equals(Statics.reportes.get(4)))
       {
           comboBox_multiple.setPromptText("Seleccione Modulador");
      //     comboBox_multiple.setItems(conexionLecturaClientes.getClientes_id_name());
           comboBox_multiple.setVisible(true);
           
       }
       else
       {
           comboBox_multiple.setVisible(false);
       }
       
    }
    
    private void graficar(){
        
    }
    
  /** a node which displays a value on hover, but is otherwise empty */
  class HoveredThresholdNode extends StackPane {
      
    HoveredThresholdNode( double value) {
      setPrefSize(15, 15);

      final Label label = createDataThresholdLabel(value);

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
      label.setStyle("-fx-font-size: 20; -fx-font-weight: bold; ");

      label.setTextFill(Color.DARKGRAY);
     
      label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
      return label;
    }
  }
 
}
