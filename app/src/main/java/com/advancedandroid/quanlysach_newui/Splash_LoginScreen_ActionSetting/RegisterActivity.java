package com.advancedandroid.quanlysach_newui.Splash_LoginScreen_ActionSetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.R;
import com.advancedandroid.quanlysach_newui.mData.mUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtUser, edtPass1, edtPass2, edtFname, edtPhone;
    Button btnRegistration, btnClear, btnBack;
    DatabaseDAO databaseDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mapping();

        btnRegistration.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnRegistration){
            regis();
        }else if (v == btnClear){
            clear();
        }else if (v == btnBack){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void mapping(){
        edtUser = findViewById(R.id.edtUser_username);
        edtPass1 = findViewById(R.id.edtUser_Pass1);
        edtPass2 = findViewById(R.id.edtUser_Pass2);
        edtFname = findViewById(R.id.edtUser_fullname);
        edtPhone = findViewById(R.id.edtUser_phonenumber);
        btnClear = findViewById(R.id.btnUser_Clear);
        btnRegistration = findViewById(R.id.btnUser_Save);
        btnBack = findViewById(R.id.btnUser_Cancel);
    }

    private void regis(){

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
        if (edtUser.getText().toString().trim().isEmpty() ||
                edtPass1.getText().toString().isEmpty() ||
                edtPass2.getText().toString().isEmpty() ||
                edtPhone.getText().toString().isEmpty() ||
                edtFname.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else if (!edtPass1.getText().toString().equals(edtPass2.getText().toString())){
            Toast.makeText(getApplicationContext(), "Vui lòng xác nhận lại mật khẩu.", Toast.LENGTH_SHORT).show();
        }else {
            mUser mUser = new mUser();
            DatabaseDAO db = new DatabaseDAO(getApplicationContext());
            mUser.setUserName(edtUser.getText().toString());
            mUser.setPassWord(edtPass2.getText().toString());
            mUser.setPhoneNumber(edtPhone.getText().toString());
            mUser.setFullname(edtFname.getText().toString());

            boolean checkAddUser = db.addUserLogin(mUser);
            if (checkAddUser==true){
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                clear();
            }else {
                Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void clear(){
        edtUser.getText().clear();
        edtPass1.getText().clear();
        edtFname.getText().clear();
        edtPass2.getText().clear();
        edtPhone.getText().clear();
    }


    private boolean RegexUsername(String checkIn4){
        // Check username
        String regex = "[a-z0-9_-]{6,12}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(checkIn4);
        boolean m = matcher.matches();
        return m;
    }

    private boolean RegexPhone(String checkPhone){
        //Check PhoneNumber
        String regex = "((09|03|07|08|05)+([0-9]{8}))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(checkPhone);
        boolean m = matcher.matches();
        return m;
    }

    private boolean RegexMail(String checkMail){
        String regex = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(checkMail);
        boolean m = matcher.matches();
        return m;
    }

    private boolean RegexFname(String checkFname){
        String regex = "^[a-zA-Z\\\\sàáạã_-]{3,25}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(checkFname.trim());
        boolean m = matcher.matches();
        return m;
    }


}
