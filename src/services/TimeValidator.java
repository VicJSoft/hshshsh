/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.base.ValidatorBase;
import java.time.LocalTime;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author VAPESIN
 */
public class TimeValidator extends ValidatorBase{

    private LocalTime time;

    private TimeValidator() {
    }


    public TimeValidator(LocalTime time,String msg){
        this.time = time;
        setMessage(msg!=null?msg:"Error Time Selected.");
    }
    
    @Override
    protected void eval() {

        if (srcControl.get() instanceof JFXTimePicker) {

            evalTimePicker();

        }

    }

    private void evalTimePicker() {

        JFXTimePicker timePicker = (JFXTimePicker) srcControl.get();
        LocalTime time = timePicker.getValue();
        
        if(time!=null){
            
            if(time.isBefore(this.time)){
                hasErrors.set(true);
            }
            else{
                hasErrors.set(false);
            }
        }

    }
    
}



