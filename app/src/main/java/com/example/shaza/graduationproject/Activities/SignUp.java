package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shaza.graduationproject.R;

public class SignUp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText firstName, secondName, password, rePassword, email, country, birthday;
    String fName, sName, pass, rePass, eMail, coun, birth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupDrawer();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        firstName = (EditText) findViewById(R.id.first_name_text_for_sign_up);
        secondName = (EditText) findViewById(R.id.second_name_text_for_sign_up);
        email = (EditText) findViewById(R.id.email_text_for_sign_up);
        password = (EditText) findViewById(R.id.password_text_for_sign_up);
        rePassword = (EditText) findViewById(R.id.rePassword_text_for_sign_up);
        country = (EditText) findViewById(R.id.country_text_for_sign_up);
        birthday = (EditText) findViewById(R.id.birtday_text_for_sign_up);

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
        }else if (id == R.id.help_community){
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

    public void profilePage(View view) {
        fName = String.valueOf(firstName.getText());
        sName = String.valueOf(secondName.getText());
        eMail = String.valueOf(email.getText());
        coun = String.valueOf(country.getText());
        pass = String.valueOf(password.getText());
        rePass = String.valueOf(rePassword.getText());
        birth = String.valueOf(birthday.getText());
        if (fName.equals("")) {
            Toast.makeText(this, "Enter the first name", Toast.LENGTH_LONG).show();
        } else if (sName.equals("")) {
            Toast.makeText(this, "Enter the second name", Toast.LENGTH_LONG).show();
        } else if (birth.isEmpty()) {
            Toast.makeText(this, "Enter your birthday", Toast.LENGTH_LONG).show();
        } else if (eMail.equals("")) {
            Toast.makeText(this, "Enter your mail", Toast.LENGTH_LONG).show();
        } else if (pass.equals("")) {
            Toast.makeText(this, "Enter your password", Toast.LENGTH_LONG).show();
        } else if (rePass.equals("")) {
            Toast.makeText(this, "Enter your repeated password", Toast.LENGTH_LONG).show();
        } else if (coun.equals("")) {
            Toast.makeText(this, "Enter your country", Toast.LENGTH_LONG).show();
        } else if (!pass.equals(rePass)) {
            Toast.makeText(this, "Not Same Password, Please enter repeat password right", Toast.LENGTH_LONG).show();
        } else {
            Intent profilePage = new Intent(this, Personal_Page.class);
            startActivity(profilePage);
        }

    }
}
