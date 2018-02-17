package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shaza.graduationproject.Fragments.Home_page_for_job;
import com.example.shaza.graduationproject.Fragments.Most_seller_product;
import com.example.shaza.graduationproject.Fragments.Newest_Page_for_job;
import com.example.shaza.graduationproject.Fragments.Newest_product;
import com.example.shaza.graduationproject.R;

/**
 * Created by ShazaHassan on 17-Feb-18.
 */

public class PageAdapterForJob extends FragmentPagerAdapter {

    private Context context;

    public PageAdapterForJob(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Home_page_for_job();
        } else {
            return new Newest_Page_for_job();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.home_page_job);
        } else {
            return context.getString(R.string.new_page_job);
        }

    }
}
