package com.example.shaza.graduationproject.TemplateForAdapter;

/**
 * Created by ShazaHassan on 17-Feb-18.
 */

public class JobTemplate {
    private int image;
    private String companyName, descriptionOfPosition;

    public JobTemplate(int image, String companyName, String descriptionOfPosition) {
        this.image = image;
        this.companyName = companyName;
        this.descriptionOfPosition = descriptionOfPosition;
    }

    public int getImage() {
        return image;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDescriptionOfPosition() {
        return descriptionOfPosition;
    }
}
