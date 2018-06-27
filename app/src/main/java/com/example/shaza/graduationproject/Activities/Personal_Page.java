package com.example.shaza.graduationproject.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.PageAdapterForPersonalPage;
import com.example.shaza.graduationproject.Database.Table.Users;
import com.example.shaza.graduationproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.StringTokenizer;

public class Personal_Page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    EditText username, email, birthday, country;
    TextView userName, eMail, birth, coun;
    Button done;
    String name, mail, day, c, g;
    ViewPager pager;
    Context context = this;

    private NavigationView navView;
    private FirebaseUser user;
    private View header;
    private TextView nameHeader, emailHeader;
    private Menu menu;
    private String idDatabase;
    private DatabaseReference userTable;
    private FirebaseDatabase database;
    private String userNameHeader, e_mailHeader;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        setupDrawer();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setupViewPager();

        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        menu = navView.getMenu();
        users = new Users();
        database = FirebaseDatabase.getInstance();
        userTable = database.getReference().child("Users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            setHeaderDrawer();
        } else {
            navView.removeHeaderView(navView.getHeaderView(0));
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.sign_up).setVisible(true);
        }

    }

    private void setHeaderDrawer() {
        header = navView.getHeaderView(0);
        idDatabase = user.getUid();
        nameHeader = header.findViewById(R.id.name_at_header);
        emailHeader = header.findViewById(R.id.mail_at_header);
        userTable.child(idDatabase).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users = dataSnapshot.getValue(Users.class);
                userNameHeader = users.getFirstName() + " " + users.getLastName();
                e_mailHeader = user.getEmail();
                nameHeader.setText(userNameHeader);
                emailHeader.setText(e_mailHeader);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        header.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pp = new Intent(Personal_Page.this, Personal_Page.class);
                startActivity(pp);
            }
        });
        menu.findItem(R.id.login).setVisible(false);
        menu.findItem(R.id.sign_up).setVisible(false);
        menu.findItem(R.id.logout).setVisible(true);
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

        } else if (id == R.id.logout) {
            navView.removeHeaderView(navView.getHeaderView(0));
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.sign_up).setVisible(true);
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Personal_Page.this, Home_Page.class));
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            getItems(view);
            if (id == R.id.edit_icon) {
                showItemsForEdit();
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        name = String.valueOf(username.getText());
                        day = String.valueOf(birth.getText());
                        c = String.valueOf(country.getText());
                        StringTokenizer s = new StringTokenizer(name);
                        if (name.equals("")) {
                            username.setError("Username can't be empty field");
                        } else if (day.equals("")) {
                            birthday.setError("Birthday can't be empty field");
                        } else if (c.equals("")) {
                            country.setError("Your country can't be empty field");
                        } else {
                            hideItemsForEdit();
                            userTable.child(idDatabase).child("firstName").setValue(s.nextToken());
                            userTable.child(idDatabase).child("lastName").setValue(s.nextToken("").trim());
                            userTable.child(idDatabase).child("birthday").setValue(day);
                            userTable.child(idDatabase).child("gender").setValue(g);
                            userTable.child(idDatabase).child("country").setValue(c);
                        }
                    }
                });

            }
        } else if (id == R.id.delete_icon) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(context);
            }
            builder.setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            userTable.child(idDatabase).removeValue();
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            // Get auth credentials from the user for re-authentication. The example below shows
                            // email and password credentials but there are multiple possible providers,
                            // such as GoogleAuthProvider or FacebookAuthProvider.
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential("user@example.com", "password1234");

                            // Prompt the user to re-provide their sign-in credentials
                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            user.delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.v("user delete", "User account deleted.");
                                                            }
                                                        }
                                                    });

                                        }
                                    });

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return true;
    }

    private void setupViewPager() {
        pager = (ViewPager) findViewById(R.id.pagesForViewPersonalPage);
        //adapter
        PageAdapterForPersonalPage pageAdapter = new PageAdapterForPersonalPage(this, getSupportFragmentManager());
        pager.setAdapter(pageAdapter);
        //tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabForViewPersonalPage);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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

    private void getItems(View view) {
        username = view.findViewById(R.id.edit_user_name);
        username = view.findViewById(R.id.edit_user_name);
        email = view.findViewById(R.id.edit_email);
        birthday = view.findViewById(R.id.edit_birth);
        country = view.findViewById(R.id.edit_country);
        userName = view.findViewById(R.id.user_name);
        eMail = view.findViewById(R.id.email_personal_page);
        birth = view.findViewById(R.id.birthday_personal_page);
        coun = view.findViewById(R.id.country);
        done = view.findViewById(R.id.done_btn);
    }

    private void showItemsForEdit() {
        username.setVisibility(View.VISIBLE);
        username.setText(userName.getText());
        userName.setVisibility(View.INVISIBLE);

        birthday.setVisibility(View.VISIBLE);
        birthday.setText(birth.getText());
        birth.setVisibility(View.INVISIBLE);

        country.setVisibility(View.VISIBLE);
        country.setText(coun.getText());
        coun.setVisibility(View.INVISIBLE);

        done.setVisibility(View.VISIBLE);

    }

    private void hideItemsForEdit() {
        username.setVisibility(View.INVISIBLE);
        userName.setText(name);
        userName.setVisibility(View.VISIBLE);

        birthday.setVisibility(View.INVISIBLE);
        birth.setText(day);
        birth.setVisibility(View.INVISIBLE);

        country.setVisibility(View.INVISIBLE);
        coun.setText(c);
        coun.setVisibility(View.VISIBLE);

        done.setVisibility(View.GONE);
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
