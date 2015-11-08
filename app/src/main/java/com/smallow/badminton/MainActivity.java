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


public class MainActivity extends AppCompatActivity {
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
        setSupportActionBar(toolbar);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.getItem(0).setTitle(sp.getBoolean("isLight", true) ? "夜间模式" : "日间模式");
        return true;
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
