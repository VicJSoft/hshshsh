/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


/**
 *
 * @author vicen
 */
public class Taxis  extends RecursiveTreeObject<Taxis>
{
    private int id;
    private String  marca, taxista;
    private int modelo,id_taxista;
    private String placa;
    
    public Taxis() {
    }

    
    public Taxis(int id, String marca, int modelo, String placa,String taxista,int id_taxista) {
        this.id =id;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.taxista = taxista;
        this.id_taxista = id_taxista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getModelo() {
        return modelo;
    }

    public void setModelo(int modelo) {
        this.modelo = modelo;
    }

    public String getTaxista() {
        return taxista;
    }

    public void setTaxista(String taxista) {
        this.taxista = taxista;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getId_taxista() {
        return id_taxista;
    }

    public void setId_taxista(int id_taxista) {
        this.id_taxista = id_taxista;
    }
    
    

}
