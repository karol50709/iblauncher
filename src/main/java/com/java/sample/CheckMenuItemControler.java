package com.java.sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;

import java.util.ArrayList;

class CheckMenuItemControler {

    private ArrayList<CheckMenuItem> checkMenuItemArrayList;

    private CheckMenuItem randomServer;

    CheckMenuItemControler(ArrayList<CheckMenuItem> checkMenuItemArrayList, CheckMenuItem randomServer) {
        this.checkMenuItemArrayList = checkMenuItemArrayList;
        this.randomServer = randomServer;
        addOnAction();
    }

    private void addOnAction(){
        for (CheckMenuItem c:checkMenuItemArrayList) {
            c.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    setter(event.getSource());
                }
            });
        }
    }

    private void setter(Object x){
        for (CheckMenuItem c:checkMenuItemArrayList){
            if(c==x){
                c.setSelected(true);
            }
            else{
                c.setSelected(false);
            }
        }
    }

    String getIP_Setting(){
        String result="";
        for (CheckMenuItem c:checkMenuItemArrayList){
            if(c!=randomServer && c.isSelected())
                result=c.getText();
        }
        return result;
    }
}
