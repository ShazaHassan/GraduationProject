package com.example.shaza.graduationproject.Database.Table;

public class RewardCampaign {
    private String Name, Duration, NeededMoney, Heighlight, Vision, Offers, HelperTeam, Campaign_Image;

    public RewardCampaign(String name, String duration, String neededMoney, String heighlight, String vision, String offers,
                          String helperTeam, String campaign_Image) {
        Name = name;
        Duration = duration;
        NeededMoney = neededMoney;
        Heighlight = heighlight;
        Vision = vision;
        Offers = offers;
        HelperTeam = helperTeam;
        Campaign_Image = campaign_Image;
    }

    public RewardCampaign() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getNeededMoney() {
        return NeededMoney;
    }

    public void setNeededMoney(String neededMoney) {
        NeededMoney = neededMoney;
    }

    public String getHeighlight() {
        return Heighlight;
    }

    public void setHeighlight(String heighlight) {
        Heighlight = heighlight;
    }

    public String getVision() {
        return Vision;
    }

    public void setVision(String vision) {
        Vision = vision;
    }

    public String getOffers() {
        return Offers;
    }

    public void setOffers(String offers) {
        Offers = offers;
    }

    public String getHelperTeam() {
        return HelperTeam;
    }

    public void setHelperTeam(String helperTeam) {
        HelperTeam = helperTeam;
    }

    public String getCampaign_Image() {
        return Campaign_Image;
    }

    public void setCampaign_Image(String campaign_Image) {
        Campaign_Image = campaign_Image;
    }
}
