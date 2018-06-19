package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Activities.Shop_info_for_creator;
import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.TemplateForAdapter.ShopForShow;

import java.util.ArrayList;

/**
 * Created by Shaza Hassan on 28-Jan-18.
 */

public class AdapterForShowProduct extends ArrayAdapter<ShopForShow> {

    private Context context;
    public AdapterForShowProduct(Context context, ArrayList<ShopForShow> shopForShows) {
        super(context, 0, shopForShows);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.shop_short_view, parent, false);
        }
        ShopForShow shopForShow = getItem(position);

        ImageView myImage = (ImageView) listItemView
                .findViewById(R.id.imge_of_product);
        myImage.setImageResource(shopForShow.getImg());

        TextView description = listItemView
                .findViewById(R.id.description_of_product);
        description.setText(shopForShow.getDescription());

        TextView campaignName = listItemView.findViewById(R.id.product_name);
        campaignName.setText(shopForShow.getName());

        TextView price = listItemView.findViewById(R.id.price_of_product);
        price.setText("Price: " + shopForShow.getPrice() + " $");

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsPage = new Intent(context, Shop_info_for_creator.class);
                detailsPage.putExtra("id", position);
                context.startActivity(detailsPage);
            }
        });

        return listItemView;
    }
}
