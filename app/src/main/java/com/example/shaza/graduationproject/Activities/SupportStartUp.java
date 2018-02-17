package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.shaza.graduationproject.R;

public class SupportStartUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_start_up);
        Button rewardButton = findViewById(R.id.reward_button);
        rewardButton.setText("Go To Reward Campaigns");
        Button equityButton = findViewById(R.id.equity_button);
        equityButton.setText("Go To Equity Campaigns");

    }


    public void goToRewardCampaign(View view) {
        Intent rewardPage = new Intent(this, Reward_campaign_Home_page.class);
        startActivity(rewardPage);
    }

    public void openExpertChat(View view) {
        Intent expertChat = new Intent(this, TalkToExpert.class);
        startActivity(expertChat);
    }
    public void  goToEquityCampaign(View view){
        Intent equityPage = new Intent(this,Equity_campaign_Home_page.class);
        startActivity(equityPage);
    }
}
