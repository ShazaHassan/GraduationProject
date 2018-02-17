package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.shaza.graduationproject.Adapters.PageAdapterForCampaign;
import com.example.shaza.graduationproject.R;

public class Reward_campaign_Home_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_campaign__home_page);
        setupDrawer();
        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);
        return true;

    }

    //viewPager set for create tabs
    private void setupViewPager() {
        ViewPager pager = (ViewPager) findViewById(R.id.pagesForViewCampaigns);
        //adapter
        PageAdapterForCampaign pageAdapter = new PageAdapterForCampaign(this, getSupportFragmentManager());
        pager.setAdapter(pageAdapter);
        //tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabForViewCampaigns);
        tabLayout.setupWithViewPager(pager);
    }

    //Drawer set
    private void setupDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
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
        }else if (id == R.id.help_community){
            Intent HelpPage = new Intent(this, HelpingCommunity.class);
            startActivity(HelpPage);
        } else if (id == R.id.sign_up) {
            Intent signUp = new Intent(this, SignUp.class);
            startActivity(signUp);
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
