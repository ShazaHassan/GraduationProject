package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shaza.graduationproject.Fragments.EndingSoonRewardCampaign;
import com.example.shaza.graduationproject.Fragments.MostFundRewardCampaign;
import com.example.shaza.graduationproject.Fragments.NewestRewardCampaign;
import com.example.shaza.graduationproject.Fragments.PopularRewardCampaign;
import com.example.shaza.graduationproject.R;

public class PageAdapterForRewardCampaign extends FragmentPagerAdapter {
    private Context context;

    public PageAdapterForRewardCampaign(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PopularRewardCampaign();
        } else if (position == 1) {
            return new NewestRewardCampaign();
        } else if (position == 2) {
            return new EndingSoonRewardCampaign();
        } else {
            return new MostFundRewardCampaign();
        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.popular_page);
        } else if (position == 1) {
            return context.getString(R.string.newest_page);
        } else if (position == 2) {
            return context.getString(R.string.ending_soon_page);
        } else {
            return context.getString(R.string.most_fund_page);
        }

    }
}
