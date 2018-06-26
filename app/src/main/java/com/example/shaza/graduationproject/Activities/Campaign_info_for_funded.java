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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shaza.graduationproject.Database.Table.Users;
import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.RoundImageByPicasso.CircleTransform;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Campaign_info_for_funded extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int[] img = {R.drawable.aa, R.drawable.ba148f888900f93996a2e2eabb7750a7, R.drawable.welcom_img};
    private static final String[] texts = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta. Mauris massa.  "
            , "Sed dignissim lacinia nunc. Curabitur tortor. Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem. Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum. "
            , "Nulla metus metus, ullamcorper vel, tincidunt sed, euismod in, nibh. Quisque volutpat condimentum velit. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam nec ante. Sed lacinia, urna non tincidunt mattis, tortor neque adipiscing diam, a cursus ipsum ante quis turpis. Nulla facilisi. Ut fringilla. Suspendisse potenti. Nunc feugiat mi a tellus consequat imperdiet. Vestibulum sapien. Proin quam. Etiam ultrices. "};
    private static final String[] campaignName = {"Camp1", "Camp2", "Camp3"};
    private static final String[] noOfDays = {"2 days left", "3 days left", "1 day left"};
    private static final String[] need = {"1$", "0$", "6$"};
    private static final int[] total = {5, 3, 12};
    private static final int[] get = {4, 3, 6};
    private int pos = 0;

    private NavigationView navView;
    private FirebaseUser user;
    private View header;
    private TextView name, email;
    private Menu menu;
    private String idDatabase;
    private DatabaseReference userTable;
    private FirebaseDatabase database;
    private String userName, e_mail, gender;
    private Users users;
    private ImageView pp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_info_for_creator);
        Intent intent = getIntent();
        pos = intent.getExtras().getInt("id");
        setItems();
        setupDrawer();
        ImageView creatorImage = findViewById(R.id.img_camp_creator);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_female_user);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        creatorImage.setImageDrawable(roundedBitmapDrawable);

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
            startActivity(new Intent(this, Home_Page.class));
        }
    }

    private void setHeaderDrawer() {
        header = navView.getHeaderView(0);
        idDatabase = user.getUid();
        name = header.findViewById(R.id.name_at_header);
        email = header.findViewById(R.id.mail_at_header);
        pp = header.findViewById(R.id.profile_image_at_header);
        userTable.child(idDatabase).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users = dataSnapshot.getValue(Users.class);
                userName = users.getFirstName() + " " + users.getLastName();
                e_mail = users.getEmail();
                name.setText(userName);
                email.setText(e_mail);

                if (!dataSnapshot.hasChild("Profile Img")) {
                    gender = users.getGender();
                    Log.v("gender", gender);
                    makeProfilePic(gender);
                } else {
                    String imageUrl = dataSnapshot.child("Profile Img").getValue().toString();
                    Picasso.get().load(imageUrl).transform(new CircleTransform()).into(pp);
                }
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
    private void setItems() {
        TextView campName = findViewById(R.id.campaign_name_creator);
        ImageView imgCamp = findViewById(R.id.campaign_image);
        TextView descCamp = findViewById(R.id.description_of_campaign);
        TextView daysLeft = findViewById(R.id.days_left_for_creator);
        TextView need = findViewById(R.id.need_money);
        ProgressBar progressForPercentage = findViewById(R.id.progress_bar_for_show_percentage);
        TextView percentage = findViewById(R.id.percentage_for_creator);
        campName.setText(campaignName[pos]);
        imgCamp.setImageResource(img[pos]);
        descCamp.setText(texts[pos]);
        daysLeft.setText(noOfDays[pos]);
        need.setText("need " + this.need[pos]);
        int percentageCalculation = (int) ((((float) get[pos] / (float) total[pos])) * 100);
        progressForPercentage.setMax(100);
        progressForPercentage.setProgress(percentageCalculation);
        percentage.setText(percentageCalculation + "%");

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

    void displayImage(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        pp.setImageDrawable(roundedBitmapDrawable);
    }
}
