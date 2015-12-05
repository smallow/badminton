package com.smallow.badminton;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import com.smallow.badminton.fragment.MineFragment;
import com.smallow.badminton.fragment.RegistrationFragment;
import com.smallow.badminton.sys.base.BaseActivity;
import com.zhy.autolayout.AutoLayout;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    //#F14E41
    private long firstTime;

    private SharedPreferences sp;
    private FrameLayout fl_content;

    private RadioGroup mainTabGroup;
    private FragmentManager fragmentManager;
    private RegistrationFragment registrationFragment;
    private MineFragment mineFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AutoLayout.getInstance().auto(this, true);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        fragmentManager = getSupportFragmentManager();
        initView();
        setTabSelection(0);
    }

    private void setTabSelection(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hiddenFragments(transaction);
        switch (i) {
            case 0:
                if (registrationFragment == null) {
                    registrationFragment = new RegistrationFragment();
                    transaction.add(R.id.fl_content, registrationFragment);
                } else {
                    transaction.show(registrationFragment);
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
        if (registrationFragment != null) {
            transaction.hide(registrationFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    private void initView() {


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
            sb.getView().setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            sb.show();
            firstTime = secondTime;
        } else {
            finish();
        }

    }


    @Override
    public void onClick(View view) {

    }


}
