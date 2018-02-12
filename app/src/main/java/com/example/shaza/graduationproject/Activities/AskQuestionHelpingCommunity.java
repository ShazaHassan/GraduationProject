package com.example.shaza.graduationproject.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shaza.graduationproject.R;

public class AskQuestionHelpingCommunity extends AppCompatActivity{

    EditText editText_title, editText_body, editText_categoery;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question_helping_community);

        editText_title = findViewById(R.id.text5);
        editText_body = findViewById(R.id.editText6);
        editText_categoery = findViewById(R.id.editText4);
        button = findViewById(R.id.button);
    }

    public void psotQuestion(){

    }
}
