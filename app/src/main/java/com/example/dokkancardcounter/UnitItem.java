package com.example.dokkancardcounter;

public class UnitItem {

    private String id;
    private String unitName;
    private String unitPicture;

    public UnitItem(String id, String unitName, String unitPicture) {
        this.id = id;
        this.unitName = unitName;
        this.unitPicture = unitPicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitPicture() {
        return unitPicture;
    }

    public void setUnitPicture(String unitPicture) {
        this.unitPicture = unitPicture;
    }
}
