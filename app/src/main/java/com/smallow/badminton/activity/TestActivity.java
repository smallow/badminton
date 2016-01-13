package com.smallow.badminton.activity;

import android.app.Activity;
import android.os.Bundle;

import com.smallow.badminton.R;
import com.smallow.badminton.view.SimpleLoadingView;

/**
 * Created by smallow on 16/1/13.
 */
public class TestActivity extends Activity {

    private SimpleLoadingView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        view = new SimpleLoadingView(this);
        view.setMsg("正在登陆请稍后...");
        view.show();
    }
}
