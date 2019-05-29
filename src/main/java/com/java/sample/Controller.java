package com.java.sample;


import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class Controller implements Initializable {


    @FXML
    StackPane swingPane;

    @FXML
    TableView<Project> Tabel = new TableView<Project>();


    TextArea textArea = new TextArea();

    ObservableList<Project> result = FXCollections.observableArrayList();

    @FXML
    TextField search;

    @FXML
    TabPane tabPane;

    @FXML
    RadioMenuItem autoUpdate;

    @FXML
    CheckMenuItem Auto,Six,Seven,Nine,Ten,Eleven;

    /*
    TODO
    - Sprawdzanie czy są pliki
    - otwieranie katalogów
    - monity

     */

    AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    ArrayList<CheckMenuItem> checkMenuItemArrayList = new ArrayList<>();
    CheckMenuItemControler checkMenuItemControler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Tabel.setItems(result);
        initFilter();

        Tabel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


        JSONConfig jsonConfig = new JSONConfig();


        TableColumn<Project, String> c1 = new TableColumn<Project, String>("Nazwa Projektu");
        TableColumn<Project, String> c2 = new TableColumn<Project, String>("Scieżka Projektu");
        TableColumn<Project, String> c3 = new TableColumn<Project, String>("Scieżka Dane");
        TableColumn<Project, String> c4 = new TableColumn<Project, String>("Status");
        TableColumn<Project, String> c5 = new TableColumn<Project, String>("Info");


        c1.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
        c2.setCellValueFactory(new PropertyValueFactory<Project, String>("path"));
        c3.setCellValueFactory(new PropertyValueFactory<Project, String>("pathData"));
        c4.setCellValueFactory(new PropertyValueFactory<Project, String>("status"));
        c5.setCellValueFactory(new PropertyValueFactory<Project, String>("info"));

        Tabel.getColumns().setAll(c1, c2, c3, c4,c5);


        //swingPane.getChildren().add(textArea);
        loadProjects(jsonConfig);

        autoUpdate.setSelected(atomicBoolean.get());
        //Platform.runLater(checker);

        Thread thread = new Thread(checker);
        thread.start();

        checkMenuItemArrayList.add(Auto);
        checkMenuItemArrayList.add(Six);
        checkMenuItemArrayList.add(Seven);
        checkMenuItemArrayList.add(Nine);
        checkMenuItemArrayList.add(Ten);
        checkMenuItemArrayList.add(Eleven);

        Auto.setSelected(true);

        checkMenuItemControler = new CheckMenuItemControler(checkMenuItemArrayList,Auto);



    }

    private void loadProjects(JSONConfig jsonConfig) {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                try {
                    result.removeAll(result);
                    List<JSONProject> jsonProjects = jsonConfig.fromJSONFile();
                    for (JSONProject jsonProject : jsonProjects) {
                        //result.add(new Project(jsonProject.getProjectName(),jsonProject.getProjectPath(),""));
                        result.add(new Project(jsonProject));
                    }
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    new Thread(task).start();
    }


    private void initFilter() {
        search.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (search.textProperty().get().isEmpty()) {
                    Tabel.setItems(result);
                    return;
                }
                ObservableList<Project> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Project, ?>> cols = Tabel.getColumns();
                for (int i = 0; i < result.size(); i++) {
                    for (int j = 0; j < cols.size(); j++) {
                        TableColumn col = cols.get(j);
                        String cellValue = col.getCellData(result.get(i)).toString().toLowerCase();
                        if (cellValue.contains(search.getText().toLowerCase())) {
                            tableItems.add(result.get(i));
                        }

                    }
                }
                Tabel.setItems(tableItems);
                if (tableItems.size() < 1) {
                    search.setStyle("-fx-text-fill: red;");
                } else {
                    search.setStyle("-fx-text-fill: black;");
                }
            }
        });
    }

    Runnable checker = new Runnable() {
        @Override
        public void run() {
            while (true){
                if(atomicBoolean.get()){
                    updateStatus();
                }
                else{
                    System.out.println("AutoUpdate off");
                }
                try {
                    TimeUnit.MINUTES.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    @FXML
    private void changeAutoUpdate(){
        if(autoUpdate.isSelected()){
            atomicBoolean.set(true);
        }
        else{
            atomicBoolean.set(false);
        }
    }


    @FXML
    private void generateProduction() {
        String IP=checkMenuItemControler.getIP_Setting();
        System.out.println("Server IP: "+IP);

        Project temp = Tabel.getSelectionModel().getSelectedItem();
        if (temp != null) {
            Worker worker = new Worker(this,temp.genereteBatch(),temp,"Generowanie GMC");
            worker.setDaemon(true);
            worker.execute();
        }
    }

    @FXML
    private void sendMails() {
        Project temp = Tabel.getSelectionModel().getSelectedItem();
        if (temp != null) {
            Worker worker = new Worker(this,temp.sendSampleandMails(),temp,"Wystawianie Próbek");
            worker.setDaemon(true);
            worker.execute();
        }
    }

    @FXML
    private void sendProduction() {
        Project temp = Tabel.getSelectionModel().getSelectedItem();
        if (temp != null) {
            Worker worker = new Worker(this,temp.sendToPrinter(),temp,"Kopiowanie Drukarki Kamery");
            worker.setDaemon(true);
            worker.execute();
        }
    }

    @FXML
    private void getData() {
        Project temp = Tabel.getSelectionModel().getSelectedItem();
        if (temp != null) {
            Worker worker = new Worker(this,temp.downloadBatch(),temp,"Pobieranie Danych");
            worker.setDaemon(true);
            worker.execute();
        }
    }



    public void execBatch(TextArea textArea, String command,boolean pB){
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(process.getOutputStream());
            String line;
            while ((line = reader.readLine()) != null) {
                String finalLine = line;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        textArea.appendText(finalLine);
                        textArea.appendText("\r\n");
                    }
                });

                bufferedOutputStream.write('X');
                bufferedOutputStream.flush();
            }
            bufferedOutputStream.write(10);
            bufferedOutputStream.write(13);
            process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void updateStatus() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Update");
                for (Project project : result) {
                    try {
                        int count = 0;
                        ArrayList<String> arrayList = new ArrayList<>();
                        Files.list(Paths.get(project.getPathData())).forEach(x -> arrayList.add(x.toString()));
                        for (String s : arrayList) {
                            if (!Files.isDirectory(Paths.get(s))) {
                                count++;
                            }
                        }
                        if (count > 0) {
                            project.setStatus("Do Generacji");
                        } else {
                            project.setStatus("Brak plików");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        project.setStatus("Błąd wczytania katalogów");
                    }
                }
            };
        };
        Platform.runLater(runnable);
    }

    public static void closeApp(){
        System.out.println("close");
        Platform.exit();
        System.exit(0);
    }




}
