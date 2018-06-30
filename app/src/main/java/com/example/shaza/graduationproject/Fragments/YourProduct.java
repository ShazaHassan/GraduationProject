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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class YourProduct extends Fragment {

    private ArrayList<Product> products = new ArrayList<>();
    private DatabaseReference userTable, productTable;
    private FirebaseUser currentUser;
    private String idUserDB, idProdDB;
    private ArrayList<String> prodIDs = new ArrayList<>();
    private ListView list;
    private TextView noProdToShow;
    private Product product;
    private AdapterForShowProduct adapter;

    public YourProduct() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        idUserDB = currentUser.getUid();
        View rootView = inflater.inflate(R.layout.list_product, container, false);
        list = rootView.findViewById(R.id.list);
        noProdToShow = rootView.findViewById(R.id.no_prod_text_view);
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        productTable = FirebaseDatabase.getInstance().getReference().child("Product");
        getDataFromDB();
        return rootView;
    }

    private void getDataFromDB() {
        userTable.child(idUserDB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Products")) {
                    userTable.child(idUserDB).child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                idProdDB = snapshot.getValue().toString();
                                prodIDs.add(idProdDB);
                                Log.v("product id", idProdDB);
                            }
                            if (prodIDs.size() > 0) {
                                getProducts(prodIDs);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getProducts(final ArrayList<String> prodIDs) {
        productTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    product = snapshot.getValue(Product.class);
                    idProdDB = product.getIdProduct();
                    for (String id : prodIDs) {
                        if (idProdDB.equals(id)) {
                            products.add(product);
                        }
                    }

                }
                if (products.size() > 0) {
                    adapter = new AdapterForShowProduct(getContext(), products, R.color.gray);
                    list.setAdapter(adapter);
                    noProdToShow.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    Log.v("camsizecampfun", Integer.toString(products.size()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

