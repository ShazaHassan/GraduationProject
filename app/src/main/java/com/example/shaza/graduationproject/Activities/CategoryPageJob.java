package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;

public class CategoryPageJob extends AppCompatActivity {

    private TextView noJob;
    private ListView list;
    private String category;
    private DatabaseReference jobTable;
    private ArrayList<Job> jobs = new ArrayList<>();
    private AdapterForShowJob showJob;
    private Job job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_job);
        noJob = findViewById(R.id.no_job_text_view);
        list = findViewById(R.id.list);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        setTitle(category);
        Log.v("size", category);
        jobTable = FirebaseDatabase.getInstance().getReference().child("Job");
        getDataFromDBJob();
    }

    private void getDataFromDBJob() {
        jobTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.v("size", "elements");
                    job = snapshot.getValue(Job.class);
                    if (category.equals("All types")) {
                        Log.v("size", "enter here");
                        jobs.add(job);
                    } else if (category.equals(job.getCategory())) {
                        Log.v("size", "special cat");
                        jobs.add(job);
                    }
                }
                Log.v("size", Integer.toString(jobs.size()));
                if (jobs.size() > 0) {
                    showJob = new AdapterForShowJob(CategoryPageJob.this, jobs, R.color.gray);
                    noJob.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    list.setAdapter(showJob);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
