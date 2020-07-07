package com.advancedandroid.quanlysach_newui.Splash_LoginScreen_ActionSetting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.MainActivity;
import com.advancedandroid.quanlysach_newui.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public boolean checkLogin;
    EditText edtUsername, edtPassword;
    TextView txtRegAdminAcc;
    Button btnLogin, btnBack;
    CheckBox chkRemember;
    SharedPreferences sharedPreferences;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapping();

        getShare();
        if (checkLogin == true){
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        //Login and remember
        sharedPreferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        edtUsername.setText(sharedPreferences.getString("USERNAME", ""));
        edtPassword.setText(sharedPreferences.getString("PASSWORD", ""));
        chkRemember.setChecked(sharedPreferences.getBoolean("CHKREMEMBER", false));

        btnLogin.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        txtRegAdminAcc.setOnClickListener(this);

    }

    private void mapping(){
        edtUsername = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnBack = findViewById(R.id.btnBack);
        chkRemember = findViewById(R.id.chkRemember);
        txtRegAdminAcc = findViewById(R.id.edtRegAdminAccount);

    }

    private void getShare(){
        sharedPreferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        boolean checkStatus = sharedPreferences.getBoolean("CHKREMEMBER", false);
        checkLogin = checkStatus;
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin){
            btnLogin();
        }else if (v == btnBack){
            finish();
        }else if (v == txtRegAdminAcc){
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        }
    }

    private void btnLogin(){
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        DatabaseDAO db = new DatabaseDAO(LoginActivity.this);
        List<String> AccSQL = db.getDataByUsername(username, password);

        String idSQL = AccSQL.get(0);
        String userSQL = AccSQL.get(1);
        String passSQL = AccSQL.get(2);

        if (userSQL.equals("") || passSQL.equals("")){
            Toast.makeText(this, "Sai tài khoản.", Toast.LENGTH_SHORT).show();
            edtUsername.getText().clear();
            edtPassword.getText().clear();
        }else if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Nhập tài khoản hoặc mật khẩu.", Toast.LENGTH_SHORT).show();
            edtUsername.getText().clear();
            edtPassword.getText().clear();
        }else {
            if ((username.equals(userSQL) && password.equals(passSQL))){

                if (chkRemember.isChecked()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("ID", idSQL);
                    editor.putString("USERNAME",username);
                    editor.putString("PASSWORD",password);
                    editor.putBoolean("CHKREMEMBER",true);
                    editor.commit();
                }else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("ID");
                    editor.remove("USERNAME");
                    editor.remove("PASSWORD");
                    editor.remove("CHKREMEMBER");
                    editor.commit();
                }

                Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_LONG).show();
                intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", idSQL);
                bundle.putString("USERNAME", userSQL);
                bundle.putString("PASS", passSQL);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }else {
                Toast.makeText(LoginActivity.this, "Username doesn't exist", Toast.LENGTH_LONG).show();

            }
        }
    }

}
