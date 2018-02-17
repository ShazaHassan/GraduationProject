package com.example.shaza.graduationproject.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shaza.graduationproject.Activities.Campaign_info_for_creator;
import com.example.shaza.graduationproject.Activities.Home_Page;
import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.TemplateForAdapter.ImgAndText;

import java.util.ArrayList;

/**
 * Created by Shaza Hassan on 15-Dec-17.
 */

public class MyAdapter extends PagerAdapter {

    private ArrayList<ImgAndText> imgAndTexts;
    private LayoutInflater inflater;
    private Context context;
    private int color = R.color.white;


    public MyAdapter(Context context, ArrayList<ImgAndText> imgAndTexts) {
        this.context = context;
        this.imgAndTexts = imgAndTexts;
        inflater = LayoutInflater.from(context);

    }

    public MyAdapter(Context context, ArrayList<ImgAndText> imgAndTexts, int color) {
        this.context = context;
        this.imgAndTexts = imgAndTexts;
        inflater = LayoutInflater.from(context);
        this.color = color;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imgAndTexts.size();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = inflater.inflate(R.layout.campaign_short_view, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image_of_campaign_in_reward_home_page);
        ImgAndText add = imgAndTexts.get(position);
        myImage.setImageResource(add.getImg());

        TextView description = myImageLayout
                .findViewById(R.id.description_for_reward_campaign_home_page);
        description.setTextColor(context.getResources().getColor(color));
        description.setText(add.getText());

        TextView campaignName = myImageLayout.findViewById(R.id.campaign_name);
        campaignName.setTextColor(context.getResources().getColor(color));
        campaignName.setText(add.getCampaignName());

        TextView daysLeft = myImageLayout.findViewById(R.id.days_left);
        daysLeft.setTextColor(context.getResources().getColor(color));
        daysLeft.setText(add.getDaysLeft());

        TextView need = myImageLayout.findViewById(R.id.need);
        need.setTextColor(context.getResources().getColor(color));
        need.setText("Need " + add.getNeed());

        int percentage = (int) (((float) add.getGet() / (float) add.getTotal()) * 100);
        ProgressBar showPercentage = (ProgressBar) myImageLayout.
                findViewById(R.id.progress_bar_for_reward_campaign_home_page);
        showPercentage.setMax(100);
        showPercentage.setProgress(percentage);

        TextView percentageView = (TextView) myImageLayout.findViewById(R.id.percentage);
        percentageView.setTextColor(context.getResources().getColor(color));
        percentageView.setText(percentage + "%");
        view.addView(myImageLayout);

        myImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsPage = new Intent(context, Campaign_info_for_creator.class);
                detailsPage.putExtra("id", position);
                context.startActivity(detailsPage);
            }
        });

        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
