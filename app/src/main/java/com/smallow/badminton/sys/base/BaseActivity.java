package com.smallow.badminton.sys.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.smallow.badminton.BadmintonApplication;

import smallow.core.AppAction;

/**
 * Created by smallow on 2015/10/28.
 */
public abstract class BaseActivity extends FragmentActivity {

    // 上下文实例
    public Context context;
    // 应用全局的实例
    public BadmintonApplication application;
    // 核心层的Action实例
    public AppAction appAction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        context = getApplicationContext();
        application = (BadmintonApplication) this.getApplication();
        appAction = application.getAppAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
    }


    protected void hideInputMethod() {
        try {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getWindow().getDecorView()
                    .getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
