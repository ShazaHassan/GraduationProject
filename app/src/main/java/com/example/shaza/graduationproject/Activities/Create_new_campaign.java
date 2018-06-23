package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Shaza Hassan on 25-Jan-18.
 */

public class Create_new_campaign extends AppCompatActivity {

    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_start_up);
        user = FirebaseAuth.getInstance().getCurrentUser();

    }


    //button action
    public void goToRewardCampaign(View view) {
        if (user != null) {
            Intent rewardPage = new Intent(this, New_campaign_reward.class);
            startActivity(rewardPage);
        } else {
            Intent loginPage = new Intent(this, Login.class);
            startActivity(loginPage);
        }

    }

    public void goToEquityCampaign(View view) {
        if (user != null) {
            Intent equityPage = new Intent(this, New_Campaign_Equity.class);
            startActivity(equityPage);
        } else {
            startActivity(new Intent(this, Login.class));
        }

    }

}
