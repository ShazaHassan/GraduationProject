package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.AdapterForShowRewardCampaign;
import com.example.shaza.graduationproject.Database.Table.RewardCampaign;
import com.example.shaza.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MostFundRewardCampaign extends Fragment {

    View rootView;
    private ArrayList<RewardCampaign> campaigns = new ArrayList<>(), campCat = new ArrayList<>(),
            rearrange = new ArrayList<>(), rearrangeCat = new ArrayList<>();
    private DatabaseReference rewardTable;
    private RewardCampaign campaign;
    private TextView noCampsTextView;
    private ListView listView;
    private AdapterForShowRewardCampaign adapter, adapterCat;
    private long noOfFunded;

    private String cDate, eDate;
    private Calendar c = Calendar.getInstance(), e = Calendar.getInstance();
    private long diff, seconds, minutes, hours, days;
    private Date currentDate, endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private int color = R.color.darkBlue;


    public MostFundRewardCampaign() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.list_campaign, container, false);
        noCampsTextView = rootView.findViewById(R.id.no_camp_text_view);
        listView = rootView.findViewById(R.id.list);
        rewardTable = FirebaseDatabase.getInstance().getReference().child("Reward Campaign");
        rewardTable.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                campaigns.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    campaign = snapshot.getValue(RewardCampaign.class);
                    noOfFunded = campaign.getNoOfFunded();
                    eDate = campaign.getEndDate();
                    try {
                        e.setTime(dateFormat.parse(eDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    endDate = e.getTime();
                    currentDate = Calendar.getInstance().getTime();
                    diff = endDate.getTime() - currentDate.getTime();
                    seconds = diff / 1000;
                    minutes = seconds / 60;
                    hours = minutes / 60;
                    days = hours / 24;
                    Log.v("day", Long.toString(days));
                    if (days > 0) {

                        if (noOfFunded >= 5) {
                            campaigns.add(campaign);
                        }
                    }
                }
                if (campaigns.size() != 0) {
                    noCampsTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    for (int i = 0; i < campaigns.size(); i++) {
                        rearrange.add(campaigns.get(campaigns.size() - 1 - i));
                    }
                    adapter = new AdapterForShowRewardCampaign(getActivity(), rearrange, color);
                    listView.setAdapter(adapter);
                } else {
                    listView.setVisibility(View.GONE);
                    noCampsTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
