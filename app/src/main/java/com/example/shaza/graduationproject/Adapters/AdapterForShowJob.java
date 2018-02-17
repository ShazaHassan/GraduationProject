package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.TemplateForAdapter.JobTemplate;

import java.util.ArrayList;

/**
 * Created by ShazaHassan on 17-Feb-18.
 */

public class AdapterForShowJob extends ArrayAdapter<JobTemplate> {
    public AdapterForShowJob(Context context, ArrayList<JobTemplate> jobTemplates) {
        super(context, 0, jobTemplates);
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
        JobTemplate jobTemplate = getItem(position);

        ImageView myImage = (ImageView) listItemView
                .findViewById(R.id.logo_of_company);
        myImage.setImageResource(jobTemplate.getImage());

        TextView description = listItemView
                .findViewById(R.id.description_for_position);
        description.setText(jobTemplate.getDescriptionOfPosition());

        TextView campaignName = listItemView.findViewById(R.id.company_name);
        campaignName.setText(jobTemplate.getCompanyName());

        return listItemView;
    }
}
