package com.example.shaza.graduationproject.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.shaza.graduationproject.Database.Table.Job;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddCareer extends AppCompatActivity {

    private static final int RESULT_LOAD_IMG = 1;
    private EditText companyName, jobDesc, links;
    private Spinner category;
    private String compName, jobDescription, companyLinks, jobCat;
    private String idJobDB, idCampDB, idUserDB, type, imageURL, imageName, sDate;
    private DatabaseReference userTable, jobTable, campTable;
    private FirebaseUser currentUser;
    private Uri imageUri;
    private Job job;
    private UploadTask uploadTask;
    private StorageReference storageImage;
    private Date startDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_career);
        Intent intent = getIntent();
        idCampDB = intent.getStringExtra("id");
        type = intent.getStringExtra("type");
        Log.v("id and type", idCampDB + " " + type);
        if (type.equals("reward")) {
            campTable = FirebaseDatabase.getInstance().getReference().child("Reward Campaign");
        } else if (type.equals("equity")) {
            campTable = FirebaseDatabase.getInstance().getReference().child("Equity Campaign");
        }
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        jobTable = FirebaseDatabase.getInstance().getReference().child("Job");
        storageImage = FirebaseStorage.getInstance().getReference();
        companyName = findViewById(R.id.company_name_editText);
        jobDesc = findViewById(R.id.desc_of_career_editText);
        links = findViewById(R.id.link_for_company_editText);
        category = findViewById(R.id.job_category);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        idUserDB = currentUser.getUid();
        job = new Job();
    }


    public void publishJob(View view) {
        getDataFromFields();
    }

    private void getDataFromFields() {
        compName = companyName.getText().toString();
        jobCat = category.getSelectedItem().toString();
        companyLinks = links.getText().toString();
        jobDescription = jobDesc.getText().toString();
        checkValue();
    }

    private void checkValue() {
        String errorMsg = "Can't Be Empty Field";
        if (compName.equals("")) {
            companyName.setError(errorMsg);
        } else if (jobCat.equals("All types")) {
            Toast.makeText(this, "Please select category", Toast.LENGTH_LONG).show();
        } else if (companyLinks.equals("")) {
            links.setError(errorMsg);
        } else if (jobDescription.equals("")) {
            jobDesc.setError(errorMsg);
        } else if (imageUri == null) {
            Toast.makeText(this, "Please upload Logo of company", Toast.LENGTH_LONG).show();
        } else {
            saveDataOnDB();
        }
    }

    private void saveDataOnDB() {
        uploadImgAndGetImgUrl();
    }

    private void uploadImgAndGetImgUrl() {
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
                                Log.v("date", sDate);
                                idJobDB = jobTable.push().getKey();
                                setDataForDB();
                                Log.v("jobID", idJobDB);
                                Log.v("userID", idUserDB);
                                userTable.child(idUserDB).child("Jobs").child(idJobDB).setValue(idJobDB);
                                campTable.child(idCampDB).child("Jobs").child(idJobDB).setValue(idJobDB);
                                jobTable.child(idJobDB).setValue(job).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "product success create", Toast.LENGTH_LONG).show();
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
        job.setName(compName);
        job.setImageURL(imageURL);
        job.setCategory(jobCat);
        job.setDescription(jobDescription);
        job.setStartDate(sDate);
        job.setIdCreator(idUserDB);
        job.setIdJob(idJobDB);
        job.setLinks(companyLinks);
        if (type.equals("reward")) {
            job.setCampaignType(new CampaignType("Reward", idCampDB));
        } else if (type.equals("equity")) {
            job.setCampaignType(new CampaignType("Equity", idCampDB));
        }
    }

    public void uploadPhoto(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
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
        }
    }
}
