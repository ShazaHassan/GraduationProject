package com.example.shaza.graduationproject.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shaza.graduationproject.Activities.Campaign_info_for_creator;
import com.example.shaza.graduationproject.Activities.Campaign_info_for_funded;
import com.example.shaza.graduationproject.Activities.Reward_campaign_Home_page;
import com.example.shaza.graduationproject.Database.Table.RewardCampaign;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PagerAdapterForShowRewardCampaign extends PagerAdapter {

    RewardCampaign rewardCampaign;
    private Date currentDate, endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private String cDate, eDate, idCurrentUserDB;
    private Calendar c = Calendar.getInstance();
    private FirebaseUser currentUser;
    private long diff, seconds, minutes, hours, days;
    private ArrayList<RewardCampaign> rewardCampaigns;
    private LayoutInflater inflater;
    private Context context;
    private int color = R.color.white;

    public PagerAdapterForShowRewardCampaign(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public PagerAdapterForShowRewardCampaign(ArrayList<RewardCampaign> rewardCampaigns, Context context, int color) {
        this.rewardCampaigns = rewardCampaigns;
        this.inflater = LayoutInflater.from(context);
        ;
        this.context = context;
        this.color = color;
    }

    public PagerAdapterForShowRewardCampaign(ArrayList<RewardCampaign> rewardCampaigns, Context context) {
        this.rewardCampaigns = rewardCampaigns;
        this.inflater = LayoutInflater.from(context);
        ;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (rewardCampaigns.size() == 0) {
            return 0;
        } else {
            return rewardCampaigns.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View myImageLayout = view;
        rewardCampaign = rewardCampaigns.get(position);
        if (rewardCampaign.getHeighlight().equals("See More")) {
            myImageLayout = inflater.inflate(R.layout.no_more_camp, view, false);
            TextView no = myImageLayout.findViewById(R.id.no_more_camps);
            no.setTextColor(context.getResources().getColor(color));
            myImageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent rewardHome = new Intent(context, Reward_campaign_Home_page.class);
                    context.startActivity(rewardHome);
                }
            });

        } else {


            myImageLayout = LayoutInflater.from(context).inflate(R.layout.campaign_short_view, view, false);


            Log.v("size", Integer.toString(position));
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                idCurrentUserDB = currentUser.getUid();
                Log.v("sizeId", idCurrentUserDB);
            } else {
                idCurrentUserDB = null;
            }

            ImageView myImage = myImageLayout
                    .findViewById(R.id.image_of_campaign_in_reward_home_page);
            String imgURI = rewardCampaign.getCampaign_Image();
            Log.v("imgsize", imgURI);
            Picasso.get().load(imgURI).into(myImage);

            TextView description = myImageLayout
                    .findViewById(R.id.description_for_reward_campaign_home_page);
            description.setTextColor(context.getResources().getColor(color));
            description.setText("Heighlight of campaign is : " + rewardCampaign.getHeighlight() + "\n" +
                    "Vision of this campaign : " + rewardCampaign.getVision() + "\n" +
                    "Offers for funded : " + rewardCampaign.getOffers() + "\n" +
                    "Team helps in campaign : " + rewardCampaign.getHelperTeam());
            Log.v("setDescSize", "true");

            TextView campaignName = myImageLayout.findViewById(R.id.campaign_name);
            campaignName.setTextColor(context.getResources().getColor(color));
            campaignName.setText(rewardCampaign.getName());

            TextView category = myImageLayout.findViewById(R.id.campaign_category_short_view);
            category.setTextColor(context.getResources().getColor(color));
            category.setText(rewardCampaign.getCategory());

            TextView daysLeft = myImageLayout.findViewById(R.id.days_left);
            daysLeft.setTextColor(context.getResources().getColor(color));
            calculateDaysLeft(rewardCampaign);
            daysLeft.setText(Long.toString(days) + " Days left");

            TextView need = myImageLayout.findViewById(R.id.need);
            need.setTextColor(context.getResources().getColor(color));
            need.setText("Need: " + (rewardCampaign.getNeededMoney() - rewardCampaign.getFundedMoney()) + " $");

            int percentage = (int) ((rewardCampaign.getFundedMoney() * 1.0 / rewardCampaign.getNeededMoney()) * 1.0
                    * 100);
            ProgressBar showPercentage = myImageLayout.
                    findViewById(R.id.progress_bar_for_reward_campaign_home_page);
            showPercentage.setMax(100);
            showPercentage.setProgress(percentage);

            TextView percentageView = myImageLayout.findViewById(R.id.percentage);
            percentageView.setTextColor(context.getResources().getColor(color));
            percentageView.setText(percentage + "%");
            String id = rewardCampaign.getIDCampaign();
            Log.v("setAllDatasize", "true");
            myImageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(context, Campaign_info_for_creator.class);
                    detailsPage.putExtra("id", position);
                    context.startActivity(detailsPage);
                }
            });

            if (idCurrentUserDB == null) {
                myImageLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = rewardCampaign.getIDCampaign();
                        Intent detailsPage = new Intent(context, Campaign_info_for_funded.class);
                        detailsPage.putExtra("id", rewardCampaign.getIDCampaign());
                        detailsPage.putExtra("type", "reward");
                        context.startActivity(detailsPage);
                    }
                });
            } else if (!idCurrentUserDB.equals(rewardCampaign.getIDCreator())) {
                myImageLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detailsPage = new Intent(context, Campaign_info_for_funded.class);
                        detailsPage.putExtra("id", rewardCampaign.getIDCampaign());
                        detailsPage.putExtra("type", "reward");
                        context.startActivity(detailsPage);
                    }
                });
            } else if (idCurrentUserDB.equals(rewardCampaign.getIDCreator())) {
                myImageLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detailsPage = new Intent(context, Campaign_info_for_creator.class);
                        detailsPage.putExtra("id", rewardCampaign.getIDCampaign());
                        detailsPage.putExtra("type", "reward");
                        context.startActivity(detailsPage);
                    }
                });
            }
        }
        view.addView(myImageLayout);
        Log.v("pager", rewardCampaign.toString());
        Log.v("pager", myImageLayout.toString());
        Log.v("page", view.toString());
        return myImageLayout;

    }

    private void calculateDaysLeft(RewardCampaign rewardCampaign) {
        currentDate = Calendar.getInstance().getTime();
        cDate = dateFormat.format(currentDate);

        Log.v("Addate", cDate);
        Log.v("Addate", c.getTime().toString());
        eDate = rewardCampaign.getEndDate();
        try {
            c.setTime(dateFormat.parse(eDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        endDate = c.getTime();
        diff = endDate.getTime() - currentDate.getTime();
        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        days = hours / 24;
        Log.v("date", Long.toString(days));
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @SuppressLint("NewApi")
    @Override
    public void finishUpdate(ViewGroup container) {
        // TODO Auto-generated method stub
        super.finishUpdate(container);

    }
}
