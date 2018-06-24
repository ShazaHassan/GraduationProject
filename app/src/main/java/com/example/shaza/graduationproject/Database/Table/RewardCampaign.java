package com.example.shaza.graduationproject.Database.Table;

public class RewardCampaign {
    private String Name, Duration, NeededMoney, Heighlight, Vision, Offers, HelperTeam, Campaign_Image, IDCreator, Category, FundedMoney, endDate;


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

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFundedMoney() {
        return FundedMoney;
    }

    public void setFundedMoney(String fundedMoney) {
        FundedMoney = fundedMoney;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIDCreator() {
        return IDCreator;
    }

    public void setIDCreator(String IDCreator) {
        this.IDCreator = IDCreator;
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
