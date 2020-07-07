package com.advancedandroid.quanlysach_newui.mData;

public class mBill {
    private int id;
    private String billCode;
    private String billDate;

    public mBill() {
    }

    public mBill(int id, String billCode, String billDate) {
        this.id = id;
        this.billCode = billCode;
        this.billDate = billDate;
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

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
}
