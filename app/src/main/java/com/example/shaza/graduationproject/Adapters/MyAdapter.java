package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shaza.graduationproject.TemplateForAdapter.ImgAndText;
import com.example.shaza.graduationproject.R;

import java.util.ArrayList;

/**
 * Created by Shaza Hassan on 15-Dec-17.
 */

public class MyAdapter extends PagerAdapter {

    private ArrayList<ImgAndText> imgAndTexts;
    private LayoutInflater inflater;
    private Context context;
    private int[] img, total, get;
    private String[] texts, daysLeft, need;

    public MyAdapter(Context context, ArrayList<ImgAndText> imgAndTexts, int[] img,
                     String[] texts, String[] daysLeft, String[] need, int[] total, int[] get) {
        this.context = context;
        this.imgAndTexts = imgAndTexts;
        inflater = LayoutInflater.from(context);
        this.img = img;
        this.texts = texts;
        this.daysLeft = daysLeft;
        this.need = need;
        this.total = total;
        this.get = get;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imgAndTexts.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.campaign_short_view, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image_of_campaign_in_reward_home_page);
        ImgAndText add = imgAndTexts.get(position);
        myImage.setImageResource(add.getImg());

        TextView description = myImageLayout
                .findViewById(R.id.description_for_reward_campaign_home_page);
        description.setText(add.getText());

        TextView campaignName = myImageLayout.findViewById(R.id.campaign_name);
        campaignName.setText(add.getCampaignName());

        TextView daysLeft = myImageLayout.findViewById(R.id.days_left);
        daysLeft.setText(add.getDaysLeft());

        TextView need = myImageLayout.findViewById(R.id.need);
        need.setText("Need " + add.getNeed());

        int percentage = (int) (((float) add.getGet() / (float) add.getTotal()) * 100);
        ProgressBar showPercentage = (ProgressBar) myImageLayout.
                findViewById(R.id.progress_bar_for_reward_campaign_home_page);
        showPercentage.setMax(100);
        showPercentage.setProgress(percentage);

        TextView percentageView = (TextView) myImageLayout.findViewById(R.id.percentage);
        percentageView.setText(percentage + "%");
        view.addView(myImageLayout);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

}
