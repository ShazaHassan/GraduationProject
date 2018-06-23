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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shaza.graduationproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText email, password;
    private String e_mail, pass;
    private FirebaseAuth userAuth;
    private NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupDrawer();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        email = findViewById(R.id.email_text_for_login);
        password = findViewById(R.id.password_text_for_login);
        userAuth = FirebaseAuth.getInstance();
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setData() {
        e_mail = String.valueOf(email.getText());
        pass = String.valueOf(password.getText());
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

    public void forgetPasswordPage(View view) {
    }

    public void signUpPage(View view) {
        Intent signUpPage = new Intent(this, SignUp.class);
        startActivity(signUpPage);
    }

    public void openExpertChat(View view) {
        Intent expertChat = new Intent(this, TalkToExpert.class);
        startActivity(expertChat);
    }

    public void Login(View view) {
        setData();
        String errorMsg = "Required field";
        if (e_mail.equals("")) {
            email.setError(errorMsg);
        } else if (pass.equals("")) {
            password.setError(errorMsg);
        } else {
            signInWithEmailAndPassword();
        }
    }

    private void signInWithEmailAndPassword() {
        userAuth.signInWithEmailAndPassword(e_mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.v("", "signInWithCustomToken:success");
                            startActivity(new Intent(Login.this, Home_Page.class));
                            FirebaseUser user = userAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.v("", "signInWithCustomToken:failure", task.getException());
                            Toast.makeText(Login.this, "Wrong mail or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

