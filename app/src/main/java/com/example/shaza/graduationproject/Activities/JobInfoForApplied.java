package com.example.shaza.graduationproject.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shaza.graduationproject.Database.Table.CampaignType;
import com.example.shaza.graduationproject.Database.Table.EquityCampaign;
import com.example.shaza.graduationproject.Database.Table.Job;
import com.example.shaza.graduationproject.Database.Table.RewardCampaign;
import com.example.shaza.graduationproject.Database.Table.Users;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class JobInfoForApplied extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navView;
    private FirebaseUser user;
    private View header;
    private TextView name, email;
    private Menu menu;
    private String idUserDB, idJobDB, campType, idCampDB;
    private DatabaseReference userTable, jobTable, campTable;
    private FirebaseDatabase database;
    private String userName, e_mail;
    private Users users;
    private Job job;
    private CampaignType type;
    private TextView companyName, jobDesc, creatorName, fromCamp, jobCategory, links;
    private ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_info_creator);
        Intent intent = getIntent();
        idJobDB = intent.getExtras().getString("id");
        setupDrawer();

        companyName = findViewById(R.id.company_name);
        logo = findViewById(R.id.logo_of_company);
        jobDesc = findViewById(R.id.job_description);
        links = findViewById(R.id.links_for_company);
        creatorName = findViewById(R.id.name_job_creator);
        fromCamp = findViewById(R.id.from_camp);
        jobCategory = findViewById(R.id.job_category);
        database = FirebaseDatabase.getInstance();
        userTable = database.getReference().child("Users");
        jobTable = database.getReference().child("Job");
        user = FirebaseAuth.getInstance().getCurrentUser();
        setItems();

        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        menu = navView.getMenu();
        users = new Users();

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
        idUserDB = user.getUid();
        name = header.findViewById(R.id.name_at_header);
        email = header.findViewById(R.id.mail_at_header);
        userTable.child(idUserDB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users = dataSnapshot.getValue(Users.class);
                userName = users.getFirstName() + " " + users.getLastName();
                e_mail = user.getEmail();
                name.setText(userName);
                email.setText(e_mail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        header.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pp = new Intent(JobInfoForApplied.this, Personal_Page.class);
                startActivity(pp);
            }
        });
        menu.findItem(R.id.login).setVisible(false);
        menu.findItem(R.id.sign_up).setVisible(false);
        menu.findItem(R.id.logout).setVisible(true);
    }

    private void setItems() {

        jobTable.child(idJobDB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                job = dataSnapshot.getValue(Job.class);
                String imgURL = job.getImageURL();
                companyName.setText(job.getName());
                Picasso.get().load(imgURL).into(logo);
                jobDesc.setText(job.getDescription());
                links.setText(job.getLinks());
                jobCategory.setText(job.getCategory());
                userTable.child(job.getIdCreator()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        creatorName.setText(dataSnapshot.getValue(Users.class).getFirstName() + " "
                                + dataSnapshot.getValue(Users.class).getLastName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                type = job.getCampaignType();
                getJob(type);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getJob(CampaignType type) {
        idCampDB = type.getID();
        campType = type.getType();
        if (campType.equals("Equity")) {
            campTable = database.getReference().child("Equity Campaign");
            campTable.child(idCampDB).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    fromCamp.setText("From Camp: " + dataSnapshot.getValue(EquityCampaign.class).getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (campType.equals("Reward")) {
            campTable = database.getReference().child("Reward Campaign");
            campTable.child(idCampDB).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    fromCamp.setText(dataSnapshot.getValue(RewardCampaign.class).getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    //setup toolbar and side drawer
    private void setupDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
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

        } else if (id == R.id.logout) {
            navView.removeHeaderView(navView.getHeaderView(0));
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.sign_up).setVisible(true);
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, Home_Page.class));
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

    public void goToCampDetails(View view) {
        Intent campDetails = new Intent(this, Campaign_info_for_creator.class);
        campDetails.putExtra("id", idCampDB);
        if (campType.equals("Reward")) {
            campDetails.putExtra("type", "reward");
        } else if (campType.equals("Equity")) {
            campDetails.putExtra("type", "equity");
        }
        startActivity(campDetails);
    }

    public void applyForJob(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload Your CV on Drive and put link here");

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(5, 5, 5, 5);
        // Set up the input
        final EditText link = new EditText(this);
        link.setHint("Put link of your CV here");

//        repeatPassword.setPadding(5,5,5,5);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        link.setInputType(InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        linearLayout.addView(link);
        builder.setView(linearLayout);

        // Set up the buttons
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", null);

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (link.getText().toString().equals("")) {
                    link.setError("Can't Be Empty Field");
                } else {
                    userTable.child(idUserDB).child("Applied Job").child(idJobDB).setValue(idJobDB);
                    dialog.dismiss();
                }
            }
        });


    }
}
