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
import com.example.shaza.graduationproject.Database.Table.Product;
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

public class AddProduct extends AppCompatActivity {

    private static int RESULT_LOAD_IMG = 1;
    private Context context = this;
    private String idCampDB, type, idUserDB, idProdDB;
    private EditText prodName, prodDesc, prodPrice;
    private Spinner prodCat;
    private Uri imageUri;
    private FirebaseUser currentUser;
    private DatabaseReference productTable, campTable, userTable;
    private String productName, productDesc, productCat, imageURL, proPrice, imageName, sDate;
    private long productPrice;
    private Product product;
    private StorageReference storageImage;
    private UploadTask uploadTask;
    private Date startDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        Intent intent = getIntent();
        idCampDB = intent.getExtras().getString("id");
        type = intent.getExtras().getString("type");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        idUserDB = currentUser.getUid();
        productTable = FirebaseDatabase.getInstance().getReference().child("Product");
        userTable = FirebaseDatabase.getInstance().getReference().child("Users");
        storageImage = FirebaseStorage.getInstance().getReference();
        if (type.equals("reward")) {
            campTable = FirebaseDatabase.getInstance().getReference().child("Reward Campaign");
        } else if (type.equals("equity")) {
            campTable = FirebaseDatabase.getInstance().getReference().child("Equity Campaign");

        }
        product = new Product();
        findItem();
//        Intent intent1 = new Intent();
//        intent1.putExtra("id", idCampDB);
//        intent1.putExtra("type",type);
//        setResult(RESULT_OK, intent);
//        finish();

    }

    private void findItem() {
        prodName = findViewById(R.id.product_name_editText);
        prodDesc = findViewById(R.id.describe_of_product_editText);
        prodPrice = findViewById(R.id.price_of_product_editText);
        prodCat = findViewById(R.id.category_for_product);
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

    public void addProduct(View view) {
        getDataFromFields();
    }

    private void getDataFromFields() {
        productName = prodName.getText().toString();
        productDesc = prodDesc.getText().toString();
        proPrice = (prodPrice.getText().toString());
        productCat = prodCat.getSelectedItem().toString();
        checkValueForData();
    }

    private void checkValueForData() {
        String errorMsg = "Field Can't Be Error";
        if (productName.equals("")) {
            prodName.setError(errorMsg);
        } else if (productCat.equals("All products")) {
            Toast.makeText(this, "Please Select Category", Toast.LENGTH_LONG).show();
        } else if (proPrice.equals("")) {
            prodPrice.setError(errorMsg);
        } else if (productDesc.equals("")) {
            prodDesc.setError(errorMsg);
        } else if (imageUri == null) {
            Toast.makeText(this, "please upload image", Toast.LENGTH_LONG).show();
        } else {
            productPrice = Long.parseLong(proPrice);
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
                                idProdDB = productTable.push().getKey();
                                setDataForDB();
                                userTable.child(idUserDB).child("Products").child(idProdDB).setValue(idProdDB);
                                campTable.child(idCampDB).child("Products").child(idProdDB).setValue(idProdDB);
                                productTable.child(idProdDB).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        product.setName(productName);
        product.setIdCreator(idUserDB);
        product.setDescription(productDesc);
        product.setStartDate(sDate);
        product.setPrice(productPrice);
        product.setNoOfBuying(0);
        if (type.equals("reward")) {
            product.setCampaignType(new CampaignType("Reward", idCampDB));
        } else if (type.equals("equity")) {
            product.setCampaignType(new CampaignType("Equity", idCampDB));
        }
        product.setImageURL(imageURL);
        product.setCategory(productCat);
        product.setIdProduct(idProdDB);
    }
}
