package com.advancedandroid.quanlysach_newui.mData;

public class mBillDetail {
    private int id;
    private String billCode;
    private String bookCode;
    private int amount;
    private double unitPrice;
    private double totalAmount;

    public mBillDetail() {
    }

    public mBillDetail(String billCode, String bookCode, int amount, double unitPrice, double totalAmount) {
        this.billCode = billCode;
        this.bookCode = bookCode;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
