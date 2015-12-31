package com.smallow.badminton.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smallow.badminton.BadmintonApplication;
import com.smallow.badminton.MainActivity;
import com.smallow.badminton.R;
import com.smallow.badminton.sys.base.BaseActivity;

import smallow.core.ActionCallbackListener;
import smallow.model.Member;

/**
 * Created by smallow on 15/12/30.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView mMobile, mPwd;
    private Button mLogin;
    private BadmintonApplication application;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences=getSharedPreferences("badminton",MODE_PRIVATE);
        application=((BadmintonApplication)getApplication());
        initView();
    }

    private void initView() {
        mMobile = (TextView) findViewById(R.id.etv_aty_login_mobile);
        mPwd = (TextView) findViewById(R.id.etv_aty_login_pwd);
        mLogin = (Button) findViewById(R.id.btn_aty_login_submit);
        mLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_aty_login_submit:
                login();
                break;
        }
    }

    private void login(){
        String mobile=mMobile.getEditableText().toString();
        String pwd=mPwd.getEditableText().toString();
        if(mobile==null || "".equals(mobile)){
            Toast.makeText(this,"请输入手机号!",Toast.LENGTH_SHORT).show();
        }

        if(pwd==null || "".equals(pwd)){
            Toast.makeText(this,"请输入密码!",Toast.LENGTH_SHORT).show();
        }

        application.getAppAction().login(mobile, pwd, new ActionCallbackListener<Member>() {
            @Override
            public void onSuccess(Member data) {
                Toast.makeText(LoginActivity.this,"登陆成功:"+data.getName(),Toast.LENGTH_SHORT).show();
                saveLoginInfo(data);
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(LoginActivity.this,"登陆失败:"+message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveLoginInfo(Member data) {
        sharedPreferences.edit().putInt("loginUserId",data.getId()).commit();
        sharedPreferences.edit().putInt("groupId",data.getGroupId()).commit();
        //sharedPreferences.edit().putInt("")
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
