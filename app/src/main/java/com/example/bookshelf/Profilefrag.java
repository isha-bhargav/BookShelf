package com.example.bookshelf;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Queue;

public class Profilefrag extends Fragment {
    TextView profile, err;
    static EditText tname, taddress;
    Button btn;
    static String name, address,useremail;
    DatabaseReference reff;
    Member1 member1 = new Member1();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilefrag, container, false);
        profile = view.findViewById(R.id.txt1);
        tname = view.findViewById(R.id.ed1);
        taddress = view.findViewById(R.id.ed2);
        btn = view.findViewById(R.id.btn1);
        err = view.findViewById(R.id.txt2);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
             useremail = user.getEmail();
        }
        reff = FirebaseDatabase.getInstance().getReference().child("Member1");
        final Member member = new Member();
        name = tname.getText().toString().trim();
        address = taddress.getText().toString().trim();
       // Query query = reff.orderByChild("").equalTo("ishabhargav@gmail.com", "email");
        if (name.isEmpty() || address.isEmpty()) {
            profile.setVisibility(View.VISIBLE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tname.getText().toString().trim().isEmpty() || taddress.getText().toString().trim().isEmpty()) {
                    err.setVisibility(View.VISIBLE);
                } else {
                    member1.setProfile(tname.getText().toString().trim());
                    member1.setAddress(taddress.getText().toString().trim());
                    member1.setEmail(useremail);
                    member1.setPass(MainActivity.passText.getText().toString().trim());
                    reff.push().setValue(member1);
                    Toast.makeText(getActivity(), "Profile is complete", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }
}