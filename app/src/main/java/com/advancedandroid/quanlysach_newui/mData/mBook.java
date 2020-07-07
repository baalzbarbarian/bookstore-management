package com.advancedandroid.quanlysach_newui.mData;

public class mBook {

    private int bookId;
    private String bookCode;
    private String bookName;
    private String bookAuthor;
    private String typesOfBook;
    private double bookPrice;
    private double bookAmount;

    public mBook() {
    }

    public mBook(String bookCode, String bookName, String bookAuthor, String typesOfBook, double bookPrice, double bookAmount) {
        this.bookCode = bookCode;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.typesOfBook = typesOfBook;
        this.bookPrice = bookPrice;
        this.bookAmount = bookAmount;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getTypesOfBook() {
        return typesOfBook;
    }

    public void setTypesOfBook(String typesOfBook) {
        this.typesOfBook = typesOfBook;
    }

    public double getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.bookPrice = bookPrice;
    }

    public double getBookAmount() {
        return bookAmount;
    }

    public void setBookAmount(double bookAmount) {
        this.bookAmount = bookAmount;
    }
}
