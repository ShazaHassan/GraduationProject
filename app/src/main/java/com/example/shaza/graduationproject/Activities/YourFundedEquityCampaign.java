package com.example.shaza.graduationproject.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.AdapterForShowEquityCampaign;
import com.example.shaza.graduationproject.Database.Table.CampaignType;
import com.example.shaza.graduationproject.Database.Table.EquityCampaign;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourFundedEquityCampaign extends AppCompatActivity {


    private FirebaseUser currentUser;
    private DatabaseReference userTable, equityTable;
    private ArrayList<EquityCampaign> equityCampaigns = new ArrayList<>();
    private TextView noCamps;
    private ListView listOfEquityCamps;
    private CampaignType campaignType;
    private AdapterForShowEquityCampaign adapter;
    private String userID, campID, campType;
    private EquityCampaign campaign;
    private ArrayList<String> campIDs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_equity);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        equityTable = FirebaseDatabase.getInstance().getReference().child("Equity Campaign");
        noCamps = findViewById(R.id.no_camp_text_view);
        listOfEquityCamps = findViewById(R.id.list_to_show_reward_camp);
        userID = currentUser.getUid();
        getDataFromDB();
    }

    private void getDataFromDB() {
        userTable.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("FundedCampaign")) {
                    userTable.child(userID).child("FundedCampaign").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                campaignType = snapshot.getValue(CampaignType.class);
                                campType = campaignType.getType();
                                Log.v("camtype", campType);
                                if (campType.equals("Equity")) {
                                    campID = campaignType.getID();
                                    campIDs.add(campID);
                                    Log.v("camid", campID);
                                }
                            }
                            if (campIDs.size() > 0) {
                                noCamps.setVisibility(View.GONE);
                                listOfEquityCamps.setVisibility(View.VISIBLE);
                                getEquityCamps(campIDs);
                            }
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

    private void getEquityCamps(final ArrayList<String> campIDs) {
        equityTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    campaign = snapshot.getValue(EquityCampaign.class);
                    for (String id : campIDs) {
                        if (campaign.getIDCamp().equals(id))
                            equityCampaigns.add(campaign);
                    }

                }
                adapter = new AdapterForShowEquityCampaign(YourFundedEquityCampaign.this, equityCampaigns, R.color.gray);
                listOfEquityCamps.setAdapter(adapter);
                listOfEquityCamps.setVisibility(View.VISIBLE);
                noCamps.setVisibility(View.GONE);
                Log.v("camsizecamp", Integer.toString(equityCampaigns.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
