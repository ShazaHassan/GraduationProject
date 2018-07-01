package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class CategoryPageReward extends AppCompatActivity {

    String category, type;
    TextView noCamps;
    ListView listView;
    private DatabaseReference campTable;
    private ArrayList<RewardCampaign> rewardCampaigns = new ArrayList<>();
    private AdapterForShowRewardCampaign rewardCampaign;
    private RewardCampaign reward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        setTitle(category);
        Log.v("size", category);
        noCamps = findViewById(R.id.no_camp_text_view);
        listView = findViewById(R.id.list_to_show_reward_camp);
        campTable = FirebaseDatabase.getInstance().getReference().child("Reward Campaign");
        getDataFromDBReward();

    }

    private void getDataFromDBReward() {
        campTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.v("size", "elements");
                    reward = snapshot.getValue(RewardCampaign.class);
                    if (category.equals("All project")) {
                        Log.v("size", "enter here");
                        rewardCampaigns.add(reward);
                    } else if (category.equals(reward.getCategory())) {
                        Log.v("size", "special cat");
                        rewardCampaigns.add(reward);
                    }
                }
                Log.v("size", Integer.toString(rewardCampaigns.size()));
                if (rewardCampaigns.size() > 0) {
                    rewardCampaign = new AdapterForShowRewardCampaign(CategoryPageReward.this, rewardCampaigns, R.color.gray);
                    noCamps.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(rewardCampaign);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
