package com.advancedandroid.quanlysach_newui.mData;

public class mTypesOfBook {
    private int id;
    private String typesCode;
    private String typesName;
    private String typesLocation;
    private String typesDescription;

    public mTypesOfBook() {
    }

    public mTypesOfBook(String typesCode, String typesName, String typesLocation, String typesDescription) {
        this.typesCode = typesCode;
        this.typesName = typesName;
        this.typesLocation = typesLocation;
        this.typesDescription = typesDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypesCode() {
        return typesCode;
    }

    public void setTypesCode(String typesCode) {
        this.typesCode = typesCode;
    }

    public String getTypesName() {
        return typesName;
    }

    public void setTypesName(String typesName) {
        this.typesName = typesName;
    }

    public String getTypesLocation() {
        return typesLocation;
    }

    public void setTypesLocation(String typesLocation) {
        this.typesLocation = typesLocation;
    }

    public String getTypesDescription() {
        return typesDescription;
    }

    public void setTypesDescription(String typesDescription) {
        this.typesDescription = typesDescription;
    }
}
