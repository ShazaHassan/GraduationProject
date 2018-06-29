package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shaza.graduationproject.Fragments.EndingSoonEquityCampaign;
import com.example.shaza.graduationproject.Fragments.MostFundEquityCampaign;
import com.example.shaza.graduationproject.Fragments.NewestEquityCampaign;
import com.example.shaza.graduationproject.Fragments.PopularEquityCampaign;
import com.example.shaza.graduationproject.Fragments.SuccessEquityCampaign;
import com.example.shaza.graduationproject.R;

/**
 * Created by Shaza Hassan on 24-Jan-18.
 */

public class PageAdapterForEquityCampaign extends FragmentPagerAdapter {

    private Context context;

    public PageAdapterForEquityCampaign(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PopularEquityCampaign();
        } else if (position == 1) {
            return new NewestEquityCampaign();
        } else if (position == 2) {
            return new EndingSoonEquityCampaign();
        } else if (position == 3) {
            return new MostFundEquityCampaign();
        } else {
            return new SuccessEquityCampaign();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.popular_page);
        } else if (position == 1) {
            return context.getString(R.string.newest_page);
        } else if (position == 2) {
            return context.getString(R.string.ending_soon_page);
        } else if (position == 3) {
            return context.getString(R.string.most_fund_page);
        } else {
            return "Successful Camp";
        }

    }
}
