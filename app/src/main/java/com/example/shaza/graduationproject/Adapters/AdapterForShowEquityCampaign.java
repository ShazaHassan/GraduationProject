package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.shaza.graduationproject.Database.Table.EquityCampaign;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Shaza Hassan on 24-Jan-18.
 */

public class AdapterForShowEquityCampaign extends ArrayAdapter<EquityCampaign> {
    ArrayList<EquityCampaign> equityCampaigns = new ArrayList<>();
    Holder holder = null;
    private Context context;
    private int colorResource;
    private Date startDate, currentDate, endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private String cDate, eDate, idCurrentUserDB, idCreatorUserDB;
    private Calendar c = Calendar.getInstance();
    private FirebaseUser currentUser;
    private long diff, seconds, minutes, hours, days;
    private DatabaseReference campaignTable;


    public AdapterForShowEquityCampaign(Context context, ArrayList<EquityCampaign> equityCampaigns) {
        super(context, 0, equityCampaigns);
        this.equityCampaigns = equityCampaigns;
        this.context = context;
        this.colorResource = R.color.white;
    }

    public AdapterForShowEquityCampaign(Context context, ArrayList<EquityCampaign> equityCampaigns, int colorResource) {
        super(context, 0, equityCampaigns);
        this.equityCampaigns = equityCampaigns;
        this.context = context;
        this.colorResource = colorResource;
    }

    public AdapterForShowEquityCampaign(Context context, ArrayList<EquityCampaign> equityCampaigns, int colorResource, DatabaseReference campaignTable) {
        super(context, 0, equityCampaigns);
        this.equityCampaigns = equityCampaigns;
        this.context = context;
        this.colorResource = colorResource;
        this.campaignTable = campaignTable;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.campaign_short_view, parent, false);
            holder = new Holder();
            findItem(listItemView);

            listItemView.setTag(holder);
        } else {
            holder = (Holder) listItemView.getTag();
        }
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            idCurrentUserDB = currentUser.getUid();
        } else {
            idCurrentUserDB = null;
        }

        final EquityCampaign equityCampaign = getItem(position);

        calculateDaysLeft(equityCampaign);

        holder.campaignName.setText(equityCampaign.getName());

        String imgURI = equityCampaign.getImgName();
        Picasso.get().load(imgURI).into(holder.campImg);

        holder.campaignDesc.setMinLines(3);
        holder.campaignDesc.setText("Heighlight of campaign is : " + equityCampaign.getHighlight() + "\n" +
                "Executive team: " + equityCampaign.getTeam() + "\n" +
                "Summary about company: " + equityCampaign.getSummary() + "\n" +
                "Processed and Timeline: " + equityCampaign.getTimeline() + "\n" +
                "Market Analysis: " + equityCampaign.getMarket() + "\n" +
                "Investment terms: " + equityCampaign.getInvestTerms() + "\n" +
                "Investor Discussion: " + equityCampaign.getInvestDiscussion() + "\n" +
                "Add offer: " + equityCampaign.getOffers());

        holder.daysLeft.setText(Long.toString(days) + " Days left");

        holder.neededMoney.setText("Need: " + (equityCampaign.getNeededMoney() - equityCampaign.getFundedMoney()) + " $");

        holder.category.setText(equityCampaign.getCategory());

        int percentage = (int) (((equityCampaign.getFundedMoney() * 1.0) / (equityCampaign.getNeededMoney() * 1.0)) * 100);
        holder.percentageBar.setMax(100);
        holder.percentageBar.setProgress(percentage);

        holder.percentageText.setText(percentage + "%");
        if (idCurrentUserDB == null) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(getContext(), Campaign_info_for_funded.class);
                    detailsPage.putExtra("id", equityCampaign.getIDCamp());
                    detailsPage.putExtra("type", "equity");
                    getContext().startActivity(detailsPage);
                }
            });
        } else if (!idCurrentUserDB.equals(equityCampaign.getIDCreator())) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(getContext(), Campaign_info_for_funded.class);
                    detailsPage.putExtra("id", equityCampaign.getIDCamp());
                    detailsPage.putExtra("type", "equity");
                    getContext().startActivity(detailsPage);
                }
            });
        } else if (idCurrentUserDB.equals(equityCampaign.getIDCreator())) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(getContext(), Campaign_info_for_creator.class);
                    detailsPage.putExtra("id", equityCampaign.getIDCamp());
                    detailsPage.putExtra("type", "equity");
                    getContext().startActivity(detailsPage);
                }
            });
        }


        View setColorBackground = listItemView.findViewById(R.id.short_view_to_show_camp);
        int color = ContextCompat.getColor(getContext(), colorResource);
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

    private void calculateDaysLeft(EquityCampaign rewardCampaign) {
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
