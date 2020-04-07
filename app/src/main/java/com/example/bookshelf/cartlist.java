package com.example.bookshelf;

public class cartlist {
    String image,book,price;

    public cartlist(String image,String book,String price) {
        this.image=image;
        this.book = book;
        this.price=price;
    }

    public String getImage() {
        return image;
    }

    public String getBook() {
        return book;
    }

    public String getPrice() {
        return price;
    }
}
