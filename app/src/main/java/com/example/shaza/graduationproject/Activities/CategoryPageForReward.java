package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.shaza.graduationproject.R;

public class CategoryPageForReward extends AppCompatActivity {
    int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        Intent intent = getIntent();
        pos = intent.getExtras().getInt("category");
        setActivityTitleName();
    }

    private void setActivityTitleName() {
        if (pos == 0) {
            setTitle("All Campaigns");
        } else if (pos == 1) {
            setTitle("Art");
        } else if (pos == 2) {
            setTitle("Crafts");
        } else if (pos == 3) {
            setTitle("Design");
        } else if (pos == 4) {
            setTitle("Fashion");
        } else if (pos == 5) {
            setTitle("Film and Video");
        } else if (pos == 6) {
            setTitle("Games");
        } else if (pos == 7) {
            setTitle("Health");
        } else if (pos == 8) {
            setTitle("Productivity");
        } else if (pos == 9) {
            setTitle("Food");
        }
    }
}
