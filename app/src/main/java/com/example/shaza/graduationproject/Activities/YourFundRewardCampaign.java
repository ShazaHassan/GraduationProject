package com.example.shaza.graduationproject.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.AdapterForShowRewardCampaign;
import com.example.shaza.graduationproject.Database.Table.CampaignType;
import com.example.shaza.graduationproject.Database.Table.RewardCampaign;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourFundRewardCampaign extends AppCompatActivity {

    private FirebaseUser currentUser;
    private DatabaseReference userTable, rewardTable;
    private ArrayList<RewardCampaign> rewardCampaigns = new ArrayList<>();
    private TextView noCamps;
    private ListView listOfRewardCamps;
    private CampaignType campaignType;
    private AdapterForShowRewardCampaign adapter;
    private String userID, campID, campType;
    private RewardCampaign campaign;
    private ArrayList<String> campIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_reward_campaign);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        rewardTable = FirebaseDatabase.getInstance().getReference().child("Reward Campaign");
        noCamps = findViewById(R.id.no_camp_text_view);
        listOfRewardCamps = findViewById(R.id.list_to_show_reward_camp);
        userID = currentUser.getUid();
        getDataFromDB();
    }

    private void getDataFromDB() {
        userTable.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("FundedCampaign")) {
                    noCamps.setVisibility(View.GONE);
                    listOfRewardCamps.setVisibility(View.VISIBLE);
                    userTable.child(userID).child("FundedCampaign").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                campaignType = snapshot.getValue(CampaignType.class);
                                campType = campaignType.getType();
                                Log.v("camtype", campType);
                                if (campType.equals("Reward")) {
                                    campID = campaignType.getID();
                                    campIDs.add(campID);
                                    Log.v("camid", campID);
                                }
                            }
                            getRewardCamps(campIDs);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getRewardCamps(final ArrayList<String> campIDs) {
        rewardTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    campaign = snapshot.getValue(RewardCampaign.class);
                    for (String id : campIDs) {
                        if (campaign.getIDCampaign().equals(id))
                            rewardCampaigns.add(campaign);
                    }

                }
                adapter = new AdapterForShowRewardCampaign(YourFundRewardCampaign.this, rewardCampaigns, R.color.gray);
                listOfRewardCamps.setAdapter(adapter);
                listOfRewardCamps.setVisibility(View.VISIBLE);
                noCamps.setVisibility(View.GONE);
                Log.v("camsizecamp", Integer.toString(rewardCampaigns.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
