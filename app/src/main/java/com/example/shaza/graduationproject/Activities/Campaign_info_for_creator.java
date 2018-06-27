package com.example.shaza.graduationproject.Activities;

import android.content.Context;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Campaign_info_for_creator extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView campName, descCamp, daysLeft, needMoney, percentage, creatorName, category;
    private ImageView imgCamp, editImgCamp;
    private Date currentDate, endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private long diff, seconds, minutes, hours, days;
    private Calendar c = Calendar.getInstance();
    ProgressBar progressForPercentage;
    EditText editCampName, editDescCamp;
    Button fund, done;
    String newCampName, newDescCamp;
    Context context = this;

    private NavigationView navView;
    private FirebaseUser user;
    private View header;
    private TextView name, email;
    private Menu menu;
    private String idDatabase;
    private DatabaseReference userTable, rewardTable;
    private FirebaseDatabase database;
    private String userName;
    private Users users;
    private String idCampDB, type, cDate, eDate;
    private RewardCampaign campaign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_info_for_creator);
        Intent intent = getIntent();
        idCampDB = intent.getExtras().getString("id");
        type = intent.getExtras().getString("type");
        setupDrawer();
        creatorName = findViewById(R.id.name_camp_creator);
        campName = findViewById(R.id.campaign_name_creator);
        imgCamp = findViewById(R.id.campaign_image);
        descCamp = findViewById(R.id.description_of_campaign);
        daysLeft = findViewById(R.id.days_left_for_creator);
        needMoney = findViewById(R.id.need_money);
        progressForPercentage = findViewById(R.id.progress_bar_for_show_percentage);
        percentage = findViewById(R.id.percentage_for_creator);
        category = findViewById(R.id.category_details_page);
        editImgCamp = findViewById(R.id.edit_camp_img);
        editCampName = findViewById(R.id.edit_campaign_name_creator);
        editDescCamp = findViewById(R.id.edit_description_of_campaign);

        fund = findViewById(R.id.fund_button);
        fund.setVisibility(View.GONE);
        done = findViewById(R.id.done_button);

        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        menu = navView.getMenu();
        users = new Users();
        database = FirebaseDatabase.getInstance();
        userTable = database.getReference().child("Users");
        if (type.equals("reward")) {
            rewardTable = database.getReference().child("Reward Campaign");
            rewardTable.child(idCampDB).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    campaign = dataSnapshot.getValue(RewardCampaign.class);
                    setItems(campaign);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            setHeaderDrawer();
        } else {
            navView.removeHeaderView(navView.getHeaderView(0));
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.sign_up).setVisible(true);
            startActivity(new Intent(this, Home_Page.class));
        }
    }


    private void setHeaderDrawer() {
        header = navView.getHeaderView(0);
        idDatabase = user.getUid();
        name = header.findViewById(R.id.name_at_header);
        email = header.findViewById(R.id.mail_at_header);
        userTable.child(idDatabase).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users = dataSnapshot.getValue(Users.class);
                userName = users.getFirstName() + " " + users.getLastName();
                name.setText(userName);
                email.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        header.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pp = new Intent(Campaign_info_for_creator.this, Personal_Page.class);
                startActivity(pp);
            }
        });
        menu.findItem(R.id.login).setVisible(false);
        menu.findItem(R.id.sign_up).setVisible(false);
        menu.findItem(R.id.logout).setVisible(true);
    }

    private void makeProfilePic(String gender, ImageView img) {
        if (gender.equals("Female")) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_female_user);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            img.setImageDrawable(roundedBitmapDrawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_male_user);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            img.setImageDrawable(roundedBitmapDrawable);
        }
    }


    private void setItems(RewardCampaign campaign) {
        campName.setText(campaign.getName());
        String imgURI = campaign.getCampaign_Image();
        Picasso.get().load(imgURI).into(imgCamp);
        descCamp.setText("Heighlight of campaign is : " + campaign.getHeighlight() + "\n" +
                "Vision of this campaign : " + campaign.getVision() + "\n" +
                "Offers for funded : " + campaign.getOffers() + "\n" +
                "Team helps in campaign : " + campaign.getHelperTeam());
        calculateDaysLeft(campaign);
        daysLeft.setText(Long.toString(days) + " Days left");
        needMoney.setText("need " + (campaign.getNeededMoney() - campaign.getFundedMoney()) + " $");
        int percentageCalculation = (int) ((double) (campaign.getFundedMoney() * 1.0 / campaign.getNeededMoney() * 1.0) * 100);
        progressForPercentage.setMax(100);
        progressForPercentage.setProgress(percentageCalculation);
        percentage.setText(percentageCalculation + "%");
        category.setText(campaign.getCategory());
        userTable.child(campaign.getIDCreator()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users = dataSnapshot.getValue(Users.class);
                userName = users.getFirstName() + " " + users.getLastName();
                creatorName.setText(userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void calculateDaysLeft(RewardCampaign rewardCampaign) {
        currentDate = Calendar.getInstance().getTime();
        cDate = dateFormat.format(currentDate);

        Log.v("Addate", cDate);
        c.add(Calendar.DATE, 7);
        Log.v("Addate", c.getTime().toString());
        eDate = rewardCampaign.getEndDate();
        try {
            c.setTime(dateFormat.parse(eDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        endDate = c.getTime();
        diff = endDate.getTime() - currentDate.getTime();
        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        days = hours / 24;
        Log.v("date", Long.toString(days));
    }

    //options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int id = item.getItemId();
        if (id == R.id.edit_icon) {
            showAndHideForEdit();
            editCampName.setText(campName.getText());
            editDescCamp.setText(descCamp.getText());
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newCampName = String.valueOf(editCampName.getText());
                    newDescCamp = String.valueOf(editDescCamp.getText());

                    if (newCampName.equals("")) {
                        Toast.makeText(context, "please enter new name of camp", Toast.LENGTH_LONG).show();
                    } else if (newDescCamp.equals("")) {
                        Toast.makeText(context, "please enter new description of camp", Toast.LENGTH_LONG).show();
                    } else {
                        campName.setText(newCampName);
                        descCamp.setText(newDescCamp);
                        campName.setVisibility(View.VISIBLE);
                        descCamp.setVisibility(View.VISIBLE);
                        daysLeft.setVisibility(View.VISIBLE);
                        needMoney.setVisibility(View.VISIBLE);
                        progressForPercentage.setVisibility(View.VISIBLE);
                        percentage.setVisibility(View.VISIBLE);
                        done.setVisibility(View.GONE);
                        editDescCamp.setVisibility(View.GONE);
                        editCampName.setVisibility(View.INVISIBLE);
                        editImgCamp.setVisibility(View.GONE);
                    }
                }
            });

        }

        return true;
    }

    private void showAndHideForEdit() {
        campName.setVisibility(View.GONE);
        descCamp.setVisibility(View.GONE);
        daysLeft.setVisibility(View.GONE);
        needMoney.setVisibility(View.GONE);
        progressForPercentage.setVisibility(View.GONE);
        percentage.setVisibility(View.GONE);
        done.setVisibility(View.VISIBLE);
        editDescCamp.setVisibility(View.VISIBLE);
        editCampName.setVisibility(View.VISIBLE);
        editImgCamp.setVisibility(View.VISIBLE);
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
        }else if (id == R.id.help_community){
            Intent HelpPage = new Intent(this, HelpingCommunity.class);
            startActivity(HelpPage);
        } else if (id == R.id.sign_up) {
            Intent signUpPage = new Intent(this, SignUp.class);
            startActivity(signUpPage);
        } else if (id == R.id.help) {


        } else if (id == R.id.about_us) {

        } else if (id == R.id.logout) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.removeHeaderView(navigationView.getHeaderView(0));

            Menu menu = navigationView.getMenu();
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

    public void uploadPhotoAndVideo(View view) {
    }

}
