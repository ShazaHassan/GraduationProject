package com.example.shaza.graduationproject.TemplateForAdapter;

/**
 * Created by Shaza Hassan on 16-Dec-17.
 */

public class ImgAndText {
    private int img, total, get;
    private String descriptionText, daysLeft, need, CampaignName, category;


    public ImgAndText(int img, String descriptionText, String CampaignName, String daysLeft, String need,
                      int total, int get, String category) {
        this.img = img;
        this.descriptionText = descriptionText;
        this.daysLeft = daysLeft;
        this.need = need;
        this.CampaignName = CampaignName;
        this.total = total;
        this.get = get;
        this.category = category;
    }

    public int getImg() {
        return img;
    }

    public String getText() {
        return descriptionText;
    }

    public String getDaysLeft() {
        return daysLeft;
    }

    public String getNeed() {
        return need;
    }

    public String getCampaignName() {
        return CampaignName;
    }

    public int getTotal() {
        return total;
    }

    public int getGet() {
        return get;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setGet(int get) {
        this.get = get;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public void setCampaignName(String campaignName) {
        CampaignName = campaignName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
