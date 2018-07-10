package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shaza.graduationproject.Fragments.AppliedJob;
import com.example.shaza.graduationproject.Fragments.Profile;
import com.example.shaza.graduationproject.Fragments.PurchasedProduct;
import com.example.shaza.graduationproject.Fragments.YourJob;
import com.example.shaza.graduationproject.Fragments.YourProduct;
import com.example.shaza.graduationproject.Fragments.YourQuestions;
import com.example.shaza.graduationproject.Fragments.Your_campaign;
import com.example.shaza.graduationproject.Fragments.Your_funded_campaign;
import com.example.shaza.graduationproject.R;

public class PageAdapterForPersonalPage extends FragmentPagerAdapter {
    private Context context;

    public PageAdapterForPersonalPage(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Profile();
        } else if (position == 1) {
            return new Your_campaign();
        } else if (position == 2) {
            return new Your_funded_campaign();
        } else if (position == 3) {
            return new YourJob();
        } else if (position == 4) {
            return new AppliedJob();
        } else if (position == 5) {
            return new YourProduct();
        } else if (position == 6) {
            return new PurchasedProduct();
        } else {
            return new YourQuestions();
        }

    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.profile_page);
        } else if (position == 1) {
            return context.getString(R.string.your_campaign);
        } else if (position == 2) {
            return context.getString(R.string.funded_campaign);
        } else if (position == 3) {
            return context.getString(R.string.your_job);
        } else if (position == 4) {
            return context.getString(R.string.applied_job);
        } else if (position == 5) {
            return context.getString(R.string.your_product);
        } else if (position == 6) {
            return context.getString(R.string.purchased_product);
        } else {
            return context.getString(R.string.your_quest);
        }

    }
}
