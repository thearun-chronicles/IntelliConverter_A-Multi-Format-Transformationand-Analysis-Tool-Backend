package com.coverter.intelliconverter.datatransferobject;


public class ImageToDataTransferObject {
    private String message;
    private String fileName;

    public ImageToDataTransferObject(String message, String fileName) {
        this.message = message;
        this.fileName = fileName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
