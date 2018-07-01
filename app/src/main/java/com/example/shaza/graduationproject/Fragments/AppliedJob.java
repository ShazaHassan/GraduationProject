package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AppliedJob extends Fragment {

    private FirebaseUser currentUser;
    private DatabaseReference userTable, jobTable;
    private String idUserDB, idJobDB;
    private ArrayList<String> jobIDS = new ArrayList<>();
    private Job job;
    private ArrayList<Job> jobs = new ArrayList<>();
    private AdapterForShowJob adapter;
    private ListView list;
    private TextView noJobToShow;

    public AppliedJob() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_job, container, false);

        list = rootView.findViewById(R.id.list);
        noJobToShow = rootView.findViewById(R.id.no_job_text_view);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        idUserDB = currentUser.getUid();
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        jobTable = FirebaseDatabase.getInstance().getReference().child("Job");
        userTable.child(idUserDB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Applied Job")) {
                    getIdsForProd();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }

    private void getIdsForProd() {
        userTable.child(idUserDB).child("Applied Job").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    idJobDB = snapshot.getValue().toString();
                    jobIDS.add(idJobDB);
                }
                if (jobIDS.size() > 0) {
                    getProducts(jobIDS);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getProducts(final ArrayList<String> jobIDS) {
        jobTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    job = snapshot.getValue(Job.class);
                    idJobDB = job.getIdJob();
                    for (String id : jobIDS) {
                        if (idJobDB.equals(id)) {
                            jobs.add(job);
                        }
                    }

                }
                if (jobs.size() > 0) {
                    adapter = new AdapterForShowJob(getContext(), jobs, R.color.gray);
                    list.setAdapter(adapter);
                    noJobToShow.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    Log.v("camsizecampfun", Integer.toString(jobs.size()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
