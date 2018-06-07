package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shaza.graduationproject.R;

public class Profile extends android.support.v4.app.Fragment {
    EditText username, email, birthday, country;
    TextView userName, eMail, birth, coun;
    Button done;

    public Profile() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_page, container, false);
        return rootView;
    }

}
