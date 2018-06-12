package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.shaza.graduationproject.Adapters.AdapterForShowProduct;
import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.TemplateForAdapter.ShopForShow;

import java.util.ArrayList;

import static com.example.shaza.graduationproject.Fragments.Home_page_for_shop.ProductName;
import static com.example.shaza.graduationproject.Fragments.Home_page_for_shop.desc;
import static com.example.shaza.graduationproject.Fragments.Home_page_for_shop.img;
import static com.example.shaza.graduationproject.Fragments.Home_page_for_shop.price;

public class YourProduct extends Fragment {
    private ArrayList<ShopForShow> array = new ArrayList<>();

    public YourProduct() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_campaign, container, false);
        array.clear();
        for (int i = 1; i < img.length; i++)
            array.add(new ShopForShow(img[i], price[i], ProductName[i], desc[i]));

        AdapterForShowProduct adapter = new AdapterForShowProduct(getActivity(), array);
        ListView listView = rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        return rootView;
    }
}
