package com.example.shaza.graduationproject.Database.Table;

public class Product {
    private String name, startDate, description, IdCreator, ImageURL, category, idProduct;
    private CampaignType campaignType;
    private long price, noOfBuying;

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdCreator() {
        return IdCreator;
    }

    public void setIdCreator(String idCreator) {
        IdCreator = idCreator;
    }

    public CampaignType getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(CampaignType campaignType) {
        this.campaignType = campaignType;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getNoOfBuying() {
        return noOfBuying;
    }

    public void setNoOfBuying(long noOfBuying) {
        this.noOfBuying = noOfBuying;
    }
}
