package data;



import java.io.Serializable;
import java.util.Arrays;


public class Request implements Serializable {
    private String  requestType;
    private String fileName;
    private String newFileName;
    private int fileId;
    private boolean byId;
    private boolean byName;
    private byte[] fileContent;

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public boolean isById() {
        return byId;
    }

    public void setById(boolean byId) {
        this.byId = byId;
    }

    public boolean isByName() {
        return byName;
    }

    public void setByName(boolean byName) {
        this.byName = byName;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestType='" + requestType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", newFileName='" + newFileName + '\'' +
                ", fileId=" + fileId +
                ", byId=" + byId +
                ", byName=" + byName +
                ", fileContent=" + fileContent.length +
                '}';
    }
}
