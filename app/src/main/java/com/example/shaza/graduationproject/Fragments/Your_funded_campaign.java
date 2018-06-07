package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaza.graduationproject.R;

public class Your_funded_campaign extends android.support.v4.app.Fragment {
    public Your_funded_campaign() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.funded_campaign, container, false);
        return rootView;
    }
}
