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

import java.util.ArrayList;

public class MostFundRewardCampaign extends Fragment {

    View rootView;
    private ArrayList<RewardCampaign> campaigns = new ArrayList<>();
    private DatabaseReference rewardTable;
    private RewardCampaign campaign;
    private TextView noCampsTextView;
    private ListView listView;
    private AdapterForShowRewardCampaign adapter;
    private long noOfFunded;

    public MostFundRewardCampaign() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.list_campaign, container, false);
        noCampsTextView = rootView.findViewById(R.id.no_camp_text_view);
        listView = rootView.findViewById(R.id.list);
        Log.v("popular", "get camp");
        rewardTable = FirebaseDatabase.getInstance().getReference().child("Reward Campaign");
        rewardTable.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.v("popular", "search for camp");
                campaigns.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    campaign = snapshot.getValue(RewardCampaign.class);
                    noOfFunded = campaign.getNoOfFunded();

                    if (noOfFunded >= 5) {
                        campaigns.add(campaign);
                    }
                    Log.v("popular", dataSnapshot.getChildren().toString());
                }
                if (campaigns.size() != 0) {
                    Log.v("popular", "camps");
                    noCampsTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    adapter = new AdapterForShowRewardCampaign(getActivity(), campaigns, R.color.darkBlue);
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
