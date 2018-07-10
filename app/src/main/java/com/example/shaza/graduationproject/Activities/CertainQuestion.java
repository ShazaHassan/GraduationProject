package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaza.graduationproject.Adapters.Answer_List_Adapter;
import com.example.shaza.graduationproject.Database.Table.Answer;
import com.example.shaza.graduationproject.Database.Table.Question;
import com.example.shaza.graduationproject.Database.Table.Users;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CertainQuestion extends AppCompatActivity {


    private FirebaseUser user;
    private String idDatabase, idQuest, idAnswer;
    private DatabaseReference userTable, questionTable, answerTable;
    private FirebaseDatabase database;
    private Users users;
    private ImageView pp;
    private Question question;
    private TextView ques, time, cat, creator, noAnswer;
    private EditText comment;
    private Answer answer;
    private ListView listAnswer;
    private ArrayList<Answer> answers = new ArrayList<>();
    private Answer_List_Adapter adapter;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certain_question_helping_community);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent intent = getIntent();
        idQuest = intent.getStringExtra("id");
        users = new Users();
        database = FirebaseDatabase.getInstance();
        userTable = database.getReference().child("Users");
        questionTable = database.getReference().child("Question");
        answerTable = database.getReference().child("Answer");
        user = FirebaseAuth.getInstance().getCurrentUser();
        answer = new Answer();
        listAnswer = findViewById(R.id.List_of_Answers);
        noAnswer = findViewById(R.id.no_comment);
        if (user != null) {
            idDatabase = user.getUid();
            userTable.child(idDatabase).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userName = dataSnapshot.getValue(Users.class).getFirstName()
                            + " " + dataSnapshot.getValue(Users.class).getLastName();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        ques = findViewById(R.id.question);
        time = findViewById(R.id.q_time);
        cat = findViewById(R.id.q_cat);
        creator = findViewById(R.id.owner_question);
        comment = findViewById(R.id.write_comment);
        getQuestion();
    }

    private void getQuestion() {
        questionTable.child(idQuest).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                question = dataSnapshot.getValue(Question.class);
                ques.setText(question.getQuestion());
                cat.setText(question.getCategory());
                calculateTime(question);
                creatorName(question);
                if (dataSnapshot.hasChild("Answer")) {
                    getAnswers();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAnswers() {
        questionTable.child(idQuest).child("Answer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                answers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    answers.add(snapshot.getValue(Answer.class));
                }
                if (answers.size() > 0) {
                    noAnswer.setVisibility(View.GONE);
                    adapter = new Answer_List_Adapter(CertainQuestion.this, answers);
                    listAnswer.setAdapter(adapter);
                } else {
                    listAnswer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    private void calculateTime(Question question) {
        Calendar c = Calendar.getInstance();
        Date current, publish;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
        current = Calendar.getInstance().getTime();
        try {
            c.setTime(dateFormat.parse(question.getPublishDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        publish = c.getTime();
        long diff = current.getTime() - publish.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days == 0) {
            if (hours == 0) {
                time.setText(Long.toString(minutes) + " minutes Ago");
            } else {
                time.setText(Long.toString(hours) + " hours Ago");
            }
        } else if (days < 30) {
            time.setText(Long.toString(days) + " days ago");
        } else if (days == 30 || days < 30 * 2) {
            time.setText("one month ago");
        } else if (days == 30 * 2 || days < 30 * 3) {
            time.setText("two months ago");
        } else if (days == 30 * 3 || days < 30 * 4) {
            time.setText("three months ago");
        } else if (days == 30 * 4 || days < 30 * 5) {
            time.setText("four months ago");
        } else if (days == 30 * 5 || days < 30 * 6) {
            time.setText("five months ago");
        } else if (days == 30 * 6 || days < 30 * 7) {
            time.setText("six months ago");
        } else if (days == 30 * 7 || days < 30 * 8) {
            time.setText("seven months ago");
        } else if (days == 30 * 8 || days < 30 * 9) {
            time.setText("eight months ago");
        } else if (days == 30 * 9 || days < 30 * 10) {
            time.setText("nine months ago");
        } else if (days == 30 * 10 || days < 30 * 11) {
            time.setText("ten months ago");
        } else if (days == 30 * 11 || days < 30 * 12) {
            time.setText("eleven month ago");
        } else if (days == 30 * 12 || days < 30 * 13) {
            time.setText("one year ago");
        }
    }

    private void creatorName(Question question) {
        userTable.child(question.getCreatorID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                creator.setText(dataSnapshot.getValue(Users.class).getFirstName() + " " + dataSnapshot.getValue(Users.class).getLastName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addComment(View view) {
        if (user == null) {
            Toast.makeText(this, "Please Login first", Toast.LENGTH_LONG).show();
        } else if (comment.getText().toString().equals("")) {
            comment.setError("Can't be empty field");
        } else {
            idAnswer = answerTable.push().getKey();
            answer.setAnswer(comment.getText().toString());
            answer.setCreatorID(idDatabase);
            answer.setAnswerID(idAnswer);
            answer.setUserName(userName);
            Date publishDate = Calendar.getInstance().getTime();
            answer.setTime(publishDate.toString());
            questionTable.child(idQuest).child("Answer").child(idAnswer).setValue(answer).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(CertainQuestion.this, "Complete", Toast.LENGTH_LONG).show();
                }
            });
            userTable.child(idDatabase).child("Answer").child(idAnswer).setValue(idAnswer);
            comment.setText("");
        }
    }
}