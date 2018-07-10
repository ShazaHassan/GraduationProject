package com.example.shaza.graduationproject.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shaza.graduationproject.Adapters.Question_List_Adapters;
import com.example.shaza.graduationproject.Database.Table.Question;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourQuestions extends Fragment {

    private ArrayList<Question> questions = new ArrayList<>();
    private DatabaseReference userTable, questionTable;
    private FirebaseUser currentUser;
    private String idUserDB, idQuesDB;
    private ArrayList<String> quesIDs = new ArrayList<>();
    private ListView list;
    private TextView noQuestToShow;
    private Question question;
    private Question_List_Adapters adapter;

    public YourQuestions() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_question, container, false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        idUserDB = currentUser.getUid();
        list = rootView.findViewById(R.id.list);
        noQuestToShow = rootView.findViewById(R.id.no_quest_text_view);
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        questionTable = FirebaseDatabase.getInstance().getReference().child("Question");
        getDataFromDB();
        return rootView;
    }

    private void getDataFromDB() {
        userTable.child(idUserDB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Question")) {
                    userTable.child(idUserDB).child("Question").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                idQuesDB = snapshot.getValue().toString();
                                quesIDs.add(idQuesDB);
                                Log.v("product id", idQuesDB);
                            }
                            if (quesIDs.size() > 0) {
                                getProducts(quesIDs);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getProducts(final ArrayList<String> prodIDs) {
        questionTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questions.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    question = snapshot.getValue(Question.class);
                    idQuesDB = question.getQuestionID();
                    for (String id : prodIDs) {
                        if (idQuesDB.equals(id)) {
                            questions.add(question);
                        }
                    }

                }
                if (questions.size() > 0) {
                    adapter = new Question_List_Adapters(getContext(), questions);
                    list.setAdapter(adapter);
                    noQuestToShow.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                    Log.v("camsizecampfun", Integer.toString(questions.size()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
