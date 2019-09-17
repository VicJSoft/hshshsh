/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources.interfaces;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author vicen
 */
public interface Cargar_Secundaria 
{
    public final String SECCION[]={"Empleados","Taxistas","Clientes","Servicios"};
    public final String SECCIONPATH[]={"/views/secundarios/Empleados.fxml","/views/secundarios/Taxistas.fxml","/views/secundarios/Clientes.fxml","/views/secundarios/Servicios.fxml"};
    public abstract void ventana(int pos);
}
