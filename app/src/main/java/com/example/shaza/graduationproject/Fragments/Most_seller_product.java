package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.AdapterForShowProduct;
import com.example.shaza.graduationproject.Database.Table.Product;
import com.example.shaza.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Shaza Hassan on 28-Jan-18.
 */

public class Most_seller_product extends Fragment {

    private View rootView;
    private ArrayList<Product> products = new ArrayList<>();
    private DatabaseReference productTable;
    private Product product;
    private TextView noProdTextView;
    private ListView listView;
    private AdapterForShowProduct adapter;

    public Most_seller_product() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.list_product, container, false);
        rootView = inflater.inflate(R.layout.list_product, container, false);
        productTable = FirebaseDatabase.getInstance().getReference().child("Product");
        noProdTextView = rootView.findViewById(R.id.no_prod_text_view);
        listView = rootView.findViewById(R.id.list);
        productTable.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    product = snapshot.getValue(Product.class);
                    long noOfBuying = product.getNoOfBuying();
                    if (noOfBuying >= 5) {
                        products.add(snapshot.getValue(Product.class));
                    }
                }
                if (products.size() != 0) {
                    Log.v("popular", "camps");
                    noProdTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    adapter = new AdapterForShowProduct(getActivity(), products, R.color.lightBlue);
                    listView.setAdapter(adapter);
                } else {
                    Log.v("popular", "no camp");
                    listView.setVisibility(View.GONE);
                    noProdTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return rootView;
    }
}
