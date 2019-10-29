/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import com.jfoenix.controls.base.IFXValidatableControl;
import java.util.ArrayList;

/**
 *
 * @author Vic Espino
 */
public interface IValidateCRUD {
    
    /**
     * Crea un ArrayList de todos los controles de esta 
     * ventana CRUD que deben tener algun tipo de validación.
     * @return 
     * Retorna ese ArrayList.
     */
    ArrayList<IFXValidatableControl> listControlsRequired();
    /**
     * Llama a los metodos especiales para darles su validación.
     * Las validaciones individuales/unicas, se declaran aquí.
     */
    void setFieldValidations();
    /**
     * Añade validadores de longuitud a los campos de esta ventana CRUD.
     */
    void setLengthValidation();
    /**
     * Añade validadores de requerición a los campos de esta ventana CRUD.
     */
    void setRequiredValidation();
    
    /**
     * Llama a todos los validates de los campos.
     * @return 
     * Retorna AND de los .validate(), de los controles, 
     * retornará TRUE si está todo en orden
     * retornara FALSE si no se cumple con alguno de los validators.
     */
    boolean validarCampos();
    
}
