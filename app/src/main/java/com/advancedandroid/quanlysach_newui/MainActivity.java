package com.advancedandroid.quanlysach_newui;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.advancedandroid.quanlysach_newui.DatabaseDAO.DatabaseDAO;
import com.advancedandroid.quanlysach_newui.HomeMenuFragment.BillFragment.BillFragment;
import com.advancedandroid.quanlysach_newui.HomeMenuFragment.BookFragment.BookFragment;
import com.advancedandroid.quanlysach_newui.HomeMenuFragment.StatisticFragment.StatisticBookFragment;
import com.advancedandroid.quanlysach_newui.HomeMenuFragment.StatisticFragment.StatisticTotalAmountFragment;
import com.advancedandroid.quanlysach_newui.HomeMenuFragment.TypesOfBookFragment.TypesOfBookFragment;
import com.advancedandroid.quanlysach_newui.HomeMenuFragment.UserFragment.UserFragment;
import com.advancedandroid.quanlysach_newui.Splash_LoginScreen_ActionSetting.LoginActivity;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;

public class MainActivity extends AppCompatActivity{

    private ResideMenu resideMenu;
    private Context mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemProfile;
    private ResideMenuItem itemCalendar;
    private ResideMenuItem itemSettings;

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAnimation();
        mContext = this;
        setUpMenu();

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.main_fragment, new HomeFragment());
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

    }

    private void changePass(){
        Bundle bundle = getIntent().getExtras();
        final String id = bundle.getString("ID");
        String user = bundle.getString("USERNAME");
        final String pass = bundle.getString("PASSWORD");

        Log.e(null, "acc: "+user+"//pass: "+pass);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater1 = MainActivity.this.getLayoutInflater();
        final View view1 = inflater1.inflate(R.layout.dialog_custom_change_pass, null);
        builder.setView(view1);
        final Dialog dialog = builder.create();
        dialog.show();

        final EditText txtAdminAcc = view1.findViewById(R.id.txtAdminAccount);
        final EditText edtPassword1 = view1.findViewById(R.id.edtAdminPass1);
        final EditText edtPassword2 = view1.findViewById(R.id.edtAdminPass2);

        txtAdminAcc.setText(user);

        Button btnCancel = view1.findViewById(R.id.btnChangePass_Cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btnChange = view1.findViewById(R.id.btnChangePass_Change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPassword1.equals("") || edtPassword2.equals("")){
                    Toast.makeText(mContext, "Vui lòng không để trống", Toast.LENGTH_SHORT).show();
                }else if (!edtPassword1.getText().toString().equals(edtPassword2.getText().toString())){
                    Toast.makeText(mContext, "Mật khẩu không giống nhau vui lòng xác nhận lại.", Toast.LENGTH_SHORT).show();
                }else {
                    String newPass = edtPassword2.getText().toString();
                    DatabaseDAO db = new DatabaseDAO(MainActivity.this);
                    Log.e(null, "onClick: "+id);
                    int result = db.editAdminAccount(id, newPass);
                    if (result>0){
                        Toast.makeText(mContext, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }
        });
    }

    private void relogin(){
        SharedPreferences sharedPreferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("USERNAME");
        editor.remove("PASSWORD");
        editor.remove("CHKREMEMBER");
        editor.commit();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void setUpMenu() {
        findViewById(R.id.txtHomeClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                if(Build.VERSION.SDK_INT>20){
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                    startActivity(i,options.toBundle());
                }else {
                    startActivity(i);
                }
            }
        });
        // attach to current activity;
//        resideMenu = new ResideMenu(this);
//
//        resideMenu.setBackground(R.drawable.menu_background);
//        resideMenu.attachToActivity(this);
//        resideMenu.setMenuListener(menuListener);
//        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
//        resideMenu.setScaleValue(0.6f);
//
//        // create menu items;
//        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
//        itemProfile  = new ResideMenuItem(this, R.drawable.icon_profile,  "Profile");
//        itemCalendar = new ResideMenuItem(this, R.drawable.icon_calendar, "Calendar");
//        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");
//
//        itemHome.setOnClickListener(this);
//        itemProfile.setOnClickListener(this);
//        itemCalendar.setOnClickListener(this);
//        itemSettings.setOnClickListener(this);
//
//        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
//        resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
//        resideMenu.addMenuItem(itemCalendar, ResideMenu.DIRECTION_RIGHT);
//        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);
//
//        // You can disable a direction by setting ->
//        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
//
//        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
//            }
//        });
//        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//            }
//        });

        //FPT Poly Menu Fragment
        findViewById(R.id.imgBook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeFragment(new BookFragment());
            }
        });

        findViewById(R.id.imgUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new UserFragment());
            }
        });

        findViewById(R.id.imgBookGender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new TypesOfBookFragment());
            }
        });

        findViewById(R.id.imgBill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new BillFragment());
            }
        });

        findViewById(R.id.bookStatisticFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new StatisticBookFragment());
            }
        });

        findViewById(R.id.statisticTotalAmount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new StatisticTotalAmountFragment());
            }
        });

    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return resideMenu.dispatchTouchEvent(ev);
//    }

//    @Override
//    public void onClick(View view) {
//
//        if (view == itemHome){
//
//        }else if (view == itemProfile){
//            changeFragment(new ProfileFragment());
//        }else if (view == itemCalendar){
//            changeFragment(new CalenderFragment());
//        }else if (view == itemSettings){
//            changeFragment(new SettingFragment());
//        }
//
//        resideMenu.closeMenu();
//    }

//    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
//        @Override
//        public void openMenu() {
//            Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void closeMenu() {
//            Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
//        }
//    };

    private void changeFragment(Fragment targetFragment){
//        resideMenu.clearIgnoredViewList();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, targetFragment)
                    .addToBackStack("fragment")
                    .commit();
    }

    // What good method is to access resideMenu？
//    public ResideMenu getResideMenu(){
//        return resideMenu;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting_change_pass:
                changePass();
                return true;
            case R.id.action_setting_logout:
                relogin();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.END);
            slide.setDuration(800);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }
}


