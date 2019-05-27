package com.java.sample;

import com.victorlaerte.asynctask.AsyncTask;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;

public class Worker extends AsyncTask<String,Integer,Boolean>{

    final Controller controller;

    String command;

    Project project;
    String header;
    TextArea textArea;

    public Worker(Controller controller,String command,Project project,String header){
        this.controller=controller;
        this.command=command;
        this.project=project;
        this.header=header;
        this.textArea= new TextArea();
    }



    @Override
    public void onPreExecute() {


        Tab tab = new Tab(String.format("%s - %s", header,project.getName()));
        tab.setContent(textArea);
        controller.tabPane.getTabs().add(tab);
    }

    @Override
    public Boolean doInBackground(String... strings) {
        controller.execBatch(textArea,command,true);
        System.out.println("diB");

        return true;
    }

    @Override
    public void onPostExecute(Boolean aBoolean) {
        System.out.println("onPE");
    }

    @Override
    public void progressCallback(Integer... integers) {
        System.out.println("pc");

    }
}
