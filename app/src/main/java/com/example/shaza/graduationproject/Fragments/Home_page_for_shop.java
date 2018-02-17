package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shaza.graduationproject.Activities.Home_Page;
import com.example.shaza.graduationproject.Adapters.AdapterForShowProduct;
import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.TemplateForAdapter.ShopForShow;

import java.util.ArrayList;

/**
 * Created by ShazaHassan on 17-Feb-18.
 */

public class Home_page_for_shop extends Fragment {

    private static final int[] img = {R.drawable.aa, R.drawable.ba148f888900f93996a2e2eabb7750a7, R.drawable.welcom_img};
    private static final String[] desc = {"text1", "text2", "text3"};
    private static final String[] ProductName = {"Product1", "Product2", "Product3"};
    private static final int[] price = {5, 9, 10};
    private ArrayList<ShopForShow> array = new ArrayList<>();

    public Home_page_for_shop() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_campaign, container, false);
        for (int i = 0; i < img.length; i++)
            array.add(new ShopForShow(img[i], price[i], ProductName[i], desc[i]));

        AdapterForShowProduct adapter = new AdapterForShowProduct(getActivity(), array);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return rootView;
    }
}
