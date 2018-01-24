package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shaza.graduationproject.Fragments.Ending_soon_campaign;
import com.example.shaza.graduationproject.Fragments.MostFundCampaign;
import com.example.shaza.graduationproject.Fragments.NewestCampaign;
import com.example.shaza.graduationproject.Fragments.PopularCampaign;
import com.example.shaza.graduationproject.R;

/**
 * Created by Shaza Hassan on 24-Jan-18.
 */

public class PageAdapter extends FragmentPagerAdapter {

    private Context context;

    public PageAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PopularCampaign();
        } else if (position == 1) {
            return new NewestCampaign();
        } else if (position == 2) {
            return new Ending_soon_campaign();
        } else {
            return new MostFundCampaign();
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
