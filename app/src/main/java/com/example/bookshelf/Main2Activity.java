package com.example.bookshelf;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class Main2Activity extends AppCompatActivity {
  //  Button sell, buy;
    Toolbar toolBar;
    private DrawerLayout drawer;
    NavigationView navigationView;
    //GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    MainActivity mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       /** sell = findViewById(R.id.se);
        buy = findViewById(R.id.pur);*/
        mAuth = FirebaseAuth.getInstance();
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolBar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mainActivity = new MainActivity();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Log.i("log", "gso");
       // mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
if (savedInstanceState==null)
{
    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFrag()).commit();
    navigationView.setCheckedItem(R.id.nav_view);
}
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.t0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new HomeFrag()).commit();
                        break;
                    case R.id.t2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                               new Profilefrag()).commit();
                        break;
                    case R.id.t3:
                        mAuth.signOut();
                        finish();
                        startActivity(new Intent(Main2Activity.this, MainActivity.class));

                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
    public void updateUI(FirebaseUser user) {
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
            Toast.makeText(this, personName + " logged in", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}