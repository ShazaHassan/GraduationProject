package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.TemplateForAdapter.ImgAndText;

import java.util.ArrayList;

public class Your_funded_campaign extends android.support.v4.app.Fragment {

    private ArrayList<ImgAndText> array = new ArrayList<ImgAndText>();

    public Your_funded_campaign() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_campaign, container, false);
        array.clear();
//        for (int i = 0; i < 1; i++)
//            array.add(new ImgAndText(img[i], texts[i], campaignName[i], noOfDays[i], need[i], total[i], get[i], category[i]));
//        AdapterForShowCampaign adapter = new AdapterForShowCampaign(getActivity(), array, R.color.darkBlue);
//        ListView listView = rootView.findViewById(R.id.list);
//        listView.setAdapter(adapter);
        return rootView;
    }
}
