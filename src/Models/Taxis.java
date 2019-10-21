/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author vicen
 */
public class Taxis  extends RecursiveTreeObject<Taxis>
{
    
    private SimpleStringProperty  marca, modelo,placa, id,taxista;

    public Taxis() {
    }

    
    public Taxis(String id, String marca, String modelo, String placa,String taxista) {
        this.id = new SimpleStringProperty(id);
        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.placa = new SimpleStringProperty(placa);
        this.taxista = new SimpleStringProperty(taxista);
    }

    public String getMarca() {
        return marca.get();
    }

    public void setMarca(String marca) {
        this.marca = new SimpleStringProperty(marca);
    }

    public String getModelo() {
        return modelo.get();
    }

    public void setModelo(String modelo) {
        this.modelo = new SimpleStringProperty(modelo);
    }

    public String getPlaca() {
        return placa.get();
    }

    public void setPlaca(String placa) {
        this.placa = new SimpleStringProperty(placa);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id = new SimpleStringProperty(id);
    }
    public String getId_taxista() {
        return taxista.get();
    }

    public void setId_taxista(String taxista) {
        this.taxista  = new SimpleStringProperty(taxista);
    }
    
    
   

   
}
