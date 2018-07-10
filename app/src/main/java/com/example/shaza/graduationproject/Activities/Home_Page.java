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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.AdapterForShowEquityCampaign;
import com.example.shaza.graduationproject.Adapters.AdapterForShowRewardCampaign;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Home_Page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ListView rewardSuccess, rewardEnding, rewardNewest, equitySuccess, equityEnding, equityNewest;
    private NavigationView navView;
    private FirebaseUser user;
    private View header;
    private TextView name, email, noCampReSu, noCampReEn, noCampReNe, noCampEqSu, noCampEqEn, noCampEqNe;
    private Menu menu;
    private String idDatabase;
    private DatabaseReference userTable, rewardTable, equityTable;
    private FirebaseDatabase database;
    private String userName, e_mail, gender;
    private Users users;
    private ImageView pp;

    private AdapterForShowRewardCampaign successCamp, endingCamp, newestCamp;
    private RewardCampaign endingReCampaign, successReCampaign, newestReCampaign;
    private ArrayList<RewardCampaign> endingCampaigns = new ArrayList<>(), endingShowCamps = new ArrayList<>(),
            successCampaigns = new ArrayList<>(), successShowCamps = new ArrayList<>(),
            newestCampaigns = new ArrayList<>(), newestShowCamps = new ArrayList<>();
    private ArrayList<EquityCampaign> endingEqCampaigns = new ArrayList<>(), endingEqShowCamps = new ArrayList<>(),
            successEqCampaigns = new ArrayList<>(), successEqShowCamps = new ArrayList<>(),
            newestEqCampaigns = new ArrayList<>(), newestEqShowCamps = new ArrayList<>();

    private Date startDate, endDate, currentDate;
    private SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
    private Calendar c = Calendar.getInstance(), e = Calendar.getInstance();
    private String eDate, sDate, cDate;
    private long diff, seconds, minutes, hours, days;
    private EquityCampaign successEqCampaign, endingEqCampaign, newestEqCampaign;
    private AdapterForShowEquityCampaign successEqCamp, endingEqCamp, newestEqCamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setupDrawer();
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        menu = navView.getMenu();
        users = new Users();
        noCampReSu = findViewById(R.id.no_camp_re_su);
        noCampReEn = findViewById(R.id.no_camp_re_en);
        noCampReNe = findViewById(R.id.no_camp_re_ne);
        noCampEqSu = findViewById(R.id.no_camp_eq_su);
        noCampEqEn = findViewById(R.id.no_camp_eq_en);
        noCampEqNe = findViewById(R.id.no_camp_eq_ne);
        database = FirebaseDatabase.getInstance();
        userTable = database.getReference().child("Users");
        rewardTable = database.getReference().child("Reward Campaign");
        equityTable = database.getReference().child("Equity Campaign");
        user = FirebaseAuth.getInstance().getCurrentUser();
        initRewardCampSuccess();
        initRewardCampEnding();
        initRewardCampNewest();

        initEquityCampSuccess();
        initEquityCampEnding();
        initEquityCampNewest();
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
        name = header.findViewById(R.id.name_at_header);
        email = header.findViewById(R.id.mail_at_header);
        userTable.child(idDatabase).addListenerForSingleValueEvent(new ValueEventListener() {
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
                Intent pp = new Intent(Home_Page.this, Personal_Page.class);
                startActivity(pp);
            }
        });
        menu.findItem(R.id.login).setVisible(false);
        menu.findItem(R.id.sign_up).setVisible(false);
        menu.findItem(R.id.logout).setVisible(true);
    }

    private void makeProfilePic(String gender) {
        if (gender.equals("Female")) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_female_user);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            pp.setImageDrawable(roundedBitmapDrawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_male_user);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            roundedBitmapDrawable.setCircular(true);
            pp.setImageDrawable(roundedBitmapDrawable);
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.search, menu);
//        return true;
//    }

    //setup view pager for reward campaign for slide campaign
    private void initRewardCampSuccess() {

        rewardSuccess = findViewById(R.id.list_reward_success);
        rewardTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                successCampaigns.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    successReCampaign = snapshot.getValue(RewardCampaign.class);
                    eDate = successReCampaign.getEndDate();
                    try {
                        c.setTime(format.parse(eDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentDate = Calendar.getInstance().getTime();
                    endDate = c.getTime();
                    diff = endDate.getTime() - currentDate.getTime();
                    seconds = diff / 1000;
                    minutes = seconds / 60;
                    hours = minutes / 60;
                    days = hours / 24;
                    if ((successReCampaign.getNeededMoney() <= successReCampaign.getFundedMoney())) {
                        successCampaigns.add(successReCampaign);
                    }

                }
                if (successCampaigns.size() > 0 && successCampaigns.size() <= 3) {
                    successCamp = new AdapterForShowRewardCampaign(Home_Page.this, successCampaigns, R.color.darkBlue);
                    rewardSuccess.setAdapter(successCamp);
                    rewardSuccess.setVisibility(View.VISIBLE);
                    noCampReSu.setVisibility(View.GONE);
                } else if (successCampaigns.size() > 3) {
                    successShowCamps.clear();
                    for (int i = 0; i < 3; i++) {
                        successShowCamps.add(successCampaigns.get(i));
                    }
                    rewardSuccess.setVisibility(View.VISIBLE);
                    successCamp = new AdapterForShowRewardCampaign(Home_Page.this, successShowCamps, R.color.darkBlue);
                    rewardSuccess.setAdapter(successCamp);
                    noCampReSu.setVisibility(View.GONE);

                } else {
                    rewardSuccess.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initRewardCampEnding() {
        rewardEnding = findViewById(R.id.list_reward_ending);
        rewardTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                endingCampaigns.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    endingReCampaign = snapshot.getValue(RewardCampaign.class);
                    eDate = endingReCampaign.getEndDate();
                    try {
                        c.setTime(format.parse(eDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentDate = Calendar.getInstance().getTime();
                    endDate = c.getTime();
                    diff = endDate.getTime() - currentDate.getTime();
                    seconds = diff / 1000;
                    minutes = seconds / 60;
                    hours = minutes / 60;
                    days = hours / 24;
                    Log.v("day", Long.toString(days));
                    if (days > 0) {
                        endingCampaigns.add(endingReCampaign);
                    }
                }

                if (endingCampaigns.size() > 0 && endingCampaigns.size() <= 3) {
                    rewardEnding.setVisibility(View.VISIBLE);
                    endingCamp = new AdapterForShowRewardCampaign(Home_Page.this, endingCampaigns, R.color.gray);
                    rewardEnding.setAdapter(endingCamp);
                    noCampReEn.setVisibility(View.GONE);
                } else if (endingCampaigns.size() > 3) {
                    endingShowCamps.clear();
                    for (int i = 0; i < 3; i++) {
                        endingShowCamps.add(endingCampaigns.get(i));
                    }
                    rewardEnding.setVisibility(View.VISIBLE);
                    endingCamp = new AdapterForShowRewardCampaign(Home_Page.this, endingShowCamps, R.color.gray);
                    rewardEnding.setAdapter(endingCamp);
                    noCampReEn.setVisibility(View.GONE);

                } else {
                    rewardEnding.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRewardCampNewest() {
        final ArrayList<RewardCampaign> rearrange = new ArrayList<>();
        rewardNewest = findViewById(R.id.list_reward_newest);
        rewardTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                newestCampaigns.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    newestReCampaign = snapshot.getValue(RewardCampaign.class);
                    sDate = newestReCampaign.getStartDate();
                    eDate = newestReCampaign.getEndDate();
                    try {
                        c.setTime(format.parse(sDate));
                        e.setTime(format.parse(eDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentDate = Calendar.getInstance().getTime();
                    startDate = c.getTime();
                    endDate = e.getTime();
                    diff = endDate.getTime() - currentDate.getTime();
                    seconds = diff / 1000;
                    minutes = seconds / 60;
                    hours = minutes / 60;
                    days = hours / 24;
                    if (days > 0) {
                        diff = currentDate.getTime() - startDate.getTime();
                        seconds = diff / 1000;
                        minutes = seconds / 60;
                        hours = minutes / 60;
                        days = hours / 24;
                        if (days < 8) {
                            newestCampaigns.add(newestReCampaign);
                        }
                    }
                }

                if (newestCampaigns.size() > 0 && newestCampaigns.size() <= 3) {
                    rearrange.clear();
                    for (int i = 0; i < newestCampaigns.size(); i++) {
                        int x = (newestCampaigns.size() - 1 - i);
                        rearrange.add(newestCampaigns.get(x));
                    }
                    newestCamp = new AdapterForShowRewardCampaign(Home_Page.this, rearrange, R.color.yellow);
                    rewardNewest.setAdapter(newestCamp);
                    rewardNewest.setVisibility(View.VISIBLE);
                    noCampReNe.setVisibility(View.GONE);
                } else if (newestCampaigns.size() > 3) {
                    rearrange.clear();
                    for (int i = 0; i < 3; i++) {
                        int x = (newestCampaigns.size() - 1 - i);
                        rearrange.add(newestCampaigns.get(x));
                    }
                    newestCamp = new AdapterForShowRewardCampaign(Home_Page.this, rearrange, R.color.yellow);
                    rewardNewest.setAdapter(newestCamp);
                    rewardNewest.setVisibility(View.VISIBLE);
                    noCampReNe.setVisibility(View.GONE);

                } else {
                    rewardNewest.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initEquityCampSuccess() {
        equitySuccess = findViewById(R.id.list_equity_success);
        equityTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                successEqCampaigns.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    successEqCampaign = snapshot.getValue(EquityCampaign.class);
                    eDate = successEqCampaign.getEndDate();
                    try {
                        c.setTime(format.parse(eDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentDate = Calendar.getInstance().getTime();
                    endDate = c.getTime();
                    diff = endDate.getTime() - currentDate.getTime();
                    seconds = diff / 1000;
                    minutes = seconds / 60;
                    hours = minutes / 60;
                    days = hours / 24;
                    if (days > 0 && (successEqCampaign.getNeededMoney() <= successEqCampaign.getFundedMoney())) {
                        successEqCampaigns.add(successEqCampaign);
                    }

                }
                if (successEqCampaigns.size() > 0 && successEqCampaigns.size() <= 3) {
                    equitySuccess.setVisibility(View.VISIBLE);
                    successEqCamp = new AdapterForShowEquityCampaign(Home_Page.this, successEqCampaigns, R.color.darkBlue);
                    equitySuccess.setAdapter(successEqCamp);
                    noCampEqSu.setVisibility(View.GONE);
                } else if (successEqCampaigns.size() > 3) {
                    successEqShowCamps.clear();
                    for (int i = 0; i < 3; i++) {
                        successEqShowCamps.add(successEqCampaigns.get(i));
                    }
                    equitySuccess.setVisibility(View.VISIBLE);
                    successEqCamp = new AdapterForShowEquityCampaign(Home_Page.this, successEqCampaigns, R.color.darkBlue);
                    equitySuccess.setAdapter(successEqCamp);
                    noCampEqSu.setVisibility(View.GONE);

                } else {
                    equitySuccess.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //setup view pager for equity campaign for slide campaign
    private void initEquityCampEnding() {
        equityEnding = findViewById(R.id.list_equity_ending);
        equityTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                endingEqCampaigns.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    endingEqCampaign = snapshot.getValue(EquityCampaign.class);
                    eDate = endingEqCampaign.getEndDate();
                    try {
                        c.setTime(format.parse(eDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentDate = Calendar.getInstance().getTime();
                    endDate = c.getTime();
                    diff = endDate.getTime() - currentDate.getTime();
                    seconds = diff / 1000;
                    minutes = seconds / 60;
                    hours = minutes / 60;
                    days = hours / 24;
                    Log.v("day", Long.toString(days));
                    if (days > 0 & days < 8) {
                        endingEqCampaigns.add(endingEqCampaign);
                    }
                }

                if (endingEqCampaigns.size() > 0 && endingEqCampaigns.size() <= 3) {
                    equityEnding.setVisibility(View.VISIBLE);
                    endingEqCamp = new AdapterForShowEquityCampaign(Home_Page.this, endingEqCampaigns, R.color.gray);
                    equityEnding.setAdapter(endingEqCamp);
                    noCampEqEn.setVisibility(View.GONE);
                } else if (endingEqCampaigns.size() > 3) {
                    endingEqShowCamps.clear();
                    for (int i = 0; i < 3; i++) {
                        endingEqShowCamps.add(endingEqCampaigns.get(i));
                    }
                    equityEnding.setVisibility(View.VISIBLE);

                    endingEqCamp = new AdapterForShowEquityCampaign(Home_Page.this, endingEqShowCamps, R.color.gray);
                    equityEnding.setAdapter(endingEqCamp);
                    noCampEqEn.setVisibility(View.GONE);

                } else {
                    equityEnding.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initEquityCampNewest() {
        final ArrayList<EquityCampaign> rearrange = new ArrayList<>();
        equityNewest = findViewById(R.id.list_equity_newest);
        equityTable.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                newestEqCampaigns.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    newestEqCampaign = snapshot.getValue(EquityCampaign.class);
                    eDate = newestEqCampaign.getEndDate();
                    sDate = newestEqCampaign.getStartDate();
                    try {
                        e.setTime(format.parse(sDate));
                        c.setTime(format.parse(eDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    currentDate = Calendar.getInstance().getTime();
                    endDate = c.getTime();
                    diff = endDate.getTime() - currentDate.getTime();
                    startDate = e.getTime();
                    seconds = diff / 1000;
                    minutes = seconds / 60;
                    hours = minutes / 60;
                    days = hours / 24;
                    if (days > 0) {
                        diff = currentDate.getTime() - startDate.getTime();
                        seconds = diff / 1000;
                        minutes = seconds / 60;
                        hours = minutes / 60;
                        days = hours / 24;
                        if (days < 8) {
                            newestEqCampaigns.add(newestEqCampaign);
                        }
                    }

                }

                if (newestEqCampaigns.size() > 0 && newestEqCampaigns.size() <= 3) {
                    rearrange.clear();
                    for (int i = 0; i < newestEqCampaigns.size(); i++) {
                        int x = (newestEqCampaigns.size() - 1 - i);
                        rearrange.add(newestEqCampaigns.get(x));
                    }
                    newestEqCamp = new AdapterForShowEquityCampaign(Home_Page.this, rearrange, R.color.yellow);
                    equityNewest.setAdapter(newestEqCamp);
                    noCampEqNe.setVisibility(View.GONE);
                    equityNewest.setVisibility(View.VISIBLE);
                } else if (newestEqCampaigns.size() > 3) {
                    rearrange.clear();
                    for (int i = 0; i < 3; i++) {
                        int x = (newestEqCampaigns.size() - 1 - i);
                        rearrange.add(newestEqCampaigns.get(x));
                    }
                    newestEqCamp = new AdapterForShowEquityCampaign(Home_Page.this, rearrange, R.color.yellow);
                    equityNewest.setAdapter(newestEqCamp);
                    noCampEqNe.setVisibility(View.GONE);
                    equityNewest.setVisibility(View.VISIBLE);
                } else {
                    equityNewest.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //set up toolbar and side drawer
    private void setupDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        } else if (id == R.id.sign_up) {
            Intent signUpPage = new Intent(this, SignUp.class);
            startActivity(signUpPage);
        } else if (id == R.id.help_community) {
            Intent HelpPage = new Intent(this, HelpingCommunity.class);
            startActivity(HelpPage);
        } else if (id == R.id.help) {

        } else if (id == R.id.about_us) {
            PrefManager prefManager = new PrefManager(getApplicationContext());
            // make first time launch TRUE
            prefManager.setFirstTimeLaunch(true);
            startActivity(new Intent(this, WelcomePage.class));
        } else if (id == R.id.logout) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.removeHeaderView(navigationView.getHeaderView(0));

            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.sign_up).setVisible(true);
            FirebaseAuth.getInstance().signOut();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //close app
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void goToRewardCampaign(View view) {
        Intent rewardPage = new Intent(this, Reward_campaign_Home_page.class);
        startActivity(rewardPage);
    }

    public void goToEquityCampaign(View view) {
        Intent equityPage = new Intent(this, Equity_campaign_Home_page.class);
        startActivity(equityPage);
    }

    public void goToShop(View view) {
        Intent shopPage = new Intent(this, Shop_Page.class);
        startActivity(shopPage);
    }

    public void openExpertChat(View view) {
        Intent expertChat = new Intent(this, TalkToExpert.class);
        startActivity(expertChat);
    }

    public void goToHelpCommunity(View view) {
        Intent helpCommunityPage = new Intent(this, HelpingCommunity.class);
        startActivity(helpCommunityPage);
    }

    public void goToJobPage(View view) {
        Intent jobPage = new Intent(this, Job.class);
        startActivity(jobPage);
    }

    public void aboutUs(View view) {
        PrefManager prefManager = new PrefManager(getApplicationContext());
        // make first time launch TRUE
        prefManager.setFirstTimeLaunch(true);
        startActivity(new Intent(this, WelcomePage.class));
    }
}
