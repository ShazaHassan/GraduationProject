package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.shaza.graduationproject.R;

/**
 * Created by Shaza Hassan on 25-Jan-18.
 */

public class Create_new_campaign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_start_up);
    }

    //button action
    public void goToRewardCampaign(View view) {
        Intent rewardPage = new Intent(this, New_campaign_reward.class);
        startActivity(rewardPage);
    }

}
