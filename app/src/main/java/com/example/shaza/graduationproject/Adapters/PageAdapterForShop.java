package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.shaza.graduationproject.Fragments.Ending_soon_campaign;
import com.example.shaza.graduationproject.Fragments.MostFundCampaign;
import com.example.shaza.graduationproject.Fragments.Most_seller_product;
import com.example.shaza.graduationproject.Fragments.NewestCampaign;
import com.example.shaza.graduationproject.Fragments.Newest_product;
import com.example.shaza.graduationproject.Fragments.PopularCampaign;
import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.TemplateForAdapter.ShopForShow;

import java.util.ArrayList;

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
            return new Most_seller_product();
        } else {
            return new Newest_product();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.most_seller);
        } else {
            return context.getString(R.string.newest_product);
        }

    }
}
