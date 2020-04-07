package com.example.bookshelf;

public class Member1 {
    private String profile,address,email,pass;

    public Member1() {
    }

    public Member1(String profile,String address,String email,String pass) {
        this.profile = profile;
        this.address=address;
        this.email=email;
        this.pass=pass;
    }
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email=email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
