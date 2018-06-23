package com.example.shaza.graduationproject.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shaza.graduationproject.R;

public class Profile extends android.support.v4.app.Fragment {


    public Profile() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_page, container, false);
        ImageView pp = rootView.findViewById(R.id.personal_img);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_female_user);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        pp.setImageDrawable(roundedBitmapDrawable);
        return rootView;
    }

}
