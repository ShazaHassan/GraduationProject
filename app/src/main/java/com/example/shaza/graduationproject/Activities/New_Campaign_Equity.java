package com.example.shaza.graduationproject.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shaza.graduationproject.Database.Table.CampaignType;
import com.example.shaza.graduationproject.Database.Table.EquityCampaign;
import com.example.shaza.graduationproject.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class New_Campaign_Equity extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_LOAD_Video = 2;

    private EditText campName, campDuration, campAmount, campHighlight, campTeam, camSummary,
            campTimeline, campMarket, campInvTerms, campInvDis, campOffers;

    private Spinner category;

    private String campaignName, campaignCategory, campaignDuration, campaignAmount, campaignHighlight,
            campaignTeam, campaignSummary, campaignTimeline, campaignMarket, campaignInvTerms,
            campaignInvDis, campaignOffers, sDate, eDate, daysLeft;

    private Uri imageUri;

    private Context context = this;

    private UploadTask uploadTask;

    private DatabaseReference userTable, equityCampaignTable;
    private FirebaseUser currentUser;
    private String idUserDB, idCampDB;

    private String imageName, imageURL;
    private StorageReference storageImage;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private Date startDate, endDate;
    private Calendar c = Calendar.getInstance();

    private EquityCampaign equityCampaign;
    private String tableName = "Equity Campaign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__campaign__equity);
        findItem();
        equityCampaign = new EquityCampaign();
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        equityCampaignTable = FirebaseDatabase.getInstance().getReference().child(tableName);
        storageImage = FirebaseStorage.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        idUserDB = currentUser.getUid();
    }

    private void findItem() {
        campName = findViewById(R.id.name_of_camp_editText);
        category = findViewById(R.id.camp_category);
        campDuration = findViewById(R.id.duration_of_camp_editText);
        campAmount = findViewById(R.id.amount_of_money_editText);
        campHighlight = findViewById(R.id.company_highlight_editText);
        campTeam = findViewById(R.id.executive_team_editText);
        camSummary = findViewById(R.id.summary_about_company_editText);
        campTimeline = findViewById(R.id.processed_and_timeline_editText);
        campMarket = findViewById(R.id.market_analysis_editText);
        campInvTerms = findViewById(R.id.investment_terms_editText);
        campInvDis = findViewById(R.id.investor_discussion_editText);
        campOffers = findViewById(R.id.add_offer_editText);
    }

    public void openExpertChat(View view) {
        Intent expertChat = new Intent(this, TalkToExpert.class);
        startActivity(expertChat);
    }

    public void uploadPhoto(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

//    public void uploadVideo(View view) {
//        Intent intent = new Intent();
//        intent.setType("video/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Video"), RESULT_LOAD_Video);
//    }

    public void viewAs(View view) {
        Intent viewAs = new Intent(this, View_As.class);
        startActivity(viewAs);
    }

    public void publish(View view) {
        checkEmptyOfFields();
    }

    private void getDataFromFields() {
        campaignName = campName.getText().toString();
        campaignCategory = category.getSelectedItem().toString();
        campaignDuration = campDuration.getText().toString();
        campaignAmount = campAmount.getText().toString();
        campaignHighlight = campHighlight.getText().toString();
        campaignTeam = campTeam.getText().toString();
        campaignSummary = camSummary.getText().toString();
        campaignTimeline = campTimeline.getText().toString();
        campaignMarket = campMarket.getText().toString();
        campaignInvTerms = campInvTerms.getText().toString();
        campaignInvDis = campInvDis.getText().toString();
        campaignOffers = campOffers.getText().toString();
    }

    private void checkEmptyOfFields() {
        getDataFromFields();
        String errorMSG = "Can't Be Empty Field";
        if (campaignName.equals("")) {
            campName.setError(errorMSG);
        } else if (campaignCategory.equals("Select category")) {
            Toast.makeText(this, "Select Category", Toast.LENGTH_LONG).show();
        } else if (campaignDuration.equals("")) {
            campDuration.setError(errorMSG);
        } else if (campaignAmount.equals("")) {
            campAmount.setError(errorMSG);
        } else if (campaignHighlight.equals("")) {
            campHighlight.setError(errorMSG);
        } else if (campaignTeam.equals("")) {
            campTeam.setError(errorMSG);
        } else if (campaignSummary.equals("")) {
            camSummary.setError(errorMSG);
        } else if (campaignTimeline.equals("")) {
            campTimeline.setError(errorMSG);
        } else if (campaignMarket.equals("")) {
            campMarket.setError(errorMSG);
        } else if (campaignInvTerms.equals("")) {
            campInvTerms.setError(errorMSG);
        } else if (campaignInvDis.equals("")) {
            campInvDis.setError(errorMSG);
        } else if (campaignOffers.equals("")) {
            campOffers.setError(errorMSG);
        } else if (imageUri == null) {
            Toast.makeText(this, "Please selectImg", Toast.LENGTH_LONG).show();
        } else {
            uploadImgAndURL();
        }

    }

    private void uploadImgAndURL() {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            imageName = UUID.randomUUID().toString();
            final StorageReference ref = storageImage.child("images/" + imageName);
            uploadTask = ref.putFile(imageUri);
            Task<Uri> urlTask = uploadTask
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");

                        }
                    }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            // Continue with the task to get the download URL
                            return ref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Uri downloadUri = task.getResult();
                                imageURL = downloadUri.toString();
                                startDate = Calendar.getInstance().getTime();
                                Log.v("date", startDate.toString());
                                sDate = dateFormat.format(startDate);
                                c.setTime(startDate);
                                Log.v("date", sDate);
                                int d = Calendar.DATE;
                                c.add(d, Integer.parseInt(campaignDuration));
                                Log.v("date", c.getTime().toString());
                                endDate = c.getTime();
                                eDate = dateFormat.format(endDate);
                                long diff = endDate.getTime() - startDate.getTime();
                                long seconds = diff / 1000;
                                long minutes = seconds / 60;
                                long hours = minutes / 60;
                                long days = hours / 24;
                                Log.v("date", Long.toString(days));
                                daysLeft = Long.toString(days);

                                idCampDB = equityCampaignTable.push().getKey();
                                setDataForDB();
                                userTable.child(idUserDB).child("Campaigns").child(idCampDB).setValue(new CampaignType("Equity", idCampDB));
                                equityCampaignTable.child(idCampDB).setValue(equityCampaign).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Campaign success create", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(context, Home_Page.class));
                                    }
                                });

                            } else {
                                // Handle failures
                                // ...
                            }
                        }
                    });
        }
    }

    private void setDataForDB() {
        equityCampaign.setName(campaignName);
        equityCampaign.setDuration(daysLeft);
        equityCampaign.setStartDate(sDate);
        equityCampaign.setEndDate(eDate);
        equityCampaign.setFundedMoney((long) 0);
        equityCampaign.setNoOfFunded((long) 0);
        equityCampaign.setNeededMoney(Long.parseLong(campaignAmount));
        equityCampaign.setIDCamp(idCampDB);
        equityCampaign.setIDCreator(idUserDB);
        equityCampaign.setHighlight(campaignHighlight);
        equityCampaign.setInvestDiscussion(campaignInvDis);
        equityCampaign.setInvestTerms(campaignInvTerms);
        equityCampaign.setMarket(campaignMarket);
        equityCampaign.setSummary(campaignSummary);
        equityCampaign.setTimeline(campaignTimeline);
        equityCampaign.setOffers(campaignOffers);
        equityCampaign.setTeam(campaignTeam);
        equityCampaign.setImgName(imageURL);
        equityCampaign.setCategory(campaignCategory);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == RESULT_LOAD_IMG) {

            if (resultCode == RESULT_OK) {
                imageUri = data.getData();

            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } else if (reqCode == RESULT_LOAD_Video) {
            if (resultCode == RESULT_OK) {
                try {
                    final Uri videoUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(videoUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, "You haven't picked video", Toast.LENGTH_LONG).show();
            }
        }
    }
}


