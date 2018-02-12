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

import com.example.shaza.graduationproject.Adapters.PageAdapterForCampaign;
import com.example.shaza.graduationproject.R;

public class Equity_campaign_Home_page extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equity_campaign__home_page);
        setupDrawer();
        setupViewPager();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);
        return true;

    }

    private void setupViewPager() {
        ViewPager pager = (ViewPager) findViewById(R.id.pagesForViewCampaigns);

        PageAdapterForCampaign pageAdapter = new PageAdapterForCampaign(this, getSupportFragmentManager());
        pager.setAdapter(pageAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabForViewCampaigns);
        tabLayout.setupWithViewPager(pager);
    }

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
        switch (id){
            case R.id.name_of_app:
                Intent homePage = new Intent(this, Home_Page.class);
                startActivity(homePage);

            case R.id.start_campaign:
                Intent startCampaign = new Intent(this, Create_new_campaign.class);
                startActivity(startCampaign);

            case R.id.suppot_startup:
                Intent supportStartupPage = new Intent(this, SupportStartUp.class);
                startActivity(supportStartupPage);

            case  R.id.shop:
                Intent shopPage = new Intent(this, Shop_Page.class);
                startActivity(shopPage);

            case R.id.job:
                Intent HelpPage = new Intent(this, HelpingCommunity.class);
                startActivity(HelpPage);
            case R.id.sign_up:
                Intent HelpPage1 = new Intent(this, HelpingCommunity.class);
                startActivity(HelpPage1);
            case R.id.help:
                Intent HelpPage2 = new Intent(this, HelpingCommunity.class);
                startActivity(HelpPage2);
            case R.id.about_us:
                Intent HelpPage3 = new Intent(this, HelpingCommunity.class);
                startActivity(HelpPage3);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
