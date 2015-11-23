package com.smallow.badminton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.smallow.badminton.fragment.BaoMingFragment;
import com.smallow.badminton.fragment.MineFragment;
import com.zhy.autolayout.AutoLayout;


public class MainActivity extends FragmentActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {
    //#F14E41
    private long firstTime;
    private Toolbar toolbar;
    private boolean isLight;
    private SharedPreferences sp;
    private FrameLayout fl_content;

    private RadioGroup mainTabGroup;
    private FragmentManager fragmentManager;
    private BaoMingFragment baoMingFragment;
    private MineFragment mineFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoLayout.getInstance().auto(this, true);
        //AutoLayout.getInstance().auto(this, true);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        isLight = sp.getBoolean("isLight", true);
        fragmentManager = getSupportFragmentManager();
        initView();
        setTabSelection(0);
    }

    private void setTabSelection(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hiddenFragments(transaction);
        switch (i) {
            case 0:
                if (baoMingFragment == null) {
                    baoMingFragment = new BaoMingFragment();
                    transaction.add(R.id.fl_content, baoMingFragment);
                } else {
                    transaction.show(baoMingFragment);
                }

                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    transaction.add(R.id.fl_content, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hiddenFragments(FragmentTransaction transaction) {
        if (baoMingFragment != null) {
            transaction.hide(baoMingFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("爱羽玩");
        // setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(this);
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        mainTabGroup = (RadioGroup) findViewById(R.id.main_tab_group);
        mainTabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.main_tab1:
                        setTabSelection(0);
                        break;
                    case R.id.main_tab4:
                        setTabSelection(3);
                        break;
                }
            }
        });


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

        if (!msg.equals("")) {
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onClick(View view) {

    }


}
