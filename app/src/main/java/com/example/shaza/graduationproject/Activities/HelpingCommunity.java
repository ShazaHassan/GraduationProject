package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


import com.example.shaza.graduationproject.R;


import com.example.shaza.graduationproject.TemplateForAdapter.QuestionList;
import com.example.shaza.graduationproject.R;
import com.example.shaza.graduationproject.Adapters.Question_List_Adapters;

import java.util.ArrayList;

public class HelpingCommunity extends AppCompatActivity  {
    private ListView listView;
    private Question_List_Adapters adapter;
    private ArrayList<QuestionList> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helping_community);
        listView = findViewById(R.id.List_of_Questions);

        list.add(new QuestionList("How to create new activity","android", "5 mins ago"));
        list.add(new QuestionList("How to add item to arraylist","Java", "2 days ago"));
        list.add(new QuestionList("How to eat 10 ton of pizza in 5 mins :'D","Mariam", "1 year ago"));
        list.add(new QuestionList("How to create new activity","android", "10 mins ago"));list.add(new QuestionList("How to create new activity","android", "5 mins ago"));
        list.add(new QuestionList("How to add item to arraylist","Java", "2 days ago"));
        list.add(new QuestionList("How to eat 10 ton of pizza in 5 mins :'D","Mariam", "1 year ago"));
        list.add(new QuestionList("How to create new activity","android", "10 mins ago"));


        adapter = new Question_List_Adapters(list);
        listView.setAdapter(adapter);

       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent goToAnswer = new Intent(view.getContext(),CertainQuestionHelpingCommunity.class);
                startActivity(goToAnswer); //start activity for result
            }
        });
*/
        setupDrawer();

    }
    private void setupDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        //Drawer of navigation bar icon
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/
    }



   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
       // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.menu_scrolling, menu);

       return true;
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        else if (id == R.id.action_Ask){
            Intent Ask = new Intent(this, AskQuestionHelpingCommunity.class);
            startActivity(Ask);
        }
        return super.onOptionsItemSelected(item);
    }
}
