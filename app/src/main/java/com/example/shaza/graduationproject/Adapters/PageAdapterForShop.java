package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shaza.graduationproject.Fragments.Home_page_for_shop;
import com.example.shaza.graduationproject.Fragments.Most_seller_product;
import com.example.shaza.graduationproject.Fragments.Newest_product;
import com.example.shaza.graduationproject.R;

/**
 * Created by Shaza Hassan on 28-Jan-18.
 */

public class PageAdapterForShop extends FragmentPagerAdapter {

    private Context context;

    public PageAdapterForShop(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Home_page_for_shop();
        } else if (position == 1) {
            return new Most_seller_product();
        } else {
            return new Newest_product();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.home_page_job);
        } else if (position == 1) {
            return context.getString(R.string.most_seller);
        } else {
            return context.getString(R.string.newest_product);
        }

    }
}
