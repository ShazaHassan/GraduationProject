package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.shaza.graduationproject.TemplateForAdapter.AnswerList;
import com.example.shaza.graduationproject.Adapters.Answer_List_Adapter;
import com.example.shaza.graduationproject.R;

import java.util.ArrayList;

public class CertainQuestion extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    private Answer_List_Adapter adapter;
    Button button;
    private ArrayList<AnswerList> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certain_question_helping_community);
        button = findViewById(R.id.button6);
        button.setOnClickListener(this);
        listView = findViewById(R.id.List_of_Answers);

        list.add(new AnswerList("Answer1"));
        list.add(new AnswerList("Answer2"));
        list.add(new AnswerList("Answer3"));
        list.add(new AnswerList("Answer4"));
        list.add(new AnswerList("bbbbbb"));
        list.add(new AnswerList("bbbbbb"));
        list.add(new AnswerList("bbbbbb"));
        list.add(new AnswerList("bbbbbb"));
        list.add(new AnswerList("bbbbbb"));

        adapter = new Answer_List_Adapter(list);
        listView.setAdapter(adapter);

        setupDrawer();
    }
    private void setupDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

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
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        Intent AnswerPage = new Intent(this,AddAnswerPage.class);
        startActivity(AnswerPage);

    }
}
