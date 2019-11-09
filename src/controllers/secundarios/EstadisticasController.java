/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.secundarios;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author vicen
 */
public class EstadisticasController implements Initializable {

         @FXML
    private LineChart<String, Double> linechart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;
    @FXML private LineChart.Series <String, Double> series;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        /*final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Month");       
        
        linechart = 
                new LineChart<String,Number>(xAxis,yAxis);
                
        linechart.setTitle("Stock Monitoring, 2010");
                                
        XYChart.Series series = new XYChart.Series();
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
        linechart.getData().add(series);*/
   
   
       
        
        x.setLabel("Month");       
        
      
                                
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
        
        
        
        // Ponemos los puntos en la gráfica
        linechart.setData(lineChartData);
       
      
    
        
    }    
    
}
