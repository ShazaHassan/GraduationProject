package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.Question_List_Adapters;
import com.example.shaza.graduationproject.Database.Table.Question;
import com.example.shaza.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryForQuestion extends AppCompatActivity {

    private TextView noQuestion;
    private ListView list;
    private String category;
    private DatabaseReference questionTable;
    private ArrayList<Question> questions = new ArrayList<>();
    private Question_List_Adapters showQuestion;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_question);

        noQuestion = findViewById(R.id.no_quest_text_view);
        list = findViewById(R.id.list);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        setTitle(category);
        Log.v("size", category);
        questionTable = FirebaseDatabase.getInstance().getReference().child("Question");
        getDataFromDBQuestion();
    }

    private void getDataFromDBQuestion() {
        questionTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questions.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.v("size", "elements");
                    question = snapshot.getValue(Question.class);
                    if (category.equals("All questions")) {
                        Log.v("size", "enter here");
                        questions.add(question);
                    } else if (category.equals(question.getCategory())) {
                        Log.v("size", "special cat");
                        questions.add(question);
                    }
                }
                Log.v("size", Integer.toString(questions.size()));
                if (questions.size() > 0) {
                    showQuestion = new Question_List_Adapters(CategoryForQuestion.this, questions);
                    noQuestion.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    list.setAdapter(showQuestion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
