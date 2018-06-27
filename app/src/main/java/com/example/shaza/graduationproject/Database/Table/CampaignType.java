package com.example.shaza.graduationproject.Database.Table;

public class CampaignType {
    private String type, id;

    public CampaignType() {
    }

    public CampaignType(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
}
