package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shaza.graduationproject.Activities.Campaign_info_for_creator;
import com.example.shaza.graduationproject.Activities.Campaign_info_for_funded;
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

public class AdapterForShowRewardCampaign extends ArrayAdapter<RewardCampaign> {
    ArrayList<RewardCampaign> rewardCampaigns = new ArrayList<>();
    AdapterForShowEquityCampaign.Holder holder = null;
    private Context context;
    private int colorResource;
    private Date currentDate, endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private String cDate, eDate, idCurrentUserDB;
    private Calendar c = Calendar.getInstance();
    private FirebaseUser currentUser;
    private long diff, seconds, minutes, hours, days;
    private long neededMoney, fundedMoney;


    public AdapterForShowRewardCampaign(Context context, ArrayList<RewardCampaign> rewardCampaigns) {
        super(context, 0, rewardCampaigns);
        this.rewardCampaigns = rewardCampaigns;
        this.context = context;
        this.colorResource = R.color.white;
    }

    public AdapterForShowRewardCampaign(Context context, ArrayList<RewardCampaign> rewardCampaigns, int colorResource) {
        super(context, 0, rewardCampaigns);
        this.rewardCampaigns = rewardCampaigns;
        this.context = context;
        this.colorResource = colorResource;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.campaign_short_view, parent, false);
            holder = new AdapterForShowEquityCampaign.Holder();
            findItem(listItemView);

            listItemView.setTag(holder);
        } else {
            holder = (AdapterForShowEquityCampaign.Holder) listItemView.getTag();
        }
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            idCurrentUserDB = currentUser.getUid();
        } else {
            idCurrentUserDB = null;
        }

        final RewardCampaign rewardCampaign = getItem(position);

        calculateDaysLeft(rewardCampaign);

        holder.campaignName.setText(rewardCampaign.getName());

        String imgURI = rewardCampaign.getCampaign_Image();
        Picasso.get().load(imgURI).into(holder.campImg);

        holder.campaignDesc.setMinLines(3);
        holder.campaignDesc.setText("Heighlight of campaign is : " + rewardCampaign.getHeighlight() + "\n" +
                "Vision of this campaign : " + rewardCampaign.getVision() + "\n" +
                "Offers for funded : " + rewardCampaign.getOffers() + "\n" +
                "Team helps in campaign : " + rewardCampaign.getHelperTeam());
        if (days <= 0) {
            holder.daysLeft.setText("Ended Camp");

        } else {
            holder.daysLeft.setText(Long.toString(days) + " Days left");
        }

        neededMoney = rewardCampaign.getNeededMoney();
        fundedMoney = rewardCampaign.getFundedMoney();
        if (neededMoney <= fundedMoney) {
            holder.neededMoney.setText("Success campaign");

        } else {
            holder.neededMoney.setText("need " + (rewardCampaign.getNeededMoney() - rewardCampaign.getFundedMoney()) + " $");
        }
        holder.category.setText(rewardCampaign.getCategory());

        int percentage = (int) ((rewardCampaign.getFundedMoney() * 1.0 / rewardCampaign.getNeededMoney()) * 1.0
                * 100);
        holder.percentageBar.setMax(100);
        holder.percentageBar.setProgress(percentage);

        holder.percentageText.setText(percentage + "%");
        if (idCurrentUserDB == null) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(getContext(), Campaign_info_for_funded.class);
                    detailsPage.putExtra("id", rewardCampaign.getIDCampaign());
                    detailsPage.putExtra("type", "reward");
                    getContext().startActivity(detailsPage);
                }
            });
        } else if (!idCurrentUserDB.equals(rewardCampaign.getIDCreator())) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(getContext(), Campaign_info_for_funded.class);
                    detailsPage.putExtra("id", rewardCampaign.getIDCampaign());
                    detailsPage.putExtra("type", "reward");
                    getContext().startActivity(detailsPage);
                }
            });
        } else if (idCurrentUserDB.equals(rewardCampaign.getIDCreator())) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(getContext(), Campaign_info_for_creator.class);
                    detailsPage.putExtra("id", rewardCampaign.getIDCampaign());
                    detailsPage.putExtra("type", "reward");
                    getContext().startActivity(detailsPage);
                }
            });
        }


        View setColorBackground = listItemView.findViewById(R.id.short_view_to_show_camp);
        int color = ContextCompat.getColor(getContext(), colorResource);
        if (colorResource == R.color.gray) {
            int darkBlue = ContextCompat.getColor(getContext(), R.color.darkBlue);
            holder.campaignName.setTextColor(darkBlue);
            holder.campaignDesc.setTextColor(darkBlue);
            holder.percentageText.setTextColor(darkBlue);
            holder.category.setTextColor(darkBlue);
            holder.neededMoney.setTextColor(darkBlue);
            holder.daysLeft.setTextColor(darkBlue);
        } else if (colorResource == R.color.lightBlue) {
            holder.percentageBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.darkBlue)));
        }
        setColorBackground.setBackgroundColor(color);
        return listItemView;
    }

    private void findItem(View listItemView) {
        holder.campaignName = listItemView.findViewById(R.id.campaign_name);
        holder.campImg = listItemView.findViewById(R.id.image_of_campaign_in_reward_home_page);
        holder.campaignDesc = listItemView.findViewById(R.id.description_for_reward_campaign_home_page);
        holder.category = listItemView.findViewById(R.id.campaign_category_short_view);
        holder.daysLeft = listItemView.findViewById(R.id.days_left);
        holder.percentageBar = listItemView.findViewById(R.id.progress_bar_for_reward_campaign_home_page);
        holder.percentageText = listItemView.findViewById(R.id.percentage);
        holder.neededMoney = listItemView.findViewById(R.id.need);
    }

    private void calculateDaysLeft(RewardCampaign rewardCampaign) {
        currentDate = Calendar.getInstance().getTime();
        cDate = dateFormat.format(currentDate);

        Log.v("Addate", cDate);
        c.add(Calendar.DATE, 7);
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

    static class Holder {
        TextView campaignName, campaignDesc, daysLeft, neededMoney, category, percentageText;
        ImageView campImg;
        ProgressBar percentageBar;
    }

}
