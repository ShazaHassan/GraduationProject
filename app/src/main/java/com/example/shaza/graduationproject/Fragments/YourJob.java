package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shaza.graduationproject.Adapters.AdapterForShowJob;
import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.TemplateForAdapter.JobTemplate;

import java.util.ArrayList;

import static com.example.shaza.graduationproject.Fragments.Home_page_for_job.companyName;
import static com.example.shaza.graduationproject.Fragments.Home_page_for_job.descriptionForPosition;
import static com.example.shaza.graduationproject.Fragments.Home_page_for_job.logo;

public class YourJob extends Fragment {

    private ArrayList<JobTemplate> jobTemplates = new ArrayList<>();

    public YourJob() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_campaign, container, false);
        jobTemplates.clear();
        for (int i = 1; i < logo.length; i++)
            jobTemplates.add(new JobTemplate(logo[i], companyName[i], descriptionForPosition[i]));
        AdapterForShowJob adapter = new AdapterForShowJob(getActivity(), jobTemplates);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        return rootView;
    }
}
