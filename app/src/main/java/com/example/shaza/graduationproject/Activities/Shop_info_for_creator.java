package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaza.graduationproject.R;

import static com.example.shaza.graduationproject.Fragments.Home_page_for_shop.ProductName;
import static com.example.shaza.graduationproject.Fragments.Home_page_for_shop.desc;
import static com.example.shaza.graduationproject.Fragments.Home_page_for_shop.img;
import static com.example.shaza.graduationproject.Fragments.Home_page_for_shop.price;

public class Shop_info_for_creator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info_for_creator);
        Intent intent = getIntent();
        pos = intent.getExtras().getInt("id");
        setupDrawer();
        setItems();
        ImageView creatorImage = findViewById(R.id.img_product_creator);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unkown_user);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        creatorImage.setImageDrawable(roundedBitmapDrawable);
        Button buy = findViewById(R.id.buy_button);
        buy.setVisibility(View.GONE);
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
    }

    private void setItems() {
        TextView productName = findViewById(R.id.product_name_creator);
        ImageView imgProduct = findViewById(R.id.product_image_creator);
        TextView descProduct = findViewById(R.id.description_of_product_creator);
        TextView priceProduct = findViewById(R.id.price_of_product_creator);
        productName.setText(ProductName[pos]);
        imgProduct.setImageResource(img[pos]);
        descProduct.setText(desc[pos]);
        priceProduct.setText(price[pos] + "$");

    }

    //options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_delete, menu);
        return true;
    }

    //setup toolbar and side drawer
    private void setupDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.name_of_app) {
            Intent homePage = new Intent(this, Home_Page.class);
            startActivity(homePage);
        } else if (id == R.id.start_campaign) {
            Intent startCampaign = new Intent(this, Create_new_campaign.class);
            startActivity(startCampaign);
            // Handle the camera action
        } else if (id == R.id.suppot_startup) {
            Intent supportStartupPage = new Intent(this, SupportStartUp.class);
            startActivity(supportStartupPage);
        } else if (id == R.id.shop) {
            Intent shopPage = new Intent(this, Shop_Page.class);
            startActivity(shopPage);
        } else if (id == R.id.job) {
            Intent jobPage = new Intent(this, Job.class);
            startActivity(jobPage);
        } else if (id == R.id.login) {
            Intent loginPage = new Intent(this, Login.class);
            startActivity(loginPage);
        } else if (id == R.id.help_community) {
            Intent HelpPage = new Intent(this, HelpingCommunity.class);
            startActivity(HelpPage);
        } else if (id == R.id.sign_up) {
            Intent signUpPage = new Intent(this, SignUp.class);
            startActivity(signUpPage);
        } else if (id == R.id.help) {


        } else if (id == R.id.about_us) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public void openExpertChat(View view) {
        Intent expertChat = new Intent(this, TalkToExpert.class);
        startActivity(expertChat);
    }
}
