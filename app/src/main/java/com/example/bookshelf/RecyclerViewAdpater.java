package com.example.bookshelf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdpater extends RecyclerView.Adapter<RecyclerViewAdpater.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdpater";
    AlertDialog.Builder builder;
    private ArrayList<Member> mem;
    private ArrayList<Member>listsearch;
    private Context mContext;
    public RecyclerViewAdpater(Context mContext,ArrayList<Member>mem) {
        this.mContext=mContext;
        this.mem=mem;
        listsearch=new ArrayList<>(mem);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,
                parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //Member up=mem.get(position);
        holder.tBook.setText(mem.get(position).getBooks());
        holder.tPrice.setText("\u20B9"+mem.get(position).getPrices().toString());
        holder.tPhone.setText(mem.get(position).getPhones());
        Picasso.with(mContext).load(mem.get(position).getmImageUrl())
                .fit()
        .centerCrop().into(holder.tImg);
       /** holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(mContext, "jdfgh", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext,Main5Activity.class);
                intent.putExtra("itemBook", mem.get(position).getBooks());
                intent.putExtra("itemPrice",mem.get(position).getPrices());
                mContext.startActivity(intent);
            }
        });*/

       holder.cart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              Cart.list.add(new cartlist(mem.get(position).getmImageUrl(),mem.get(position).getBooks()
              ,String.valueOf(mem.get(position).getPrices())));
             // Cart.imagesC.add(mem.get(position).getmImageUrl());
             // Cart.pricesC.add(mem.get(position).getPrices().toString());
              // Toast.makeText(mContext, "item added to cart", Toast.LENGTH_SHORT).show();
               }
       });
       holder.buy.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               builder = new AlertDialog.Builder(mContext);
               builder.setTitle(R.string.dialog_message) .setMessage(R.string.dialog_title)
               //Setting message manually and performing action on button click
              // builder.setMessage("Do you want to close this application ?")
                       .setCancelable(false)
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               Intent intent=new Intent(mContext,Main6Activity.class);
                               intent.putExtra("MemberItem",mem.get(position));
                               mContext.startActivity(intent);
                                        }
                       })
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               //  Action for 'NO' Button
                               dialog.cancel();
                               Toast.makeText(mContext,"you choose no action for alertbox",
                                       Toast.LENGTH_SHORT).show();
                           }
                       });
               //Creating dialog box
               AlertDialog alert = builder.create();
               //Setting the title manually
               alert.setTitle("Are you sure");
               alert.show();
           }
       });
    }
    @Override
    public int getItemCount() {
        return mem.size();
    }
    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Member>filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0)
            {
                filteredList.addAll(listsearch);
            }else
            {
                String filterPattern=constraint.toString().toLowerCase().trim();
                for (Member item:listsearch)
                {
                    if (item.getBooks().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults res=new FilterResults();
            res.values=filteredList;
            return res;
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            mem.clear();
            mem.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tBook,tPrice,tPhone;
        ImageView tImg;
        Button buy,cart;
        RelativeLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            tBook=itemView.findViewById(R.id.txt1);
            tPrice=itemView.findViewById(R.id.txt2);
            tPhone=itemView.findViewById(R.id.txt3);
            layout=itemView.findViewById(R.id.rel);
            tImg=itemView.findViewById(R.id.img1);
            buy=itemView.findViewById(R.id.btn2);
            cart=itemView.findViewById(R.id.btn1);
        }
    }
}
