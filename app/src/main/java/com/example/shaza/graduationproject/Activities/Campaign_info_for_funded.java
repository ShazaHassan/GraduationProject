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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaza.graduationproject.Database.Table.EquityCampaign;
import com.example.shaza.graduationproject.Database.Table.RewardCampaign;
import com.example.shaza.graduationproject.Database.Table.Users;
import com.example.shaza.graduationproject.PrefManager;
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

public class Campaign_info_for_funded extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView campName, descCamp, daysLeft, needMoney, percentage, creatorName, cat, link;
    ImageView imgCamp, editImgCamp;
    ProgressBar progressForPercentage;
    EditText editCampName, editDescCamp;

    private NavigationView navView;
    private FirebaseUser user;
    private View header;
    private TextView name, email;
    private Menu menu;
    private String idUserDB, cDate, eDate;
    private DatabaseReference userTable, rewardTable, equityTable;
    private FirebaseDatabase database;
    private String userName, e_mail, gender;
    private Users users;
    private ImageView pp;
    private String idCampDB, type;
    private Button fund;

    private RewardCampaign campaign;
    private EquityCampaign equityCampaign;

    private Date currentDate, endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private long diff, seconds, minutes, hours, days;
    private Calendar c = Calendar.getInstance();
    private long fundedMoney, neededMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_info_for_creator);
        Intent intent = getIntent();
        database = FirebaseDatabase.getInstance();
        idCampDB = intent.getExtras().getString("id");
        type = intent.getExtras().getString("type");
        creatorName = findViewById(R.id.name_camp_creator);
        campName = findViewById(R.id.campaign_name_creator);
        imgCamp = findViewById(R.id.campaign_image);
        descCamp = findViewById(R.id.description_of_campaign);
        daysLeft = findViewById(R.id.days_left_for_creator);
        needMoney = findViewById(R.id.need_money);
        cat = findViewById(R.id.category_details_page);
        progressForPercentage = findViewById(R.id.progress_bar_for_show_percentage);
        percentage = findViewById(R.id.percentage_for_creator);
        editImgCamp = findViewById(R.id.edit_camp_img);
        editCampName = findViewById(R.id.edit_campaign_name_creator);
        editDescCamp = findViewById(R.id.edit_description_of_campaign);
        link = findViewById(R.id.link_for_video);
        fund = findViewById(R.id.fund_button);

        if (type.equals("reward")) {
            rewardTable = database.getReference().child("Reward Campaign");
            rewardTable.child(idCampDB).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    campaign = dataSnapshot.getValue(RewardCampaign.class);
                    setItems(campaign);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (type.equals("equity")) {
            equityTable = database.getReference().child("Equity Campaign");
            equityTable.child(idCampDB).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    equityCampaign = dataSnapshot.getValue(EquityCampaign.class);
                    setItems(equityCampaign);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        setupDrawer();


        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        menu = navView.getMenu();
        users = new Users();
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
                Intent pp = new Intent(Campaign_info_for_funded.this, Personal_Page.class);
                startActivity(pp);
            }
        });
        menu.findItem(R.id.login).setVisible(false);
        menu.findItem(R.id.sign_up).setVisible(false);
        menu.findItem(R.id.logout).setVisible(true);
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
        link.setText(campaign.getLinkForVideo());
        neededMoney = campaign.getNeededMoney();
        fundedMoney = campaign.getFundedMoney();
        if (days > 0) {
            daysLeft.setText(Long.toString(days) + " Days left");
            if (neededMoney <= fundedMoney) {
                needMoney.setText("Success campaign");

            } else {
                needMoney.setText("need " + (campaign.getNeededMoney() - campaign.getFundedMoney()) + " $");
            }
        } else {
            daysLeft.setText("Ended camp at: " + campaign.getEndDate());
            fund.setVisibility(View.GONE);
            needMoney.setText("");
        }
        int percentageCalculation = (int) ((double) (campaign.getFundedMoney() * 1.0 / campaign.getNeededMoney() * 1.0) * 100);
        Log.v("precentage", Integer.toString(percentageCalculation));
        progressForPercentage.setMax(100);
        progressForPercentage.setProgress(percentageCalculation);
        percentage.setText(percentageCalculation + "%");
        link.setText(campaign.getLinkForVideo());
        cat.setText(campaign.getCategory());
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

    private void setItems(EquityCampaign campaign) {
        campName.setText(campaign.getName());
        String imgURI = campaign.getImgName();
        Picasso.get().load(imgURI).into(imgCamp);
        descCamp.setText("Heighlight of campaign is : " + equityCampaign.getHighlight() + "\n" +
                "Executive team: " + equityCampaign.getTeam() + "\n" +
                "Summary about company: " + equityCampaign.getSummary() + "\n" +
                "Processed and Timeline: " + equityCampaign.getTimeline() + "\n" +
                "Market Analysis: " + equityCampaign.getMarket() + "\n" +
                "Investment terms: " + equityCampaign.getInvestTerms() + "\n" +
                "Investor Discussion: " + equityCampaign.getInvestDiscussion() + "\n" +
                "Add offer: " + equityCampaign.getOffers());
        calculateDaysLeft(campaign);
        link.setText(campaign.getLinkForVideo());
        neededMoney = campaign.getNeededMoney();
        fundedMoney = campaign.getFundedMoney();
        if (days > 0) {
            daysLeft.setText(Long.toString(days) + " Days left");
            if (neededMoney <= fundedMoney) {
                needMoney.setText("Success campaign");

            } else {
                needMoney.setText("need " + (campaign.getNeededMoney() - campaign.getFundedMoney()) + " $");
            }
        } else {
            daysLeft.setText("Ended camp at: " + campaign.getEndDate());
        }
        int percentageCalculation = (int) ((double) (campaign.getFundedMoney() * 1.0 / campaign.getNeededMoney() * 1.0) * 100);
        progressForPercentage.setMax(100);
        progressForPercentage.setProgress(percentageCalculation);
        percentage.setText(percentageCalculation + "%");
        link.setText(campaign.getLinkForVideo());
        cat.setText(campaign.getCategory());
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

    private void calculateDaysLeft(EquityCampaign campaign) {
        currentDate = Calendar.getInstance().getTime();
        cDate = dateFormat.format(currentDate);

        Log.v("Addate", cDate);
        c.add(Calendar.DATE, 7);
        Log.v("Addate", c.getTime().toString());
        eDate = campaign.getEndDate();
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
            PrefManager prefManager = new PrefManager(getApplicationContext());
            // make first time launch TRUE
            prefManager.setFirstTimeLaunch(true);
            startActivity(new Intent(this, WelcomePage.class));
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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

    public void PaymentProcess(View view) {
        if (user != null) {
            Intent paymentProcess = new Intent(this, FundedPage.class);
            paymentProcess.putExtra("id", idCampDB);
            paymentProcess.putExtra("type", type);
            startActivity(paymentProcess);
        } else {
            Toast.makeText(this, "You must login first", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Login.class));
        }
    }
}
