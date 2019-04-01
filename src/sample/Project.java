package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Project {


    private JSONProject jsonProject;

    public Project(JSONProject x){
        this.jsonProject=x;
        setPath(jsonProject.getProjectPath());
        setName(jsonProject.getProjectName());
        setPathData(jsonProject.getIncomingPath());
        setStatus("");
        if(x.getInfo()==null){
            setInfo("");
        }
        else{
            setInfo(jsonProject.getInfo());
        }

    }

    private SimpleStringProperty name = new SimpleStringProperty(this,"name");
    private SimpleStringProperty path = new SimpleStringProperty(this,"path");
    private SimpleStringProperty pathData = new SimpleStringProperty(this,"pathData");
    private SimpleStringProperty status = new SimpleStringProperty(this,"status");
    private SimpleStringProperty info = new SimpleStringProperty(this,"info");

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {this.name.set(name);}
    public String getPathData() {return pathData.get();}
    public SimpleStringProperty pathDataProperty() {return pathData;}
    public void setPathData(String pathData2) {this.pathData.set(pathData2);}
    public String getPath() {
        return path.get();
    }
    public StringProperty pathProperty() {
        return path;
    }
    public void setPath(String path) {this.path.set(path);}
    public String getStatus() {
        return status.get();
    }
    public StringProperty statusProperty() {
        return status;
    }
    public void setStatus(String status) {this.status.set(status);}
    public String getInfo() {return info.get();}
    public SimpleStringProperty infoProperty() {return info;}
    public void setInfo(String info) {this.info.set(info);}


    public String downloadBatch(){return jsonProject.getDownloadBatch();}
    public String genereteBatch(){return jsonProject.getGenerateBatch();}
    public String sendSampleandMails() {return jsonProject.getSendMailBatch();}
    public String sendToPrinter() {return jsonProject.getSendPrinterBatch();}




}
