package com.example.shaza.graduationproject.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shaza.graduationproject.Activities.YourRewardCampaign;
import com.example.shaza.graduationproject.R;

public class Your_campaign extends android.support.v4.app.Fragment {

    private View rootView;
    private Button yourRewardCampaign;

    public Your_campaign() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.choose_type_to_see_your_campaign, container, false);
        yourRewardCampaign = rootView.findViewById(R.id.see_your_reward_camp);
        yourRewardCampaign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), YourRewardCampaign.class));
            }
        });
        return rootView;
    }
}
