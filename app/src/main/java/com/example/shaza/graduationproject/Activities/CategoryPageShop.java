package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class CategoryPageShop extends AppCompatActivity {

    private TextView noProd;
    private ListView list;
    private String category;
    private DatabaseReference shopTable;
    private ArrayList<Product> products = new ArrayList<>();
    private AdapterForShowProduct shop;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_product);
        noProd = findViewById(R.id.no_prod_text_view);
        list = findViewById(R.id.list);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        setTitle(category);
        Log.v("size", category);
        shopTable = FirebaseDatabase.getInstance().getReference().child("Product");
        getDataFromDBProduct();
    }

    private void getDataFromDBProduct() {
        shopTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.v("size", "elements");
                    product = snapshot.getValue(Product.class);
                    if (category.equals("All project")) {
                        Log.v("size", "enter here");
                        products.add(product);
                    } else if (category.equals(product.getCategory())) {
                        Log.v("size", "special cat");
                        products.add(product);
                    }
                }
                Log.v("size", Integer.toString(products.size()));
                if (products.size() > 0) {
                    shop = new AdapterForShowProduct(CategoryPageShop.this, products, R.color.gray);
                    noProd.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    list.setAdapter(shop);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
