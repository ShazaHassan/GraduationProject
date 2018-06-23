package com.example.shaza.graduationproject.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shaza.graduationproject.Database.Table.Users;
import com.example.shaza.graduationproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URL;

public class Profile extends android.support.v4.app.Fragment {


    private FirebaseUser currentUser;
    private String idDB, oldPass, newPass, repeatPass;
    private ImageView pp;
    private TextView userName, email, gender, birthday, country, resetPassword;
    private DatabaseReference userTable;
    private Users user;
    private AuthCredential authCredential;
    public Profile() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_page, container, false);
        pp = rootView.findViewById(R.id.personal_img);
        userName = rootView.findViewById(R.id.user_name);
        email = rootView.findViewById(R.id.email_personal_page);
        gender = rootView.findViewById(R.id.gender_personal_page);
        birthday = rootView.findViewById(R.id.birthday_personal_page);
        country = rootView.findViewById(R.id.country);
        resetPassword = rootView.findViewById(R.id.edit_password);
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        idDB = currentUser.getUid();
        getDataFromDB();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unknown_female_user);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        pp.setImageDrawable(roundedBitmapDrawable);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
        return rootView;
    }

    private void getDataFromDB() {
        userTable.child(idDB).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(Users.class);
                userName.setText(user.getFirstName() + " " + user.getLastName());
                email.setText(user.getEmail());
                gender.setText(user.getGender());
                birthday.setText(user.getBirthday());
                country.setText(user.getCountry());

                if (!dataSnapshot.hasChild("Profile Img")) {
                    String gender = user.getGender();
                    Log.v("gender", gender);
                    makeProfilePic(gender);
                } else {
                    String imageUrl = dataSnapshot.child("Profile Img").getValue().toString();
                    new DownloadImage().execute(imageUrl);
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

    void displayImage(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);
        pp.setImageDrawable(roundedBitmapDrawable);
    }

    private void resetpassword(String oldPass, final String newPass) {
        authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldPass);
        currentUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    currentUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("new password", "Password updated");
                                userTable.child(idDB).child("password").setValue(newPass);
                            } else {
                                Log.d("new password", "Error password not updated");
                            }
                        }
                    });
                } else {
                    Log.d("new password", "Error auth failed");
                }


            }
        });
    }

    public void resetPassword() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Set new password");

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(5, 5, 5, 5);
        // Set up the input
        final EditText oldPassword = new EditText(getContext());
        oldPassword.setHint("Enter your old password");
//        oldPassword.setPadding(5,5,5,5);
        final EditText newPassword = new EditText(getContext());
        newPassword.setHint("Enter your new password");
//        newPassword.setPadding(5,5,5,5);
        final EditText repeatPassword = new EditText(getContext());
        repeatPassword.setHint("repeat new password");
//        repeatPassword.setPadding(5,5,5,5);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        linearLayout.addView(oldPassword);
        newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        linearLayout.addView(newPassword);
        repeatPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        linearLayout.addView(repeatPassword);
        builder.setView(linearLayout);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                oldPass = oldPassword.getText().toString();
                newPass = newPassword.getText().toString();
                repeatPass = repeatPassword.getText().toString();
                if (oldPass.equals(newPass)) {
                    dialog.dismiss();
                    newPassword.setError("the new password can't be the same for old password");
                    Toast.makeText(getContext(), "the new password can't be the same for old password", Toast.LENGTH_LONG).show();

                } else if (newPass.equals(repeatPass)) {
                    resetpassword(oldPass, newPass);
                    Toast.makeText(getContext(), "password update successfully ", Toast.LENGTH_LONG).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "the repeated password not the same for new password", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        private Exception exception;

        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                return bmp;
            } catch (Exception e) {
                this.exception = e;

                return null;
            } finally {
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            // TODO: check this.exception
            // TODO: do something with the feed
            super.onPostExecute(bitmap);
            displayImage(bitmap);
        }
    }
}
