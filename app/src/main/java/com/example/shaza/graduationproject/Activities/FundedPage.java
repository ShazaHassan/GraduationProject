package com.example.shaza.graduationproject.Activities;

import android.content.Intent;
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

import com.example.shaza.graduationproject.Database.Table.CampaignType;
import com.example.shaza.graduationproject.Database.Table.EquityCampaign;
import com.example.shaza.graduationproject.Database.Table.RewardCampaign;
import com.example.shaza.graduationproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FundedPage extends AppCompatActivity {

    private String idCampDb, type, idUserDb;
    private FirebaseUser currentUser;
    private DatabaseReference rewardTable, userTable, equityTable;
    private EditText amountOfMoney;
    private RewardCampaign rewardCampaign;
    private EquityCampaign equityCampaign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funded_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        idCampDb = intent.getExtras().getString("id");
        type = intent.getExtras().getString("type");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        idUserDb = currentUser.getUid();
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        amountOfMoney = findViewById(R.id.amount_of_money_for_payment);
        if (type.equals("reward")) {
            rewardTable = FirebaseDatabase.getInstance().getReference().child("Reward Campaign");
        } else if (type.equals("equity")) {
            equityTable = FirebaseDatabase.getInstance().getReference().child("Equity Campaign");
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * 0.9), (int) (height * 0.5));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

    public void PayProcess(View view) {
        if (amountOfMoney.getText().equals("")) {
            amountOfMoney.setError("enter amount of money");
        } else {
            if (type.equals("reward")) {
                rewardTable.child(idCampDb).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        rewardCampaign = dataSnapshot.getValue(RewardCampaign.class);
                        long amountOfFunded = rewardCampaign.getFundedMoney();
                        long noOfFunded = rewardCampaign.getNoOfFunded();
                        long amount = Long.parseLong(amountOfMoney.getText().toString());
                        amountOfFunded = amountOfFunded + amount;
                        noOfFunded++;
                        rewardTable.child(idCampDb).child("fundedMoney").setValue(amountOfFunded);
                        rewardTable.child(idCampDb).child("noOfFunded").setValue(noOfFunded);
                        userTable.child(idUserDb).child("FundedCampaign").child(idCampDb).setValue(new CampaignType("Reward", idCampDb));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else if (type.equals("equity")) {
                equityTable.child(idCampDb).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        equityCampaign = dataSnapshot.getValue(EquityCampaign.class);
                        long amountOfFunded = equityCampaign.getFundedMoney();
                        long noOfFunded = equityCampaign.getNoOfFunded();
                        long amount = Long.parseLong(amountOfMoney.getText().toString());
                        amountOfFunded = amountOfFunded + amount;
                        noOfFunded++;
                        equityTable.child(idCampDb).child("fundedMoney").setValue(amountOfFunded);
                        equityTable.child(idCampDb).child("noOfFunded").setValue(noOfFunded);
                        userTable.child(idUserDb).child("FundedCampaign").child(idCampDb).setValue(new CampaignType("Equity", idCampDb));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}
