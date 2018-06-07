package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaza.graduationproject.R;

public class Your_campaign extends android.support.v4.app.Fragment {
    public Your_campaign() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.your_campaign, container, false);
        return rootView;
    }
}
