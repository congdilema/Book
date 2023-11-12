package com.example.book;

public class BookList {

    private String bookname;
    private String price;
    private String publish;
    private String push;

    public BookList(){}

    public BookList(String bookname,String price,String publish){
        this.bookname = bookname;
        this.price = price;
        this.publish = publish;
    }

    public BookList(String push, String bookname,String price,String publish){
        this.bookname = bookname;
        this.price = price;
        this.publish = publish;
        this.push = push;
    }

    public String getBookname(){
        return bookname;
    }
    public String getPrice(){
        return price;
    }
    public String getPublish(){
        return publish;
    }
    public String getPush(){return push;}


    public void setBookname(String bookname){
        bookname = bookname;
    }
    public void setPrice(String price){
        price = price;
    }
    public void setPublish(String publish){
        publish = publish;
    }
    public void setPush(String push){push = push;}


}
