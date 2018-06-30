package com.example.shaza.graduationproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Activities.ShopInfoForBuying;
import com.example.shaza.graduationproject.Activities.Shop_info_for_creator;
import com.example.shaza.graduationproject.Database.Table.Product;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shaza Hassan on 28-Jan-18.
 */

public class AdapterForShowProduct extends ArrayAdapter<Product> {

    private Context context;
    private int colorResource;
    private ArrayList<Product> products;

    public AdapterForShowProduct(Context context, ArrayList<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
    }

    public AdapterForShowProduct(Context context, ArrayList<Product> products, int colorResource) {
        super(context, 0, products);
        this.context = context;
        this.products = products;
        this.colorResource = colorResource;
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
        final Product product = getItem(position);
        String idUserDB = null;
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            idUserDB = currentUser.getUid();
        }
        ImageView proImg = listItemView.findViewById(R.id.imge_of_product);
        String imgURI = product.getImageURL();
        Picasso.get().load(imgURI).into(proImg);

        TextView description = listItemView
                .findViewById(R.id.description_of_product);
        description.setText(product.getDescription());

        TextView prodName = listItemView.findViewById(R.id.product_name);
        prodName.setText(product.getName());

        TextView price = listItemView.findViewById(R.id.price_of_product);
        price.setText("Price: " + product.getPrice() + " $");

        TextView category = listItemView.findViewById(R.id.category_of_prod);
        category.setText(product.getCategory());

        View setColorBackground = listItemView.findViewById(R.id.short_view_to_show_prod);
        int color = ContextCompat.getColor(getContext(), colorResource);
        if (colorResource == R.color.gray) {
            int darkBlue = ContextCompat.getColor(getContext(), R.color.darkBlue);
            prodName.setTextColor(darkBlue);
            description.setTextColor(darkBlue);
            price.setTextColor(darkBlue);
            category.setTextColor(darkBlue);
        }
        setColorBackground.setBackgroundColor(color);

        if (currentUser == null || !idUserDB.equals(product.getIdCreator())) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(context, ShopInfoForBuying.class);
                    detailsPage.putExtra("id", product.getIdProduct());
                    context.startActivity(detailsPage);
                }
            });

        } else if (idUserDB.equals(product.getIdCreator())) {
            listItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailsPage = new Intent(context, Shop_info_for_creator.class);
                    detailsPage.putExtra("id", product.getIdProduct());
                    context.startActivity(detailsPage);
                }
            });
        }


        return listItemView;
    }
}
