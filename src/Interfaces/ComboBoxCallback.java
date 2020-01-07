/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 *
 * @author VAPESIN
 */
public interface ComboBoxCallback {
    
        Callback<ListView<IModelReport>, ListCell<IModelReport>> cellFactory =
            new Callback<ListView<IModelReport>, ListCell<IModelReport>>() {
        @Override
        public ListCell<IModelReport> call(ListView<IModelReport> param) {
           
            return new ListCell<IModelReport>(){

                
                @Override
                protected void updateItem(IModelReport item, boolean empty) {
                   // super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                    if (item == null || empty) {
                        setGraphic(null);
                        
                        //setText("");
                    } else {
                        setText(item.getName()+ "    " );
                    }
                }

            };
        }
    };
    
}
