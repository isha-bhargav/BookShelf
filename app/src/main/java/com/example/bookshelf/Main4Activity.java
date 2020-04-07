package com.example.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.Filterable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.Inflater;

public class Main4Activity extends AppCompatActivity {
    private static final String TAG = "Main4Activity";
    ArrayList<Member> list;
    DatabaseReference reference4;
    RecyclerView recyclerView;
    RecyclerViewAdpater adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        list = new ArrayList<Member>();
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutmanager = new LinearLayoutManager(Main4Activity.this);
        recyclerView.setLayoutManager(layoutmanager);
        reference4 = FirebaseDatabase.getInstance().getReference("Member");
        reference4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("log","datasanp");
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.i("log","datasanp inside for");
                    Member member = dataSnapshot1.getValue(Member.class);
                    list.add(member);
                    Log.i("log","list added");
                }
                adapter = new RecyclerViewAdpater(Main4Activity.this, list);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        inflater.inflate(R.menu.cart,menu);
        MenuItem searchitem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)searchitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("log","textchangge");
                adapter.getFilter().filter(newText);
                Log.i("log","filter text");
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.cart1)
        {
            startActivity(new Intent(Main4Activity.this,Cart.class));
        }
        return  true;
    }
}