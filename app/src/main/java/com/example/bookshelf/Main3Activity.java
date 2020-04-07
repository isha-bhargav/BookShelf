package com.example.bookshelf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class Main3Activity extends AppCompatActivity {
    EditText Tbook, Tprice, Tphone;
    public static final int PICK_IMG_REQUEST = 1;
    Button btn, mButtonChoseImage;
    ImageView mImageView;
    DatabaseReference reff;
    Member member;
    private Uri mImageUri;
    StorageReference mStorageRef;
    StorageTask mUploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Tbook = findViewById(R.id.ed1);
        Tprice = findViewById(R.id.ed2);
        Tphone = findViewById(R.id.ed3);
        btn = findViewById(R.id.btn1);
        mImageView = findViewById(R.id.img1);
        mButtonChoseImage = findViewById(R.id.choose_img);
        mStorageRef=FirebaseStorage.getInstance().getReference().child("Member");
        member = new Member();
        reff = FirebaseDatabase.getInstance().getReference().child("Member");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   final String books = Tbook.getText().toString().trim();
                   final double prices = Double.parseDouble(Tprice.getText().toString().trim());
                    final String phones = Tphone.getText().toString().trim();
                    if (phones.length() != 10) {
                        Tphone.setError("Please enter valid contact number");
                        Tphone.requestFocus();
                        return;
                    }

                member.setBooks(books);
                    member.setPrices(prices);
                    member.setPhones(phones);
                    member.setEmail1(MainActivity.emailText.getText().toString().trim());
                    member.setPass1(MainActivity.passText.getText().toString().trim());
                        if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(Main3Activity.this, "Upload in progress..", Toast.LENGTH_SHORT).show();
                    }else {
                        if (mImageUri != null) {
                            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + ", " + getFileExtension(mImageUri));
                            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                   /** member.setmImageUrl(taskSnapshot.getStorage().getDownloadUrl().toString());
                                    String uploadId = reff.push().getKey();
                                    reff.child(uploadId).setValue(member);*/
                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();

                                    while (!urlTask.isSuccessful());

                                    Uri downloadUrl = urlTask.getResult();

                                        member.setmImageUrl(downloadUrl.toString());

                                    //Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString());
                                    String uploadId = reff.push().getKey();
                                    reff.child(uploadId).setValue(member);


                                }
                            });
                        } else
                            Toast.makeText(Main3Activity.this, "nothing found", Toast.LENGTH_SHORT).show();
                       // reff.push().setValue(member);
                    Toast.makeText(Main3Activity.this, books + " ready to be sold", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mButtonChoseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }

    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMG_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    /**public void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + ", " + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    member.setmImageUrl(taskSnapshot.getStorage().getDownloadUrl().toString());
                    String uploadId = reff.push().getKey();
                    reff.child(uploadId).setValue(member);
                }
            });
        } else
            Toast.makeText(this, "nothing found", Toast.LENGTH_SHORT).show();
    }*/
}

