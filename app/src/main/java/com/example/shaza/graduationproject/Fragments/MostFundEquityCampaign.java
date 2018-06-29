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

import com.example.shaza.graduationproject.Adapters.AdapterForShowEquityCampaign;
import com.example.shaza.graduationproject.Database.Table.EquityCampaign;
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

public class MostFundEquityCampaign extends Fragment {

    View rootView;
    private ArrayList<EquityCampaign> campaigns = new ArrayList<>();
    private DatabaseReference campaignTable;
    private EquityCampaign campaign;
    private TextView noCampsTextView;
    private ListView listView;
    private AdapterForShowEquityCampaign adapter;

    private Date currentDate, endDate, startDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private String eDate, sDate;
    private Calendar c = Calendar.getInstance(), d = Calendar.getInstance();
    private long diff, seconds, minutes, hours, days;

    public MostFundEquityCampaign() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        campaignTable = FirebaseDatabase.getInstance().getReference().child("Equity Campaign");
        rootView = inflater.inflate(R.layout.list_campaign, container, false);
        noCampsTextView = rootView.findViewById(R.id.no_camp_text_view);
        listView = rootView.findViewById(R.id.list);

        Log.v("popular", "get camp");

        campaignTable.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("popular", "search for camp");
                campaigns.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    campaign = snapshot.getValue(EquityCampaign.class);
                    sDate = campaign.getStartDate();
                    eDate = campaign.getEndDate();
                    try {
                        c.setTime(dateFormat.parse(eDate));
                        d.setTime(dateFormat.parse(sDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    endDate = c.getTime();
                    currentDate = Calendar.getInstance().getTime();
                    diff = endDate.getTime() - currentDate.getTime();
                    seconds = diff / 1000;
                    minutes = seconds / 60;
                    hours = minutes / 60;
                    days = hours / 24;
                    Log.v("day", Long.toString(days));
                    if (days > 0) {
                        if (campaign.getNoOfFunded() >= 5) {
                            campaigns.add(campaign);
                        }
                    }
                }
                Log.v("popular", String.valueOf(campaigns.size()));
                if (campaigns.size() != 0) {
                    Log.v("popular", "camps");
                    noCampsTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    adapter = new AdapterForShowEquityCampaign(getActivity(), campaigns, R.color.lightYellow);
                    listView.setAdapter(adapter);
                } else {
                    Log.v("popular", "no camp");
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
