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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaza.graduationproject.Adapters.Question_List_Adapters;
import com.example.shaza.graduationproject.Database.Table.Question;
import com.example.shaza.graduationproject.Database.Table.Users;
import com.example.shaza.graduationproject.PrefManager;
import com.example.shaza.graduationproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HelpingCommunity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ListView listView;
    private Question_List_Adapters adapter;
    Date startDate;

    private NavigationView navView;
    private FirebaseUser user;
    private View header;
    private TextView name, email;
    private Menu menu;
    private String idDatabase;
    RelativeLayout searchResult;
    private FirebaseDatabase database;
    private String userName, e_mail, gender;
    private Users users;
    private ImageView pp;
    ArrayList<Question> questionsList = new ArrayList<>();
    TextView noResult;
    private ArrayList<Question> questions = new ArrayList<>();
    private DatabaseReference userTable, questionTable;
    private EditText question;
    private Spinner quesCategory;
    private String ques, category, idQuestDB, publishDate;
    private TextView noQuest;
    private Question questionData, questionSearch;
    private Question_List_Adapters questionAdapters;
    private LinearLayout app;
    private ListView listSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helping_community);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        listView = findViewById(R.id.List_of_Questions);
        question = findViewById(R.id.write_post);
        quesCategory = findViewById(R.id.spinner_cat);
        noQuest = findViewById(R.id.no_quest);
        questionData = new Question();
        app = findViewById(R.id.app);
        listSearch = findViewById(R.id.list_search);
        searchResult = findViewById(R.id.search_result);
        noResult = findViewById(R.id.no_result);
        setupDrawer();
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        menu = navView.getMenu();
        users = new Users();
        database = FirebaseDatabase.getInstance();
        userTable = database.getReference().child("Users");
        questionTable = database.getReference().child("Question");

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            setHeaderDrawer();
        } else {
            navView.removeHeaderView(navView.getHeaderView(0));
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.sign_up).setVisible(true);
        }
        showQuestion();

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
                Intent pp = new Intent(HelpingCommunity.this, Personal_Page.class);
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

    void displayImage(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        pp.setImageDrawable(roundedBitmapDrawable);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.category_for_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String cat = "category";
        Intent catPage = new Intent(this, CategoryForQuestion.class);
        if (id == R.id.all_questions) {
            catPage.putExtra(cat, "All questions");
            startActivity(catPage);
        } else if (id == R.id.art) {
            catPage.putExtra(cat, "Art");
            startActivity(catPage);
        } else if (id == R.id.craft) {
            catPage.putExtra(cat, "Craft");
            startActivity(catPage);
        } else if (id == R.id.design) {
            catPage.putExtra(cat, "Design");
            startActivity(catPage);
        } else if (id == R.id.fashion) {
            catPage.putExtra(cat, "Fashion");
            startActivity(catPage);
        } else if (id == R.id.film_and_video) {
            catPage.putExtra(cat, "Film and Video");
            startActivity(catPage);
        } else if (id == R.id.games) {
            catPage.putExtra(cat, "Games");
            startActivity(catPage);
        } else if (id == R.id.health) {
            catPage.putExtra(cat, "Health");
            startActivity(catPage);
        } else if (id == R.id.productivity) {
            catPage.putExtra(cat, "Productivity");
            startActivity(catPage);
        } else if (id == R.id.food) {
            catPage.putExtra(cat, "Food");
            startActivity(catPage);
        } else if (id == R.id.search_icon) {
            final SearchView searchView = (SearchView) item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(final String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(final String newText) {
                    if (newText.equals("")) {
                        app.setVisibility(View.VISIBLE);
                        searchResult.setVisibility(View.GONE);
                    } else {
                        app.setVisibility(View.GONE);
                        searchResult.setVisibility(View.VISIBLE);
                        noResult.setVisibility(View.VISIBLE);
                        questionTable.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                questionsList.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    questionSearch = snapshot.getValue(Question.class);
                                    String name = questionSearch.getQuestion();
                                    if (name.equals(newText) || name.contains(newText)) {
                                        questionsList.add(questionSearch);
                                    }
                                }
                                if (questionsList.size() > 0) {
                                    noResult.setVisibility(View.GONE);
                                    app.setVisibility(View.GONE);
                                    searchResult.setVisibility(View.VISIBLE);
                                    Question_List_Adapters adapter = new Question_List_Adapters(HelpingCommunity.this,
                                            questionsList);
                                    listSearch.setAdapter(adapter);
                                    listSearch.setVisibility(View.VISIBLE);

                                } else {
                                    searchResult.setVisibility(View.VISIBLE);
                                    noResult.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    return true;

                }
            });
        }
        return true;
    }


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

    public void openExpertChat(View view) {
        Intent expertChat = new Intent(this, TalkToExpert.class);
        startActivity(expertChat);
    }

    public void publishQuestion(View view) {
        getItemFromFields();
    }

    private void getItemFromFields() {
        ques = question.getText().toString();
        category = quesCategory.getSelectedItem().toString();
        checkItem();
    }

    private void checkItem() {
        if (ques.equals("")) {
            question.setError("Can't be empty field");
        } else if (category.equals("Select category")) {
            Toast.makeText(this, "Please select category", Toast.LENGTH_LONG).show();
        } else if (user == null) {
            Toast.makeText(this, "Please signIn First", Toast.LENGTH_LONG).show();
        } else {
            saveDataOnDB();
        }
    }

    private void saveDataOnDB() {
        startDate = Calendar.getInstance().getTime();
        publishDate = startDate.toString();
        idQuestDB = questionTable.push().getKey();
        setDataForDB();
        userTable.child(idDatabase).child("Question").child(idQuestDB).setValue(idQuestDB);
        questionTable.child(idQuestDB).setValue(questionData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(HelpingCommunity.this, "question publish success", Toast.LENGTH_LONG).show();
            }
        });
        question.setText("");
        quesCategory.setSelection(0);
    }


    private void setDataForDB() {
        questionData.setQuestion(ques);
        questionData.setCategory(category);
        questionData.setCreatorID(idDatabase);
        questionData.setPublishDate(publishDate);
        questionData.setQuestionID(idQuestDB);
        questionData.setUserName(userName);
    }

    private void showQuestion() {
        questionTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questions.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    questions.add(snapshot.getValue(Question.class));
                }
                if (questions.size() > 0) {
                    noQuest.setVisibility(View.GONE);
                    questionAdapters = new Question_List_Adapters(HelpingCommunity.this, questions);
                    listView.setAdapter(questionAdapters);
                } else {
                    listView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
