package com.example.shaza.graduationproject.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shaza.graduationproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TalkToExpert extends AppCompatActivity {

    private com.example.shaza.graduationproject.Database.Table.TalkToExpert talkToExpert;
    private DatabaseReference databaseReference;
    private EditText msg, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk_to_expert);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.6));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Message from users");
        msg = findViewById(R.id.send_message);
        mail = findViewById(R.id.mail_to_expert);
        talkToExpert = new com.example.shaza.graduationproject.Database.Table.TalkToExpert();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.close_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.close_button) {
            finish();
        }
        return true;
    }

    public void sendMsg(View view) {
        String email, message, errorMSG;
        email = mail.getText().toString();
        message = msg.getText().toString();
        errorMSG = "Can't be empty field";
        if (email.equals("")) {
            mail.setError(errorMSG);
        } else if (message.equals("")) {
            msg.setError(errorMSG);
        } else {
            talkToExpert.setMail(email);
            talkToExpert.setMsg(message);
            String id = databaseReference.push().getKey();
            databaseReference.child(id).setValue(talkToExpert).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(TalkToExpert.this, "Message sent", Toast.LENGTH_LONG).show();
                }
            });
            mail.setText("");
            msg.setText("");
        }
    }
}
