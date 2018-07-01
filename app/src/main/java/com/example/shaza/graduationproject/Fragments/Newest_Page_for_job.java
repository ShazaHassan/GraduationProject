package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.AdapterForShowJob;
import com.example.shaza.graduationproject.Database.Table.Job;
import com.example.shaza.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ShazaHassan on 17-Feb-18.
 */

public class Newest_Page_for_job extends Fragment {


    private View rootView;
    private TextView noJobToShow;
    private ListView list;
    private DatabaseReference jobTable;
    private AdapterForShowJob adapter;
    private ArrayList<Job> jobs = new ArrayList<>();
    private Date startDate, currentDate;
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private long diff, seconds, minutes, hours, days;
    private String sDate;
    private Job job;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_job, container, false);
        noJobToShow = rootView.findViewById(R.id.no_job_text_view);
        list = rootView.findViewById(R.id.list);
        jobTable = FirebaseDatabase.getInstance().getReference().child("Job");
        jobTable.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    job = snapshot.getValue(Job.class);
                    sDate = job.getStartDate();
                    try {
                        c.setTime(dateFormat.parse(sDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    startDate = c.getTime();

                    currentDate = Calendar.getInstance().getTime();
                    diff = currentDate.getTime() - startDate.getTime();
                    seconds = diff / 1000;
                    minutes = seconds / 60;
                    hours = minutes / 60;
                    days = hours / 24;
                    if (days < 8) {
                        jobs.add(snapshot.getValue(Job.class));
                    }
                }
                if (jobs.size() != 0) {
                    Log.v("popular", "camps");
                    noJobToShow.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    adapter = new AdapterForShowJob(getActivity(), jobs, R.color.lightBlue);
                    list.setAdapter(adapter);
                } else {
                    Log.v("popular", "no camp");
                    list.setVisibility(View.GONE);
                    noJobToShow.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
