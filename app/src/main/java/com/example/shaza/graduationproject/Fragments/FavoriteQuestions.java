package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaza.graduationproject.R;

public class FavoriteQuestions extends Fragment {

    public FavoriteQuestions() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.favorite_questions, container, false);
        return rootView;
    }
}

