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
public interface IAbrir_Edicion_Registros 
{
    /**
     * 
     * @param registroEitado
     * El registro se trata como si fuera object y así hacer casting correspondiente dentro de cada controller.
     * 
     */
    public void registroEditado(Object registroEitado);
   
    
    
}
