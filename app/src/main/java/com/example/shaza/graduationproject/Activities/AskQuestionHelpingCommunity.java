package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Database.Table.Users;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;

public class AskQuestionHelpingCommunity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText editText_title, editText_body, editText_categoery;
    Button button;

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
        setContentView(R.layout.activity_ask_question_helping_community);

        editText_title = findViewById(R.id.text5);
        editText_body = findViewById(R.id.editText6);
        editText_categoery = findViewById(R.id.editText4);
        button = findViewById(R.id.button);
        setupDrawer();

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
                    new DownloadImage().execute(imageUrl);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        header.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pp = new Intent(AskQuestionHelpingCommunity.this, Personal_Page.class);
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


    public void psotQuestion(){
      // on vlick remove data from this activity to list view in helping community
        // andd numbering in questions and answers
       //   Intent Ask = new Intent(AskQuestionHelpingCommunity.this, HelpingCommunity.class);
         // startActivity(Ask);
    }
    private void setupDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        //Drawer of navigation bar icon
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
            FirebaseAuth.getInstance().signOut();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void displayImage(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        pp.setImageDrawable(roundedBitmapDrawable);
    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        private Exception exception;

        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                return bmp;
            } catch (Exception e) {
                this.exception = e;

                return null;
            } finally {
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            // TODO: check this.exception
            // TODO: do something with the feed
            super.onPostExecute(bitmap);
            displayImage(bitmap);
        }
    }
}
