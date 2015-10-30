package com.smallow.badminton.sys.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by smallow on 2015/10/28.
 */
public class BaseActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
