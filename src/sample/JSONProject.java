package sample;

public class JSONProject {
    private String projectName;
    private String projectPath;
    private String incomingPath;
    private String downloadBatch;
    private String generateBatch;
    private String sendMailBatch;
    private String sendPrinterBatch;
    private String info;


    public String getGenerateBatch() {
        return generateBatch;
    }

    public void setGenerateBatch(String generateBatch) {
        this.generateBatch = generateBatch;
    }

    public String getSendMailBatch() {
        return sendMailBatch;
    }

    public void setSendMailBatch(String sendMailBatch) {
        this.sendMailBatch = sendMailBatch;
    }

    public String getSendPrinterBatch() {
        return sendPrinterBatch;
    }

    public void setSendPrinterBatch(String sendPrinterBatch) {
        this.sendPrinterBatch = sendPrinterBatch;
    }




    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getIncomingPath() {
        return incomingPath;
    }

    public void setIncomingPath(String incomingPath) {
        this.incomingPath = incomingPath;
    }

    public String getDownloadBatch() {
        return downloadBatch;
    }

    public void setDownloadBatch(String downloadBatch) {
        this.downloadBatch = downloadBatch;
    }

    public String getInfo() {return info;}

    public void setInfo(String info) {this.info = info;}


    public JSONProject(){

    }

}
