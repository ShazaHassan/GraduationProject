package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shaza.graduationproject.Database.Table.Product;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BuyPage extends AppCompatActivity {
    private FirebaseUser currentUser;
    private DatabaseReference userTable, productTable;
    private String idProdDB, idUserDB;
    private Product product;
    private EditText quantity;
    private TextView totalToPay;
    private long price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.5));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        productTable = FirebaseDatabase.getInstance().getReference().child("Product");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        idProdDB = intent.getExtras().getString("id");
        idUserDB = currentUser.getUid();
        quantity = findViewById(R.id.quantity_of_prod);
        totalToPay = findViewById(R.id.total_to_pay);
        productTable.child(idProdDB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                price = dataSnapshot.getValue(Product.class).getPrice();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(quantity.getText().toString().equals(""))) {
                    totalToPay.setText((Integer.parseInt(quantity.getText().toString()) * price) + " $");
                } else {
                    totalToPay.setText(0 + " $");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.close_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.close_button) {
            finish();
        }
        return true;
    }

    public void PayProcess(View view) {
        if (quantity.getText().toString().equals("")) {
            quantity.setError("Please enter the quantity");
        } else {
            productTable.child(idProdDB).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    product = dataSnapshot.getValue(Product.class);
                    long noOfBuy = product.getNoOfBuying();
                    productTable.child(idProdDB).child("noOfBuying").setValue(noOfBuy + 1);
                    userTable.child(idUserDB).child("Purchase Product").child(idProdDB).setValue(idProdDB);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
