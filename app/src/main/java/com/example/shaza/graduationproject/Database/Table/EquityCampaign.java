package com.example.shaza.graduationproject.Database.Table;

public class EquityCampaign {
    private String Name, ImgName, duration, IDCamp, IDCreator,
            Offers, Timeline, Highlight, Summary, Market,
            InvestTerms, InvestDiscussion, StartDate, EndDate, Team, category, LinkForVideo;
    private Long NoOfFunded, NeededMoney, FundedMoney;

    public String getLinkForVideo() {
        return LinkForVideo;
    }

    public void setLinkForVideo(String linkForVideo) {
        LinkForVideo = linkForVideo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTeam() {
        return Team;
    }

    public void setTeam(String team) {
        Team = team;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImgName() {
        return ImgName;
    }

    public void setImgName(String imgName) {
        ImgName = imgName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIDCamp() {
        return IDCamp;
    }

    public void setIDCamp(String IDCamp) {
        this.IDCamp = IDCamp;
    }

    public String getIDCreator() {
        return IDCreator;
    }

    public void setIDCreator(String IDCreator) {
        this.IDCreator = IDCreator;
    }

    public String getOffers() {
        return Offers;
    }

    public void setOffers(String offers) {
        Offers = offers;
    }

    public String getTimeline() {
        return Timeline;
    }

    public void setTimeline(String timeline) {
        Timeline = timeline;
    }

    public String getHighlight() {
        return Highlight;
    }

    public void setHighlight(String highlight) {
        Highlight = highlight;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getMarket() {
        return Market;
    }

    public void setMarket(String market) {
        Market = market;
    }

    public String getInvestTerms() {
        return InvestTerms;
    }

    public void setInvestTerms(String investTerms) {
        InvestTerms = investTerms;
    }

    public String getInvestDiscussion() {
        return InvestDiscussion;
    }

    public void setInvestDiscussion(String investDiscussion) {
        InvestDiscussion = investDiscussion;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public Long getNoOfFunded() {
        return NoOfFunded;
    }

    public void setNoOfFunded(Long noOfFunded) {
        NoOfFunded = noOfFunded;
    }

    public Long getNeededMoney() {
        return NeededMoney;
    }

    public void setNeededMoney(Long neededMoney) {
        NeededMoney = neededMoney;
    }

    public Long getFundedMoney() {
        return FundedMoney;
    }

    public void setFundedMoney(Long fundedMoney) {
        FundedMoney = fundedMoney;
    }
}
