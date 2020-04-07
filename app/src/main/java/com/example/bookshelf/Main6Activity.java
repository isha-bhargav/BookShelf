package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Main6Activity extends AppCompatActivity {
        ImageView iv;
        Button btn;
        TextView tb,tp;
        Button  cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        iv = findViewById(R.id.img1);
        btn = findViewById(R.id.btn1);
        tb = findViewById(R.id.txt1);
        tp = findViewById(R.id.txt2);
        cont=findViewById(R.id.btn1);
        Intent intent = getIntent();
        final Member member = intent.getParcelableExtra("MemberItem");
        String imgres = member.getmImageUrl();
        Log.i("log", imgres);
        Picasso.with(getApplicationContext()).load(member.getmImageUrl()).fit().centerCrop().into(iv);
        tb.setText(member.getBooks());
        tp.setText(member.getPrices().toString());
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Main6Activity.this,Main7Activity.class);
                intent1.putExtra("buy",member.getBooks());
                intent1.putExtra("buy",member.getPrices());
                startActivity(intent1);
            }
        });
    }
}
