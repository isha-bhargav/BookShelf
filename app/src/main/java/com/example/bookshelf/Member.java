package com.example.bookshelf;

import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable {
    private String books1;
    private  Double prices1;
    private String phones1;
    String mImageUrl;
    private String email1,pass1;

    public Member() {
    }

    public Member(String books1,Double prices1,String phones1,String email1,String pass1,String mImageUrl) {
        this.books1 = books1;
        this.prices1=prices1;
        this.phones1=phones1;
        this.email1=email1;
        this.pass1=pass1;
        this.mImageUrl=mImageUrl;
    }


    protected Member(Parcel in) {
        books1 = in.readString();
        if (in.readByte() == 0) {
            prices1 = null;
        } else {
            prices1 = in.readDouble();
        }
        phones1 = in.readString();
        mImageUrl = in.readString();
        email1 = in.readString();
        pass1 = in.readString();
    }

    public static final Creator<Member> CREATOR = new Creator<Member>() {
        @Override
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }

        @Override
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };

    public String getBooks() {
        return books1;
    }

    public void setBooks(String books) {
        this.books1 = books;
    }

    public Double getPrices() {
        return prices1;
    }

    public void setPrices(Double prices) {
        this.prices1 = prices;
    }

    public String getPhones() {
        return phones1;
    }

    public void setPhones(String phones) {
        this.phones1 = phones;
    }
    public String getEmail1()
    {
        return email1;
    }
    public void setEmail1(String email)
    {
        this.email1=email;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass) {
        this.pass1 = pass;
    }


    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(books1);
        if (prices1 == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(prices1);
        }
        parcel.writeString(phones1);
        parcel.writeString(mImageUrl);
        parcel.writeString(email1);
        parcel.writeString(pass1);
    }
}
