package com.example.shaza.graduationproject.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shaza.graduationproject.Database.Table.CampaignType;
import com.example.shaza.graduationproject.Database.Table.RewardCampaign;
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

import java.util.UUID;

public class New_campaign_reward extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    private static int RESULT_LOAD_Video = 2;

    private EditText campaignName, campaignDuration, campaignMoney, campaignHeighlight, campaignVision, campaignOffers, campaignTeam;
    private String campName, campDuration, campMoney, campCategory, campHeighlight, campVision, campOffers, campTeam;
    private Spinner campaignCategory;

    private DatabaseReference userTable, rewardCampaignTable;
    private FirebaseUser currentUser;
    private String idUserDB, idCampDB;

    private Uri imageUri, videoUri;
    private String imageName, videoName, imageURL;
    private StorageReference storageImage, storageVideo;
    private UploadTask uploadTask;

    private Context context = this;

    private RewardCampaign campaign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_campaign_reward);
        findItem();
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        rewardCampaignTable = FirebaseDatabase.getInstance().getReference().child("Reward Campaign");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        idUserDB = currentUser.getUid();
        campaign = new RewardCampaign();
        storageImage = FirebaseStorage.getInstance().getReference();
    }

    private void findItem() {
        campaignName = findViewById(R.id.reward_campaign_name_editText);
        campaignDuration = findViewById(R.id.reward_duration_campaign_editText);
        campaignMoney = findViewById(R.id.reward_amount_of_money_editText);
        campaignHeighlight = findViewById(R.id.reward_heighlight_editText);
        campaignVision = findViewById(R.id.reward_vision_editText);
        campaignOffers = findViewById(R.id.reward_offers_editText);
        campaignTeam = findViewById(R.id.reward_team_editText);
        campaignCategory = findViewById(R.id.reward_category_spinner);
    }

    private void getValueFromItem() {
        campName = campaignName.getText().toString();
        campDuration = campaignDuration.getText().toString();
        campMoney = campaignMoney.getText().toString();
        campCategory = campaignCategory.getSelectedItem().toString();
        campHeighlight = campaignHeighlight.getText().toString();
        campVision = campaignVision.getText().toString();
        campOffers = campaignOffers.getText().toString();
        campTeam = campaignTeam.getText().toString();
    }

    private void checkValue() {
        if (campName.equals("")) {
            campaignName.setError("Enter name of campaign");
        } else if (campDuration.equals("")) {
            campaignDuration.setError("Enter duration of campaign");
        } else if (campMoney.equals("")) {
            campaignMoney.setError("Enter amount of money for your campaign");
        } else if (campCategory.equals("Select category")) {
            Toast.makeText(this, "Please select category", Toast.LENGTH_LONG).show();
        } else if (campHeighlight.equals("")) {
            campaignHeighlight.setError("Enter your heighlight of campaign");
        } else if (campVision.equals("")) {
            campaignVision.setError("What's your if campaign success");
        } else if (campOffers.equals("")) {
            campaignOffers.setError("Give offers for founded people");
        } else if (campTeam.equals("")) {
            campaignTeam.setError("Describe the roles for every member of team");
        } else {
            saveDateOnDB();
        }
    }

    private void saveDateOnDB() {
        //uploadImg();
        uploadImgAndgetImgUrl();

    }

    private void setDataForDB() {
        campaign.setName(campName);
        campaign.setCampaign_Image(imageURL);
        campaign.setDuration(campDuration);
        campaign.setHeighlight(campHeighlight);
        campaign.setHelperTeam(campTeam);
        campaign.setNeededMoney(campMoney);
        campaign.setOffers(campOffers);
        campaign.setVision(campVision);
    }

    public void uploadPhoto(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
    }

//    private void uploadImg() {
//        if (imageUri != null) {
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//            imageName = UUID.randomUUID().toString();
//            final StorageReference ref = storageImage.child("images/" + imageName);
//            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
//                            getImgUrl();
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            progressDialog.dismiss();
//                            Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
//                                    .getTotalByteCount());
//                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
//                        }
//                    });
//        }
//    }

    private void uploadImgAndgetImgUrl() {
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
                                setDataForDB();
                                idCampDB = rewardCampaignTable.push().getKey();
                                userTable.child(idUserDB).child("Campaigns").child(idCampDB).setValue(new CampaignType("Reward", idCampDB));
                                rewardCampaignTable.child(idCampDB).setValue(campaign).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Campaign success create", Toast.LENGTH_LONG).show();
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
                videoUri = data.getData();

            } else {
                Toast.makeText(this, "You haven't picked video", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void openExpertChat(View view) {
        Intent expertChat = new Intent(this, TalkToExpert.class);
        startActivity(expertChat);
    }

    public void uploadVideo(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, RESULT_LOAD_Video);
    }

    public void viewAs(View view) {
        Intent viewAs = new Intent(this, View_As.class);
        startActivity(viewAs);
    }

    public void publish(View view) {

        getValueFromItem();
        checkValue();

    }
}
