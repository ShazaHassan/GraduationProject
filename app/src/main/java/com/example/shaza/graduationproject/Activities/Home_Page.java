package com.example.shaza.graduationproject.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.MyAdapter;
import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.TemplateForAdapter.ImgAndText;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class Home_Page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MyAdapter adapter1, adapter2;
    private ViewPager mPager, mPager1;
    private static int currentPage = 0;
    private static final int[] img = {R.drawable.aa, R.drawable.ba148f888900f93996a2e2eabb7750a7, R.drawable.welcom_img};
    private static final String[] texts = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa.  "
            , "Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. "
            , "Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. "};
    private static final String[] campaignName = {"Camp1", "Camp2", "Camp3"};
    private static final String[] noOfDays = {"2 days left", "3 days left", "1 day left"};
    private static final String[] need = {"1$", "0$", "6$"};
    private static final int[] total = {5, 3, 12};
    private static final int[] get = {4, 3, 6};
    private ArrayList<ImgAndText> array = new ArrayList<ImgAndText>();
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setupDrawer();
        initRewardCampSuccess();
        initEquityCampSuccess();
        initRewardCampEnding();
        initEquityCampEnding();
        initRewardCampNewest();
        initEquityCampNewest();
        dialog = new Dialog(this);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        View header = navView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.name_at_header);
        name.setText("Shaza Hassan");
        TextView mail = (TextView) header.findViewById(R.id.mail_at_header);
        mail.setText("Shazahassan2020@gmail.com");
        ImageView pp = header.findViewById(R.id.profile_image_at_header);
        pp.setImageResource(R.drawable.unkown_user);

        header.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pp = new Intent(Home_Page.this, Personal_Page.class);
                startActivity(pp);
            }
        });
        Menu menu = navView.getMenu();
        menu.findItem(R.id.login).setVisible(false);
        menu.findItem(R.id.sign_up).setVisible(false);
        menu.findItem(R.id.logout).setVisible(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);
        return true;
    }

    //setup view pager for reward campaign for slide campaign
    private void initRewardCampSuccess() {
        for (int i = 0; i < img.length; i++)
            array.add(new ImgAndText(img[i], texts[i], campaignName[i], noOfDays[i], need[i], total[i], get[i]));

        mPager = (ViewPager) findViewById(R.id.pager_reward_success);
        adapter1 = new MyAdapter(Home_Page.this, array);
        mPager.setAdapter(adapter1);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator_reward_success);
        indicator.setViewPager(mPager);
        adapter1.notifyDataSetChanged();

//        // Auto start of viewpager
//        final Handler handler = new Handler();
//        final Runnable Update = new Runnable() {
//            public void run() {
//                if (currentPage == array.size()) {
//                    currentPage = 0;
//                }
//                else{
//                    mPager.setCurrentItem(currentPage++, true);
//                }
//
//            }
//        };
//        /*Timer swipeTimer = new Timer();
//        swipeTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(Update);
//            }
//        }, 4500, 4500);*/
    }

    private void initEquityCampSuccess() {
//        for (int i = 0; i < img.length; i++)
//            array.add(new ImgAndText(img[i], texts[i], campaignName[i], noOfDays[i], need[i], total[i], get[i]));

        mPager = (ViewPager) findViewById(R.id.pager_equity_success);
        adapter1 = new MyAdapter(Home_Page.this, array);
        mPager.setAdapter(adapter1);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator_equity_success);
        indicator.setViewPager(mPager);
        adapter1.notifyDataSetChanged();
    }

    //setup view pager for equity campaign for slide campaign
    private void initEquityCampEnding() {
        mPager1 = (ViewPager) findViewById(R.id.pager_equity_ending);
        adapter2 = new MyAdapter(Home_Page.this, array, R.color.darkBlue);
        mPager1.setAdapter(adapter2);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator_equity_ending);
        indicator.setViewPager(mPager1);
    }

    private void initRewardCampEnding() {
        mPager1 = (ViewPager) findViewById(R.id.pager_reward_ending);
        adapter2 = new MyAdapter(Home_Page.this, array, R.color.darkBlue);
        mPager1.setAdapter(adapter2);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator_reward_ending);
        indicator.setViewPager(mPager1);
    }

    private void initRewardCampNewest() {
        mPager1 = (ViewPager) findViewById(R.id.pager_reward_newest);
        adapter2 = new MyAdapter(Home_Page.this, array);
        mPager1.setAdapter(adapter2);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator_reward_newest);
        indicator.setViewPager(mPager1);
    }

    private void initEquityCampNewest() {
        mPager1 = (ViewPager) findViewById(R.id.pager_equity_newest);
        adapter2 = new MyAdapter(Home_Page.this, array);
        mPager1.setAdapter(adapter2);
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator_equity_newest);
        indicator.setViewPager(mPager1);
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
        } else if (id == R.id.sign_up) {
            Intent signUpPage = new Intent(this, SignUp.class);
            startActivity(signUpPage);
        } else if (id == R.id.help_community){
            Intent HelpPage = new Intent(this, HelpingCommunity.class);
            startActivity(HelpPage);
        }
        else if (id == R.id.help) {

        } else if (id == R.id.about_us) {

        } else if (id == R.id.logout) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.removeHeaderView(navigationView.getHeaderView(0));

            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.sign_up).setVisible(true);
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
}
