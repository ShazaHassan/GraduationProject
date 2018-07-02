package com.example.shaza.graduationproject.Database.Table;

public class RewardCampaign {
    private String Name, Duration, Heighlight, Vision, Offers, HelperTeam, Campaign_Image, IDCreator, Category,
            endDate, startDate, IDCampaign, LinkForVideo;
    private long noOfFunded, NeededMoney, FundedMoney;


    public RewardCampaign(String name, String duration, long neededMoney, String heighlight, String vision, String offers,
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

    public String getLinkForVideo() {
        return LinkForVideo;
    }

    public void setLinkForVideo(String linkForVideo) {
        LinkForVideo = linkForVideo;
    }

    public RewardCampaign(String heighlight) {
        Heighlight = heighlight;
    }

    public long getNoOfFunded() {
        return noOfFunded;
    }

    public void setNoOfFunded(long noOfFunded) {
        this.noOfFunded = noOfFunded;
    }

    public String getIDCampaign() {
        return IDCampaign;
    }

    public void setIDCampaign(String IDCampaign) {
        this.IDCampaign = IDCampaign;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getFundedMoney() {
        return FundedMoney;
    }

    public void setFundedMoney(long fundedMoney) {
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

    public long getNeededMoney() {
        return NeededMoney;
    }

    public void setNeededMoney(long neededMoney) {
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
