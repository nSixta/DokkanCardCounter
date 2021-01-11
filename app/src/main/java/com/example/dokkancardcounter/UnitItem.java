package com.example.dokkancardcounter;

public class UnitItem {

    private String unitName;
    private String unitPicture;

    public UnitItem(String unitName, String unitPicture) {
        this.unitName = unitName;
        this.unitPicture = unitPicture;
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
