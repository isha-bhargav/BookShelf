package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Cart extends AppCompatActivity {
    ListView listView;
    static List<cartlist> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        listView=findViewById(R.id.lst);
        list=new ArrayList<>();

        MyAdapter adapter=new MyAdapter(getApplicationContext(),R.layout.row,list);
        listView.setAdapter(adapter);


    }

}
