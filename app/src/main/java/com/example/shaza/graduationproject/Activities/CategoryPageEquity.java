package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CategoryPageEquity extends AppCompatActivity {

    private ArrayList<EquityCampaign> campaigns = new ArrayList<>();
    private DatabaseReference campTable;
    private EquityCampaign equity;
    private TextView noCamps;
    private ListView listView;
    private AdapterForShowEquityCampaign adapter;
    private Date currentDate, endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private String category;

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
        campTable = FirebaseDatabase.getInstance().getReference().child("Equity Campaign");
        getDataFromDBEquity();
    }

    private void getDataFromDBEquity() {
        campTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.v("size", "elements");
                    equity = snapshot.getValue(EquityCampaign.class);
                    if (category.equals("All project")) {
                        Log.v("size", "enter here");
                        campaigns.add(equity);
                    } else if (category.equals(equity.getCategory())) {
                        Log.v("size", "special cat");
                        campaigns.add(equity);
                    }
                }
                Log.v("size", Integer.toString(campaigns.size()));
                if (campaigns.size() > 0) {
                    adapter = new AdapterForShowEquityCampaign(CategoryPageEquity.this, campaigns, R.color.gray);
                    noCamps.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
