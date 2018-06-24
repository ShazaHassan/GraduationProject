package com.example.shaza.graduationproject.Database.Table;

public class CampaignType {
    private String Type, ID;

    public CampaignType(String type, String ID) {
        Type = type;
        this.ID = ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
