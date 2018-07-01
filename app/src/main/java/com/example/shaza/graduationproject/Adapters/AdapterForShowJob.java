package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Activities.JobInfoCreator;
import com.example.shaza.graduationproject.Activities.JobInfoForApplied;
import com.example.shaza.graduationproject.Database.Table.Job;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ShazaHassan on 17-Feb-18.
 */

public class AdapterForShowJob extends ArrayAdapter<Job> {

    private Context context;
    private int colorResource;
    private ArrayList<Job> jobs;

    public AdapterForShowJob(Context context, ArrayList<Job> jobs) {
        super(context, 0, jobs);
        this.context = context;
        this.jobs = jobs;
    }

    public AdapterForShowJob(Context context, ArrayList<Job> jobs, int colorResource) {
        super(context, 0, jobs);
        this.context = context;
        this.jobs = jobs;
        this.colorResource = colorResource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.job_for_short_view, parent, false);
        }
        final Job job = getItem(position);
        String idUserDB = null;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            idUserDB = currentUser.getUid();
        }
        ImageView myImage = listItemView
                .findViewById(R.id.logo_of_company_shortView);
        String imgURL = job.getImageURL();
        Picasso.get().load(imgURL).into(myImage);
        TextView description = listItemView
                .findViewById(R.id.description_for_position_shortView);
        description.setText(job.getDescription());

        TextView cat = listItemView.findViewById(R.id.job_category_shortView);
        cat.setText(job.getCategory());

        TextView campaignName = listItemView.findViewById(R.id.company_name_shortView);
        campaignName.setText(job.getName());

        View setColorBackground = listItemView.findViewById(R.id.short_view_to_show_job);
        int color = ContextCompat.getColor(getContext(), colorResource);
        if (colorResource == R.color.gray) {
            int darkBlue = ContextCompat.getColor(getContext(), R.color.darkBlue);
            campaignName.setTextColor(darkBlue);
            description.setTextColor(darkBlue);
            cat.setTextColor(darkBlue);
        }
        setColorBackground.setBackgroundColor(color);

        if (currentUser == null || !idUserDB.equals(job.getIdCreator())) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(context, JobInfoForApplied.class);
                    detailsPage.putExtra("id", job.getIdJob());
                    context.startActivity(detailsPage);
                }
            });

        } else if (idUserDB.equals(job.getIdCreator())) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(context, JobInfoCreator.class);
                    detailsPage.putExtra("id", job.getIdJob());
                    context.startActivity(detailsPage);
                }
            });
        }

        return listItemView;
    }
}
