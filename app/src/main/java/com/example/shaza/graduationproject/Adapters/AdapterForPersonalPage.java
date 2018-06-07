package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shaza.graduationproject.Fragments.Profile;
import com.example.shaza.graduationproject.Fragments.Your_campaign;
import com.example.shaza.graduationproject.Fragments.Your_funded_campaign;
import com.example.shaza.graduationproject.R;

public class AdapterForPersonalPage extends FragmentPagerAdapter {
    private Context context;

    public AdapterForPersonalPage(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Profile();
        } else if (position == 1) {
            return new Your_campaign();
        } else {
            return new Your_funded_campaign();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.profile_page);
        } else if (position == 1) {
            return context.getString(R.string.your_campaign);
        } else {
            return context.getString(R.string.funded_campaign);
        }

    }
}
