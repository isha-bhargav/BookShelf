package com.example.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Main5Activity extends AppCompatActivity {
    TextView tBook,tPrice;
    CardForm cardForm;
    Button btn;
    AlertDialog.Builder alertBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        cardForm=findViewById(R.id.card_form);
        btn=findViewById(R.id.btn1);
        tBook=findViewById(R.id.txt1);
        tPrice=findViewById(R.id.txt2);
        if(getIntent().hasExtra("itemBook") && getIntent().hasExtra("itemPrice"))
        {
            String book=getIntent().getStringExtra("itemBook");
            Double price=getIntent().getDoubleExtra("itemPrice",0);
            tBook.setText(book);tPrice.setText("\u20B9"+String.valueOf(price));
        }
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(Main5Activity.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardForm.isValid())
                {
                    alertBuilder=new AlertDialog.Builder(Main5Activity.this);
                    alertBuilder.setTitle("Confirm before Purchase");
                    alertBuilder.setMessage("Card Number: "+cardForm.getCardNumber()+" \n"+ "Card expiry date: "+
                            cardForm.getExpirationDateEditText().getText().toString()+"\n"+
                            "Card CVV: "+cardForm.getCvv()+"\n"+"Phone number: "+cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            Toast.makeText(Main5Activity.this, "Thankyou for purchase", Toast.LENGTH_SHORT).show();

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            Query applesQuery = ref.child("Member").orderByChild("books").equalTo(getIntent().getStringExtra("itemBook"));

                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e("log", "onCancelled", databaseError.toException());
                                }
                            });

                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                }
                else
                {
                    Toast.makeText(Main5Activity.this, "Please complete the form", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
