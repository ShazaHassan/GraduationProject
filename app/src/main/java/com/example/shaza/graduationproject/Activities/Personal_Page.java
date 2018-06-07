package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.AdapterForPersonalPage;
import com.example.shaza.graduationproject.Fragments.Profile;
import com.example.shaza.graduationproject.R;

public class Personal_Page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText username, email, birthday, country;
    TextView userName, eMail, birth, coun;
    Button done;
    ViewPager pager;
    Profile profile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        setupDrawer();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setupViewPager();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_delete, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (pager.getCurrentItem() == 0) {
            View view = pager.getRootView();
            username = (EditText) view.findViewById(R.id.edit_user_name);
            username = (EditText) view.findViewById(R.id.edit_user_name);
            email = (EditText) view.findViewById(R.id.edit_email);
            birthday = (EditText) view.findViewById(R.id.edit_birth);
            country = (EditText) view.findViewById(R.id.edit_country);
            userName = (TextView) view.findViewById(R.id.user_name);
            eMail = (TextView) view.findViewById(R.id.email_personal_page);
            birth = (TextView) view.findViewById(R.id.birthday_perdonal_page);
            coun = (TextView) view.findViewById(R.id.country);
            done = (Button) view.findViewById(R.id.done_btn);
            if (id == R.id.edit_icon) {
                username.setVisibility(View.VISIBLE);
                username.setText(userName.getText());
                userName.setVisibility(View.INVISIBLE);

                email.setVisibility(View.VISIBLE);
                email.setText(eMail.getText());
                eMail.setVisibility(View.INVISIBLE);

                birthday.setVisibility(View.VISIBLE);
                birthday.setText(birth.getText());
                birth.setVisibility(View.INVISIBLE);

                country.setVisibility(View.VISIBLE);
                country.setText(coun.getText());
                coun.setVisibility(View.INVISIBLE);

                done.setVisibility(View.VISIBLE);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        username.setVisibility(View.GONE);
                        userName.setText(String.valueOf(username.getText()));
                        userName.setVisibility(View.VISIBLE);

                        email.setVisibility(View.GONE);
                        eMail.setText(String.valueOf(email.getText()));
                        eMail.setVisibility(View.VISIBLE);

                        birthday.setVisibility(View.GONE);
                        birth.setText(String.valueOf(birth.getText()));
                        birth.setVisibility(View.VISIBLE);

                        country.setVisibility(View.GONE);
                        coun.setText(String.valueOf(country.getText()));
                        coun.setVisibility(View.VISIBLE);

                        done.setVisibility(View.GONE);
                    }
                });

            }
        } else if (id == R.id.delete_icon) {

        }
        return true;
    }

    private void setupViewPager() {
        pager = (ViewPager) findViewById(R.id.pagesForViewPersonalPage);
        //adapter
        AdapterForPersonalPage pageAdapter = new AdapterForPersonalPage(this, getSupportFragmentManager());
        pager.setAdapter(pageAdapter);
        //tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabForViewPersonalPage);
        tabLayout.setupWithViewPager(pager);
    }

    //set up toolbar and side drawer
    private void setupDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        } else if (id == R.id.suppot_startup) {
            Intent supportPage = new Intent(this, SupportStartUp.class);
            startActivity(supportPage);
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
}
