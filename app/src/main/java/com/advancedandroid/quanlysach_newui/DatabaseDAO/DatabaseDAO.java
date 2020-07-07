package com.advancedandroid.quanlysach_newui.DatabaseDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.advancedandroid.quanlysach_newui.mData.mBill;
import com.advancedandroid.quanlysach_newui.mData.mBillDetail;
import com.advancedandroid.quanlysach_newui.mData.mBook;
import com.advancedandroid.quanlysach_newui.mData.mTypesOfBook;
import com.advancedandroid.quanlysach_newui.mData.mUser;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * Ctrl + F and search: Book's Method to pass to Book's method.
 * Ctrl + F and search: Types Of Book Method to pass to Book's method.
 * Ctrl + F and search: Bill's Method to pass to Book's method.
 * Ctrl + F and search: Bill Detail Method to pass to Book's method.
 *
 * */


public class DatabaseDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fpolyBook"; //Database Name
    private static final String TAG = "databaseDAO: ";

    private static final String USER_LOGIN_TABLE = "userlogin"; //User Table
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String PHONENUMBER = "phonenumber";
    private static final String FULLNAME = "fullname";

    private static final String TYPES_BOOK_TABLE = "types_of_book"; //Gender Book
    private static final String TYPESCODE = "typesCode";
    private static final String TYPESNAME = "typesName";
    private static final String TYPESLOCATION = "genderLocation";
    private static final String TYPESDESCRIPTION = "genderDescrition";

    private static final String BOOK_TABLE = "book_table"; //Book
    private static final String BOOKCODE = "bookCode";
    private static final String BOOKNAME = "bookName";
    private static final String BOOKAUTHOR = "bookAuthor";
    private static final String TYPESOFBOOK = "typesOfBook";
    private static final String BOOKPRICE = "bookPrice";
    private static final String BOOKAMOUNT = "bookAmount";

    private static final String BILL_TABLE = "billtable";
    private static final String BILLCODE = "billcode";
    private static final String BILLDATE = "billdate";

    private static final String BILLDETAIL_TABLE = "billDetailTable";
    private static final String TOTALAMOUNT = "totalAmount";

    //String to Create Types Table
    private static final String typesTable = "CREATE TABLE " +TYPES_BOOK_TABLE+ "("
            +ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +TYPESCODE+ " TEXT, "
            +TYPESNAME+ " TEXT, "
            +TYPESLOCATION+ " TEXT, "
            +TYPESDESCRIPTION+ " TEXT);";

    //String to Create User Table
    private static final String userLogin = "CREATE TABLE " +USER_LOGIN_TABLE+ "("
            +ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +USERNAME+ " TEXT, "
            +PASSWORD+ " TEXT, "
            +PHONENUMBER+ " TEXT, "
            +FULLNAME+ " TEXT);";

    //String to Create Book Table
    private static final String bookTable = "CREATE TABLE " +BOOK_TABLE+ "("
            +ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +BOOKCODE+ " TEXT, "
            +BOOKNAME+ " TEXT, "
            +BOOKAUTHOR+ " TEXT, "
            +TYPESOFBOOK+ " TEXT, "
            +BOOKPRICE+ " DOUBLE, "
            +BOOKAMOUNT+ " DOUBLE, "
            + "FOREIGN KEY ( "+TYPESOFBOOK+" ) REFERENCES " +TYPES_BOOK_TABLE + " ( "+TYPESNAME+" ));";

    //String to Create Bill Table
    private static final String billTable = "CREATE TABLE " +BILL_TABLE+ "("
            +ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +BILLCODE+ " TEXT, "
            +BILLDATE+ " TEXT);";

    //String to Create Bill Detail Table
    private static final String billDetail = "CREATE TABLE " +BILLDETAIL_TABLE+ "("
            +ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            +BILLCODE+ " TEXT, "
            +BOOKCODE+ " TEXT, "
            +BOOKAMOUNT+ " INT, "
            +BOOKPRICE+ " DOUBLE, "
            +TOTALAMOUNT+ " DOUBLE, "
            +" FOREIGN KEY ("+BILLCODE+") REFERENCES " +BILL_TABLE+ " ("+BILLCODE+"), "
            +" FOREIGN KEY ("+BOOKCODE+") REFERENCES " +BOOK_TABLE+ " ("+BOOKCODE+"));";


    public DatabaseDAO(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(userLogin);
        db.execSQL(typesTable);
        db.execSQL(bookTable);
        db.execSQL(billTable);
        db.execSQL(billDetail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +USER_LOGIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +TYPES_BOOK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +BOOK_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +BILL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " +BILLDETAIL_TABLE);
        onCreate(db);
    }

    //Add User Login
    public boolean addUserLogin(mUser mUser){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USERNAME, mUser.getUserName());
        values.put(PASSWORD, mUser.getPassWord());
        values.put(PHONENUMBER, mUser.getPhoneNumber());
        values.put(FULLNAME, mUser.getFullname());

        long result = db.insert(USER_LOGIN_TABLE, null, values);
        db.close();
        if (result < 1){
            Log.d(TAG, "addUserLogin: "+mUser.getUserName()+"//"+mUser.getPassWord()+"//"+mUser.getPhoneNumber()+"//"+mUser.getFullname());
            return false;
        }else {
            return true;
        }
    }

    //Get All User
    public List<mUser> getAllUser(){
        List<mUser> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+USER_LOGIN_TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mUser mUser = new mUser();
                mUser.setFullname(cursor.getString(4));
                mUser.setUserName(cursor.getString(1));
                mUser.setPhoneNumber(cursor.getString(3));
                list.add(mUser);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    //Get All User To Delete
    public List<mUser> getAllUserToDelete(){
        List<mUser> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+USER_LOGIN_TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mUser mUser = new mUser();
                mUser.setId(Integer.parseInt(cursor.getString(0)));
                mUser.setUserName(cursor.getString(1));
                mUser.setPassWord(cursor.getString(2));
                mUser.setPhoneNumber(cursor.getString(3));
                mUser.setFullname(cursor.getString(4));
                list.add(mUser);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    //Delete User
    public int deleteUser(mUser m){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.advancedandroid.quanlysach_newui/databases/fpolyBook", null);

        }catch (SQLException e){
            Log.e(TAG, "deleteUser: "+e);
        }

        int sql = db.delete(USER_LOGIN_TABLE, ID+ "=?",
                new String[]{String.valueOf(m.getId())});

        db.close();
        if (sql == 0){
            return -1;
        }else {
            return 1;
        }

    }

    public List getDataByUsername(String user, String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> c = new ArrayList<>();
        String sqlUser = "SELECT * FROM "
                +USER_LOGIN_TABLE+ " WHERE " +USERNAME+ " LIKE " +"'"+user+"'" + " AND " +PASSWORD+ " LIKE " +"'"+pass+"'";
        Cursor cursor = db.rawQuery(sqlUser, null);
        if (cursor.moveToFirst()){
            do {
                String idSQL = cursor.getString(0);
                String userSQL = cursor.getString(1);
                String passSQL = cursor.getString(2);

                c.add(idSQL);
                c.add(userSQL);
                c.add(passSQL);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        if (c.size() < 1){
            c.add("");
            c.add("");
            c.add("");
        }
        return c;
    }

    //Check admin account exists or not
    public String checkReg(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> d = new ArrayList<>();
//        List<String> e = new ArrayList<>();
//        List<String> f = new ArrayList<>();

        String sql1 = "SELECT " +USERNAME+ " FROM "
                +USER_LOGIN_TABLE+ " WHERE "
                +USERNAME+ " LIKE " + "'"+user+"'";

//        String sql2 = "SELECT " +PASSWORD+ " FROM "
//                +ADMINITRATOR+ " WHERE "
//                +PASSWORD+ " LIKE " + "'"+pass+"'";

        Cursor cursor1 = db.rawQuery(sql1, null);
//        Cursor cursor2 = db.rawQuery(sql2, null);

        String ck1, ck2;
        if (cursor1.moveToFirst()){
            do {
                ck1 = cursor1.getString(0);
                d.add(ck1);
            }while (cursor1.moveToNext());
        }

//        if (cursor2.moveToFirst()){
//            do {
//                ck2 = cursor2.getString(0);
//                e.add(ck2);
//            }while (cursor2.moveToNext());
//        }

        db.close();
        cursor1.close();
//        cursor2.close();

        if (d.size() >= 1) {
            return "d";
        }
//        else if (e.size() >= 1){
//            return "e";
//        }else if (f.size() >= 1){
//            return "f";
//        }
        else {
            return "";
        }

    }


    public int editAdminAccount(String id, String newPass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PASSWORD, newPass);
//        db.close();
        int result = db.update(USER_LOGIN_TABLE, values,
                ID+ " =?",
                new String[]{id});
        Log.e(TAG, "editAdminAccount: "+result);
        return result;
    }

    //Check Exist User//

//    public boolean checkLoaiThu(String nameLT){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String sql = "SELECT "+USERNAME+" FROM "+USER_LOGIN_TABLE+" WHERE "+NAME_T+" LIKE "+"'"+nameLT+"'";
//        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
//        cursor.moveToFirst();
//        String check;
//        try {
//            check = cursor.getString(0);
//            if (!check.isEmpty()){
//                return true;
//            }
//            return false;
//        }catch (Exception e){
//            return false;
//        }
//    }

    public int editUserInfor(String username, String newPhone, String newName){
        int a;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHONENUMBER, newPhone);
        values.put(FULLNAME, newName);

        a = db.update(USER_LOGIN_TABLE, values, USERNAME+ "=?", new String[]{username});

        return a;
    }

    /**
     *
     * Types of book method
     *
     * **/

    public boolean checkTypesCodeExist(String typesCode){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TYPES_BOOK_TABLE,null,
                    TYPESCODE+" =?",
                    new String[]{typesCode},
                    null, null, null);
        }catch (Exception e){
            Log.e(TAG, "checkExist: "+e);
        }

        if (cursor.moveToFirst()){
            do {

                if (cursor != null){
                    check = true;
                    break;
                }

            }while (cursor.moveToNext());
        }

        return check;
    }

    public boolean checkTypesNameExist(String typesCode){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TYPES_BOOK_TABLE,null,
                    TYPESNAME+" =?",
                    new String[]{typesCode},
                    null, null, null);
        }catch (Exception e){
            Log.e(TAG, "checkExist: "+e);
        }

        if (cursor.moveToFirst()){
            do {

                if (cursor != null){
                    check = true;
                    break;
                }

            }while (cursor.moveToNext());
        }

        return check;
    }

    public boolean addTypesOfBook(mTypesOfBook m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TYPESCODE, m.getTypesCode());
        values.put(TYPESNAME, m.getTypesName());
        values.put(TYPESLOCATION, m.getTypesLocation());
        values.put(TYPESDESCRIPTION, m.getTypesDescription());

        long result = db.insert(TYPES_BOOK_TABLE, null, values);
        db.close();
        if (result < 1){
            Log.e(TAG, "addTypesOfBook: "+result);
            return false;
        }else {
            return true;
        }
    }

    //get types of boof
    public List<String> getTypesName(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+TYPESNAME+" FROM "+TYPES_BOOK_TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                String m = cursor.getString(0);
                list.add(m);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    //Get All tYPES
    public List<mTypesOfBook> getAllTypes(){
        List<mTypesOfBook> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+TYPES_BOOK_TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mTypesOfBook m = new mTypesOfBook();
                m.setTypesCode(cursor.getString(1));
                m.setTypesName(cursor.getString(2));
                m.setTypesLocation(cursor.getString(3));
                list.add(m);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<mTypesOfBook> getAllTypesToDelete(){
        List<mTypesOfBook> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+TYPES_BOOK_TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mTypesOfBook mUser = new mTypesOfBook();
                mUser.setId(Integer.parseInt(cursor.getString(0)));
                mUser.setTypesCode(cursor.getString(1));
                mUser.setTypesName(cursor.getString(2));
                mUser.setTypesLocation(cursor.getString(3));
                mUser.setTypesDescription(cursor.getString(4));
                list.add(mUser);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int deleteTypes(mTypesOfBook m){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.advancedandroid.quanlysach_newui/databases/fpolyBook", null);

        }catch (SQLException e){
            Log.e(TAG, "deleteUser: "+e);
        }

        int sql = db.delete(TYPES_BOOK_TABLE, ID+ "=?",
                new String[]{String.valueOf(m.getId())});

        db.close();
        if (sql == 0){
            return -1;
        }else {
            return 1;
        }

    }

    public int editTypes(String username, String newPhone, String newName){
        int a;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TYPESNAME, newPhone);
        values.put(TYPESLOCATION, newName);

        a = db.update(TYPES_BOOK_TABLE, values, TYPESCODE+ "=?", new String[]{username});

        return a;
    }


    /**
     *
     * Book's Method
     *
     *
     * */

    public boolean addBook(mBook m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BOOKCODE, m.getBookCode());
        values.put(BOOKNAME, m.getBookName());
        values.put(TYPESOFBOOK, m.getTypesOfBook());
        values.put(BOOKAUTHOR, m.getBookAuthor());
        values.put(BOOKPRICE, m.getBookPrice());
        values.put(BOOKAMOUNT, m.getBookAmount());

        long result = db.insert(BOOK_TABLE, null, values);
        db.close();
        if (result < 1){
            Log.e(TAG, "addBook: "+result);
            return false;
        }else {
            return true;
        }
    }

    public int getAmountBookByBookCodeInInventory(String code){
        int totalBook = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT sum("+BOOKAMOUNT+") FROM "+BOOK_TABLE+" WHERE "+BOOKCODE+" LIKE "+"'"+code+"'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        totalBook = cursor.getInt(0);
        Log.e(TAG, "getAmountBookByBookCode: "+totalBook);
        return totalBook;
    }

    public boolean checkExist(String bookCode){
        boolean check = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(BOOK_TABLE,null,
                    BOOKCODE+" =?",
                    new String[]{bookCode},
                    null, null, null);
        }catch (Exception e){
            Log.e(TAG, "checkExist: "+e);
        }

        if (cursor.moveToFirst()){
            do {

                if (cursor != null){
                    check = true;
                    break;
                }

            }while (cursor.moveToNext());
        }

        return check;
    }

    //Get all book
    public List<mBook> getAllBook(){
        List<mBook> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+BOOK_TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mBook m = new mBook();
                m.setTypesOfBook(cursor.getString(4));
                m.setBookName(cursor.getString(2));
                m.setBookCode(cursor.getString(1));
                m.setBookPrice(Double.parseDouble(cursor.getString(5)));
                m.setBookAmount(Double.parseDouble(cursor.getString(6)));
                list.add(m);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public double getBookPriceByCode(String bookCode){
        double price = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT "+BOOKPRICE+" FROM "+BOOK_TABLE+ " WHERE "+BOOKCODE+ " LIKE " +"'"+bookCode+"'";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                price = cursor.getDouble(0);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return price;
    }

    public List<mBook> getAllBookToDelete(){
        List<mBook> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+BOOK_TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mBook mUser = new mBook();
                mUser.setBookId(Integer.parseInt(cursor.getString(0)));
                mUser.setBookCode(cursor.getString(1));
                mUser.setBookName(cursor.getString(2));
                mUser.setBookAuthor(cursor.getString(3));
                mUser.setTypesOfBook(cursor.getString(4));
                mUser.setBookPrice(Double.parseDouble(cursor.getString(5)));
                mUser.setBookAmount(Double.parseDouble(cursor.getString(6)));
                list.add(mUser);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int deleteBook(mBook m){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.advancedandroid.quanlysach_newui/databases/fpolyBook", null);

        }catch (SQLException e){
            Log.e(TAG, "deleteUser: "+e);
        }

        int sql = db.delete(BOOK_TABLE, ID+ "=?",
                new String[]{String.valueOf(m.getBookId())});

        db.close();
        if (sql == 0){
            return -1;
        }else {
            return 1;
        }

    }

    public int deleteBookWithTypesName(String typesName){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.advancedandroid.quanlysach_newui/databases/fpolyBook", null);

        }catch (SQLException e){
            Log.e(TAG, "deleteUser: "+e);
        }

        int sql = db.delete(BOOK_TABLE, TYPESOFBOOK+ "=?",
                new String[]{typesName});

        db.close();
        if (sql == 0){
            return -1;
        }else {
            return 1;
        }

    }

    public int editBook(String code, String name, String author, String types, String price, String amount){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOKNAME, name);
        values.put(BOOKAUTHOR, author);
        values.put(TYPESOFBOOK, types);
        values.put(BOOKPRICE, price);
        values.put(BOOKAMOUNT, amount);

        int a = db.update(BOOK_TABLE, values, BOOKCODE+ "=?", new String[]{code});
        db.close();
        Log.e(TAG, "editBook: "+a);
        return a;
    }

    public int updateAmountBook(String code, int amount){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOKAMOUNT, amount);

        int a = db.update(BOOK_TABLE, values, BOOKCODE+ "=?", new String[]{code});
        db.close();
        Log.e(TAG, "updateAmount: "+a);
        return a;
    }

    /**
     *
     * Bill's Methods
     *
     * **/

    public boolean addBill(String m, String n){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BILLCODE, m);
        values.put(BILLDATE, n);

        long result = db.insert(BILL_TABLE, null, values);
        Log.e(TAG, "addBill: "+result);

        db.close();
        if (result < 1){

            return false;
        }else {
            return true;
        }
    }

    //Get all book
    public List<mBill> getAllBill(){
        List<mBill> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+BILL_TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mBill m = new mBill();
                m.setBillCode(cursor.getString(1));
                m.setBillDate(cursor.getString(2));
                list.add(m);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public List<mBill> getAllBillToDelete(){
        List<mBill> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+BILL_TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mBill mUser = new mBill();
                mUser.setId(Integer.parseInt(cursor.getString(0)));
                mUser.setBillCode(cursor.getString(1));
                mUser.setBillDate(cursor.getString(2));
                list.add(mUser);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int deleteBill(mBill m){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.advancedandroid.quanlysach_newui/databases/fpolyBook", null);

        }catch (SQLException e){
            Log.e(TAG, "deleteUser: "+e);
        }

        int sql = db.delete(BILL_TABLE, ID+ "=?",
                new String[]{String.valueOf(m.getId())});

        db.close();
        if (sql == 0){
            return -1;
        }else {
            return 1;
        }

    }

    public int deleteBillWithCode(String m){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.advancedandroid.quanlysach_newui/databases/fpolyBook", null);

        }catch (SQLException e){
            Log.e(TAG, "deleteUser: "+e);
        }

        int sql = db.delete(BILLDETAIL_TABLE, BILLCODE+ "=?",
                new String[]{m});

        db.close();
        if (sql == 0){
            return -1;
        }else {
            return 1;
        }

    }

    public int editBill(mBill m){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BILLCODE, m.getBillCode());
        values.put(BILLDATE, m.getBillDate());

        int a = db.update(BILL_TABLE, values, ID+ "=?", new String[]{String.valueOf(m.getId())});
        db.close();
        Log.e(TAG, "editBill: "+a);
        return a;
    }

    /**
     *
     * Bill Detail Method
     *
     * **/
    public boolean addBillDetail(mBillDetail m){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(BILLCODE, m.getBillCode());
        values.put(BOOKCODE, m.getBookCode());
        values.put(BOOKAMOUNT, m.getAmount());
        values.put(BOOKPRICE, m.getUnitPrice());
        values.put(TOTALAMOUNT, m.getTotalAmount());

        long result = db.insert(BILLDETAIL_TABLE, null, values);
        Log.e(TAG, "addBillDetail: "+result);
        db.close();
        if (result < 1){
            return false;
        }else {
            return true;
        }
    }

    //Get all Bill Detail
    public List<mBillDetail> getBillDetailByBillCode(String billCode){
        List<mBillDetail> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+BILLDETAIL_TABLE+ " WHERE " +BILLCODE+ " LIKE " +"'"+billCode+"'";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mBillDetail m = new mBillDetail();
                m.setBookCode(cursor.getString(2));
                m.setAmount(cursor.getInt(3));
                m.setUnitPrice(cursor.getDouble(4));
                m.setTotalAmount(cursor.getDouble(5));
                list.add(m);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public double getTotalAmountBillDetail(String billCode){
        double totalAmount = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT sum("+TOTALAMOUNT+") FROM "+BILLDETAIL_TABLE+" WHERE "+BILLCODE+" LIKE "+"'"+billCode+"'";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){
            totalAmount = cursor.getDouble(0);
        }
        return totalAmount;
    }

    public List<mBillDetail> getAllBillDetailToDelete(){
        List<mBillDetail> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+BILLDETAIL_TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do {
                mBillDetail mUser = new mBillDetail();
                mUser.setId(Integer.parseInt(cursor.getString(0)));
                mUser.setBillCode(cursor.getString(1));
                mUser.setBookCode(cursor.getString(2));
                mUser.setAmount(Integer.parseInt(cursor.getString(3)));
                mUser.setUnitPrice(Double.parseDouble(cursor.getString(4)));
                mUser.setTotalAmount(Double.parseDouble(cursor.getString(5)));

                list.add(mUser);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public int deleteBillDetail(mBillDetail m){
        SQLiteDatabase db = null;
        try {
            db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.advancedandroid.quanlysach_newui/databases/fpolyBook", null);

        }catch (SQLException e){
            Log.e(TAG, "deleteUser: "+e);
        }

        int sql = db.delete(BILLDETAIL_TABLE, ID+ "=?",
                new String[]{String.valueOf(m.getId())});

        db.close();
        if (sql == 0){
            return -1;
        }else {
            return 1;
        }

    }

    public int getAmountBookByBookCodeInBillDetail(String code){
        int totalSumBook;
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT sum("+BOOKAMOUNT+") FROM "+BILLDETAIL_TABLE+" WHERE "+BOOKCODE+" LIKE "+"'"+code+"'";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        totalSumBook = cursor.getInt(0);
        Log.e(TAG, "getAmountBookByBookCode: "+totalSumBook);
        return totalSumBook;
    }


    public List<mBook> getTheBestSellingBook(String month){
        List<mBook> dsSach = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        if (Integer.parseInt(month)<10){
            month = "0"+month;
        }
        String sSQL = "SELECT "+BOOKCODE+", SUM("+BOOKAMOUNT+") AS soLuong FROM "+BILLDETAIL_TABLE+" INNER JOIN "+BILL_TABLE+" ON billtable.billcode = billDetailTable.billcode " +
                " WHERE strftime('%m',"+BILLDATE+") LIKE '"+month+"'GROUP BY "+BOOKCODE+" ORDER BY soluong DESC LIMIT 10";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            Log.d("//=====",c.getString(0));
            mBook s = new mBook();
            s.setBookCode(c.getString(0));
            s.setBookAmount(c.getInt(1));
            s.setBookPrice(0);
            s.setTypesOfBook("");
            s.setBookName("");
            dsSach.add(s);
            c.moveToNext();
        }
        c.close();
        return dsSach;
    }

    /**
     *
     * Get doanh thu theo thoi gian
     *
     * **/

    public double getDoanhThuTheoNgay(){
        SQLiteDatabase db = this.getReadableDatabase();
        double doanhThu = 0;
        String sSQL ="SELECT sum(tongtien) " +
                    "FROM (SELECT SUM(book_table.bookPrice * billDetailTable.bookAmount) as 'tongtien' " +
                    "FROM "+BILL_TABLE+" INNER JOIN billDetailTable on billtable.billcode = billDetailTable.billcode " +
                    "INNER JOIN "+BOOK_TABLE+" on billDetailTable.bookCode = book_table.bookCode " +
                    "WHERE billtable.billdate = date('now') " +
                    "GROUP BY billDetailTable.bookCode)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }
    public double getDoanhThuTheoThang(){
        SQLiteDatabase db = this.getReadableDatabase();
        double doanhThu = 0;
        String sSQL ="SELECT SUM(tongtien) FROM (SELECT SUM(book_table.bookPrice * billDetailTable.bookAmount) AS 'tongtien' " +
        "FROM "+BILL_TABLE+" INNER JOIN "+BILLDETAIL_TABLE+" on billtable.billcode = billDetailTable.billcode " +
        "INNER JOIN "+BOOK_TABLE+" on billDetailTable.bookCode = book_table.bookcode " +
                "WHERE strftime('%m',billtable.billdate) = strftime('%m','now') GROUP BY billDetailTable.bookCode)tmp";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }
    public double getDoanhThuTheoNam(){
        SQLiteDatabase db = this.getReadableDatabase();
        double doanhThu = 0;
        String sSQL ="SELECT SUM(tongtien) from (SELECT SUM(book_table.bookPrice * billDetailTable.bookAmount) as 'tongtien' " +
        "FROM "+BILL_TABLE+" INNER JOIN "+BILLDETAIL_TABLE+" on billtable.billcode = billDetailTable.billcode " +
        "INNER JOIN "+BOOK_TABLE+" on billDetailTable.bookCode = book_table.bookcode " +
                "WHERE strftime('%Y',billtable.billdate) = strftime('%Y','now') GROUP BY billDetailTable.bookCode)tmp";

        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

}
