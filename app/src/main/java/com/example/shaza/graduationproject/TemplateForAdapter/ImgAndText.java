package com.example.shaza.graduationproject.TemplateForAdapter;

/**
 * Created by Shaza Hassan on 16-Dec-17.
 */

public class ImgAndText {
    private int img, total, get;
    private String descriptionText, daysLeft, need, CampaignName;


    public ImgAndText(int img, String descriptionText, String CampaignName, String daysLeft, String need,
                      int total, int get) {
        this.img = img;
        this.descriptionText = descriptionText;
        this.daysLeft = daysLeft;
        this.need = need;
        this.CampaignName = CampaignName;
        this.total = total;
        this.get = get;

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
}
