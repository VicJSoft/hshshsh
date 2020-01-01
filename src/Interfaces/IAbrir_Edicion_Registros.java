/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Models.Taxis;

/**
 * Esta interface es el intermedio para mandar un objeto (editado también) a través de ventanas.
 * @author vic xd
 */
public abstract interface IAbrir_Edicion_Registros 
{

   
    /**
     * 
     * Metodo que invocará el evento.
     * 
     * @param registro 
     * El registro se trata como si fuera object y así hacer casting correspondiente dentro de cada controller.
     * 
     * @param isEdit
     * Indica si el registro es para edición o no.
     */
    public void registroEditNuevo(Object registro);
    
}
