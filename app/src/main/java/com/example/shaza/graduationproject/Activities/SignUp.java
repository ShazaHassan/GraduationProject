package com.example.shaza.graduationproject.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shaza.graduationproject.Database.Table.Users;
import com.example.shaza.graduationproject.PrefManager;
import com.example.shaza.graduationproject.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class SignUp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public String imageName;
    private static int RESULT_LOAD_IMG = 1;
    EditText firstName, lastName, password, rePassword, email, country, birthday;
    Spinner gender;
    ImageView imageView;
    private String fName, lName, pass, rePass, eMail, coun, birth, idDatabase;
    private FirebaseDatabase database;
    private DatabaseReference userTable;
    private FirebaseAuth auth;
    private Users user;
    private Context context = this;
    private StorageReference storageImage;
    private Uri imageUri;
    private UploadTask uploadTask;
    private NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupDrawer();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        defineItems();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userTable = database.getReference().child("Users");
        storageImage = FirebaseStorage.getInstance().getReference();
        user = new Users();
        navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);
        navView.removeHeaderView(navView.getHeaderView(0));
    }

    //set up toolbar and side drawer
    private void setupDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.name_of_app) {
            Intent homePage = new Intent(this, Home_Page.class);
            startActivity(homePage);
        } else if (id == R.id.start_campaign) {
            Intent startCampaign = new Intent(this, Create_new_campaign.class);
            startActivity(startCampaign);
        } else if (id == R.id.suppot_startup) {
            Intent supportPage = new Intent(this, SupportStartUp.class);
            startActivity(supportPage);
        } else if (id == R.id.shop) {
            Intent shopPage = new Intent(this, Shop_Page.class);
            startActivity(shopPage);
        } else if (id == R.id.job) {
            Intent jobPage = new Intent(this, Job.class);
            startActivity(jobPage);
        } else if (id == R.id.login) {
            Intent loginPage = new Intent(this, Login.class);
            startActivity(loginPage);
        }else if (id == R.id.help_community){
            Intent HelpPage = new Intent(this, HelpingCommunity.class);
            startActivity(HelpPage);
        } else if (id == R.id.sign_up) {
            Intent signUpPage = new Intent(this, SignUp.class);
            startActivity(signUpPage);
        } else if (id == R.id.help) {

        } else if (id == R.id.about_us) {
            PrefManager prefManager = new PrefManager(getApplicationContext());
            // make first time launch TRUE
            prefManager.setFirstTimeLaunch(true);
            startActivity(new Intent(this, WelcomePage.class));
        } else if (id == R.id.logout) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.removeHeaderView(navigationView.getHeaderView(0));

            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.logout).setVisible(false);
            menu.findItem(R.id.login).setVisible(true);
            menu.findItem(R.id.sign_up).setVisible(true);
            FirebaseAuth.getInstance().signOut();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void openExpertChat(View view) {
        Intent expertChat = new Intent(this, TalkToExpert.class);
        startActivity(expertChat);
    }

    private void defineItems() {
        firstName = findViewById(R.id.first_name_text_for_sign_up);
        lastName = findViewById(R.id.last_name_text_for_sign_up);
        email = findViewById(R.id.email_text_for_sign_up);
        password = findViewById(R.id.password_text_for_sign_up);
        rePassword = findViewById(R.id.rePassword_text_for_sign_up);
        country = findViewById(R.id.country_text_for_sign_up);
        birthday = findViewById(R.id.birthday_text_for_sign_up);
    }

    private void setData() {
        user.setFirstName(fName);
        user.setLastName(lName);
        user.setCountry(coun);
        user.setBirthday(birth);
    }

    private void getDataFromFields() {
        fName = String.valueOf(firstName.getText());
        lName = String.valueOf(lastName.getText());
        eMail = String.valueOf(email.getText());
        coun = String.valueOf(country.getText());
        pass = String.valueOf(password.getText());
        rePass = String.valueOf(rePassword.getText());
        birth = String.valueOf(birthday.getText());
    }

    //button signUp action
    public void profilePage(View view) {

        getDataFromFields();
        String errorMsg = "Required";
        if (fName.equals("")) {
            firstName.setError(errorMsg);
        } else if (lName.equals("")) {
            lastName.setError(errorMsg);
        } else if (birth.isEmpty()) {
            birthday.setError(errorMsg);
        } else if (eMail.equals("")) {
            email.setError(errorMsg);
        } else if (pass.equals("")) {
            password.setError(errorMsg);
        } else if (rePass.equals("")) {
            rePassword.setError(errorMsg);
        } else if (coun.equals("")) {
            country.setError(errorMsg);
        } else if (!pass.equals(rePass)) {
            rePassword.setError("Not same the password");
        } else {
            if (!isEmailValid(eMail)) {
                email.setError("Wrong format for mail");
                Toast.makeText(context, "wrong", Toast.LENGTH_LONG).show();
            } else {
                createAccount(eMail, pass);
            }
        }
    }

    //to create new account at authentication to can add data at data database
    private void createAccount(String email, String password) {
        Log.d(context.toString(), "createAccount:" + email);
        // [START create_user_with_email]
        final EditText m = this.email;
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(context.toString(), "createUserWithEmail:success");
                            FirebaseUser userAuth = auth.getCurrentUser();
                            idDatabase = userAuth.getUid();
                            setData();
                            userTable.child(idDatabase).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "dataSaved", Toast.LENGTH_LONG).show();
                                }
                            });
                                Intent profilePage = new Intent(context, Home_Page.class);
                                startActivity(profilePage);
                        } else {
                            // If sign in fails, display a message to the user.
                            m.setError(task.getException().getLocalizedMessage());
                            Log.w(context.toString(), "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    // [START_EXCLUDE]
                    // [END_EXCLUDE]

                });
        // [END create_user_with_email]
    }

    //get url to save img at database
    private void getImgUrl() {
//        final StorageReference ref = storageImage.child("images/" + imageName);
//        uploadTask = ref.putFile(imageUri);
//
//        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//                }
//                // Continue with the task to get the download URL
//                return ref.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    Uri downloadUri = task.getResult();
//                    userTable.child(idDatabase).child("Profile Img").setValue(downloadUri.toString()).
//                            addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    Toast.makeText(context, "ImageSaved", Toast.LENGTH_LONG).show();
//                                    //downloadImg();
//                                }
//                            });
//                } else {
//                    // Handle failures
//                    // ...
//                }
//            }
//        });
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
                                userTable.child(idDatabase).child("Profile Img").setValue(downloadUri.toString()).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "ImageSaved", Toast.LENGTH_LONG).show();
                                    Intent profilePage = new Intent(context, Home_Page.class);
                                    startActivity(profilePage);
                                    //downloadImg();
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

    //method to upload img to storage
//    private void uploadImg() {
//        if (imageUri != null) {
//            final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//            imageName = UUID.randomUUID().toString();
//            final StorageReference ref = storageImage.child("images/" + imageName);
//            ref.putFile(imageUri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            progressDialog.dismiss();
//                            Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent profilePage = new Intent(context, Home_Page.class);
//                            startActivity(profilePage);
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

    //to check if the mail format is right or not
    private boolean isEmailValid(String email) {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            imageUri = data.getData();
        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
}
