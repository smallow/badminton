package com.smallow.badminton;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.FrameLayout;


import com.smallow.badminton.sys.base.BaseActivity;


public class MainActivity extends BaseActivity {
    //#F14E41
    private long firstTime;
    private Toolbar toolbar;
    private boolean isLight;
    private SharedPreferences sp;
    private FrameLayout fl_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        isLight = sp.getBoolean("isLight", true);
        initView();
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("爱羽玩");
       // setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
    }



    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Snackbar sb = Snackbar.make(fl_content, "再按一次退出", Snackbar.LENGTH_SHORT);
            sb.getView().setBackgroundColor(getResources().getColor(isLight ? android.R.color.holo_red_dark : android.R.color.black));
            sb.show();
            firstTime = secondTime;
        } else {
            finish();
        }

    }
}
