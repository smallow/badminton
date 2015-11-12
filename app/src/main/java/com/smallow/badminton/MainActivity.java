package com.smallow.badminton;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.smallow.badminton.sys.base.BaseActivity;


public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {
    //#F14E41
    private long firstTime;
    private Toolbar toolbar;
    private boolean isLight;
    private SharedPreferences sp;
    private FrameLayout fl_content;
    private RadioButton tab1,tab2,tab3,tab4;
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
        toolbar.setOnMenuItemClickListener(this);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        tab1= (RadioButton) findViewById(R.id.main_tab1);
        tab2= (RadioButton) findViewById(R.id.main_tab2);
        tab3= (RadioButton) findViewById(R.id.main_tab3);
        tab4= (RadioButton) findViewById(R.id.main_tab4);
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        tab4.setOnClickListener(this);

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

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        String msg = "";
        switch (menuItem.getItemId()) {
            case R.id.ac_toolbar_reg:
                msg += "注册";
                break;
            case R.id.ac_toolbar_jq:
                msg += "加群";
                break;
            case R.id.ac_toolbar_rm:
                msg += "入盟";
                break;
        }

        if(!msg.equals("")) {
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onClick(View view) {

    }
}
