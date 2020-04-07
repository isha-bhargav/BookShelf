package com.example.bookshelf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends ArrayAdapter<cartlist> {
    Context context;
    int resource;
    List<cartlist>cartlists;
    public MyAdapter(Context context, int resource, List<cartlist> cartlists) {
        super(context, resource, cartlists);
        this.context=context;
        this.resource=resource;
        this.cartlists=cartlists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(resource,null);
        ImageView imgC=view.findViewById(R.id.img1);
        TextView tbookC=view.findViewById(R.id.txt1);
        TextView tpticeC=view.findViewById(R.id.txt2);
        cartlist c=cartlists.get(position);
        tbookC.setText(c.getBook());
        tpticeC.setText(c.getPrice());
        Picasso.with(context).load(c.getImage()).fit().centerCrop().into(imgC);
        return  view;
    }
}
