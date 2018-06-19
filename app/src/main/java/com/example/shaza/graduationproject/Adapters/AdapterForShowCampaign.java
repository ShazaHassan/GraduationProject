package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shaza.graduationproject.Activities.Campaign_info_for_funded;
import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.TemplateForAdapter.ImgAndText;

import java.util.ArrayList;

/**
 * Created by Shaza Hassan on 24-Jan-18.
 */

public class AdapterForShowCampaign extends ArrayAdapter<ImgAndText> {
    ArrayList<ImgAndText> imgAndTexts;
    private Context context;
    private int colorResource;

    public AdapterForShowCampaign(Context context, ArrayList<ImgAndText> imgAndTexts) {
        super(context, 0, imgAndTexts);
        this.imgAndTexts = imgAndTexts;
        this.context = context;
        this.colorResource = R.color.white;
    }

    public AdapterForShowCampaign(Context context, ArrayList<ImgAndText> imgAndTexts, int colorResource) {
        super(context, 0, imgAndTexts);
        this.imgAndTexts = imgAndTexts;
        this.context = context;
        this.colorResource = colorResource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.campaign_short_view, parent, false);
        }
        ImgAndText imgAndText = getItem(position);
        ImageView myImage = (ImageView) listItemView
                .findViewById(R.id.image_of_campaign_in_reward_home_page);
        myImage.setImageResource(imgAndText.getImg());

        TextView description = listItemView
                .findViewById(R.id.description_for_reward_campaign_home_page);
        description.setMinLines(2);
        description.setText(imgAndText.getText());

        TextView campaignName = listItemView.findViewById(R.id.campaign_name);
        campaignName.setText(imgAndText.getCampaignName());

        TextView daysLeft = listItemView.findViewById(R.id.days_left);
        daysLeft.setText(imgAndText.getDaysLeft());

        TextView need = listItemView.findViewById(R.id.need);
        need.setText("Need " + imgAndText.getNeed());

        int percentage = (int) (((float) imgAndText.getGet() / (float) imgAndText.getTotal()) * 100);
        ProgressBar showPercentage = (ProgressBar) listItemView.
                findViewById(R.id.progress_bar_for_reward_campaign_home_page);
        showPercentage.setMax(100);
        showPercentage.setProgress(percentage);

        TextView percentageView = (TextView) listItemView.findViewById(R.id.percentage);
        percentageView.setText(percentage + "%");

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsPage = new Intent(context, Campaign_info_for_funded.class);
                detailsPage.putExtra("id", position);
                context.startActivity(detailsPage);
            }
        });
        View setColorBackground = listItemView.findViewById(R.id.short_view_to_show_camp);
        int color = ContextCompat.getColor(getContext(), colorResource);
        setColorBackground.setBackgroundColor(color);
        return listItemView;
    }
}
