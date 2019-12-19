/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import Resources.statics.Statics;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import services.sql.read.ConexionLecturaClientes;
import services.sql.read.ConexionLecturaEmpleados;
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
    private JFXComboBox<String> comboBox_multiple;

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
       
      
       
        
        x.setLabel("Mes");       
        y.setLabel("Cantidad");  
        
      
                                
        ObservableList<XYChart.Series<String, Double>> lineChartData = FXCollections.observableArrayList();
        // Iniciamos el objeto series
        series = new LineChart.Series<>();
        series.setName("My portfolio");
        
        series.getData().add(new XYChart.Data("Jan", 23));  
        series.getData().add(new XYChart.Data("Feb", 14));
        series.getData().add(new XYChart.Data("Mar", 15));
        series.getData().add(new XYChart.Data("Apr", 24));
        series.getData().add(new XYChart.Data("May", 34));
        series.getData().add(new XYChart.Data("Jun", 36));
        series.getData().add(new XYChart.Data("Jul", 22));
        series.getData().add(new XYChart.Data("Aug", 45));
        series.getData().add(new XYChart.Data("Sep", 500));
        series.getData().add(new XYChart.Data("Oct", 17));
        series.getData().add(new XYChart.Data("Nov", 29));
        series.getData().add(new XYChart.Data("Dec", 25));
        
        
       
         // Guardamos todos los puntos de la función que hemos obtenido
        lineChartData.add(series);
        
        // Si No quereis que se pinten los puntos, poner a false
        linechart.setCreateSymbols(true);
        linechart.setTitle("Reporte");
        linechart.setAnimated(true);
        linechart.setLegendSide(Side.TOP);
        linechart.setLegendVisible(true);
        
        
        
        // Ponemos los puntos en la gráfica
        linechart.setData(lineChartData);
       
      
    
        
    }    

    private void evaluarReporte(String selectedItem)
    {
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
    
}
