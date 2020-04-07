package com.example.bookshelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;
    static EditText emailText,passText;
    Button login,signup;
    ProgressBar progressBar;
     static String email,pass;
     ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailText=findViewById(R.id.ed1);
        passText=findViewById(R.id.ed2);
        login=findViewById(R.id.btn1);
        signup=findViewById(R.id.btn2);
        progressBar=findViewById(R.id.p);
        dialog=new ProgressDialog(MainActivity.this);
        dialog.setTitle(" Please wait");
        dialog.setMessage("Signing in...");
        mAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }
    private void showProgressDialogWithTitle(String title,String sunstring)
    {

    }
    private void userLogin()
    {
         email=emailText.getText().toString().trim();
         pass=emailText.getText().toString().trim();
        if(email.isEmpty())
        {
            emailText.setError("email is required");
            emailText.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            passText.setError("email is required");
            passText.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            passText.setError("password should be minimum 6 characters");
            passText.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
        dialog.show();

        mAuth.signInWithEmailAndPassword(email,pass) .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);
                dialog.dismiss();
                if(task.isSuccessful())
                {
                  /**  Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);*/
                  checkVerification();
                }else
                {
                    Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void registerUser()
    {
         email=emailText.getText().toString().trim();
         pass=emailText.getText().toString().trim();
        Log.i("log" ,"taking mail");
        if(email.isEmpty())
        {
            emailText.setError("email is required");
            emailText.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            passText.setError("email is required");
            passText.requestFocus();
            return;
        }
        if(pass.length()<6)
        {
            passText.setError("password should be minimum 6 characters");
            passText.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);
        dialog.show();
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               // progressBar.setVisibility(View.GONE);t.setVisibility(View.GONE);
                dialog.dismiss();
                if(task.isSuccessful())
                {
                    sendEmail();
                }
                else
                {
                    if(task.getException()instanceof FirebaseAuthUserCollisionException)
                        Toast.makeText(MainActivity.this, "You are alreary registered", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }
    }

    /**public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.i("log", "firebaseAuth called:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        Log.i("log  :",acct.getIdToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("log", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i("log", "getUser");
                            updateUI(user);
                            startActivity(new Intent(MainActivity.this,Main2Activity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("MainActivity", "signInWithCredential:failure");
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }*/
    public  void updateUI(FirebaseUser user) {
        //sgnOut.setVisibility(View.VISIBLE);
        Log.i("log", "updateUI");
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Toast.makeText(this, personName+" logged in", Toast.LENGTH_LONG).show();
        }
    }
    private  void checkVerification()
    {
        FirebaseUser firebaseUser=mAuth.getInstance().getCurrentUser();
        boolean emailflag=firebaseUser.isEmailVerified();
        if(emailflag)
        {   finish();
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }
        else
        {
            mAuth.signOut();
        }
    }
    private  void sendEmail()
    {
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
       if(firebaseUser!=null)
       {
           firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful())
                   {
                       Toast.makeText(MainActivity.this, "email sent for verification", Toast.LENGTH_SHORT).show();
                       userLogin();

                   }
               }
           });
       }
    }
}
