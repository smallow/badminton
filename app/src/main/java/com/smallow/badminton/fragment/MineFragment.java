package com.smallow.badminton.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smallow.badminton.R;
import com.smallow.badminton.activity.LoginActivity;
import com.smallow.badminton.sys.base.BaseFragment;

/**
 * Created by smallow on 15/11/14.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {


    private RelativeLayout mQuit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    protected void onInitWidgets(View rootView, Bundle savedInstanceState) {
        initView();
        initHeadView(false, getResources().getString(R.string.fragment_mine_title));
    }

    private void initView() {
        mQuit=findViewById(R.id.layout_fragment_mine_quit);
        mQuit.setOnClickListener(this);
    }

    private void initHeadView(boolean displayNavBackImg, String title) {
        ImageView mHeadBavBack;
        TextView mHeadTitle;
        mHeadBavBack = findViewById(R.id.common_head_nav_back);
        mHeadTitle = findViewById(R.id.common_head_title);
        mHeadTitle.setText(title);
        if (!displayNavBackImg) {
            mHeadBavBack.setOnClickListener(null);
            mHeadBavBack.setVisibility(View.GONE);
        } else {
            mHeadBavBack.setOnClickListener(this);
            mHeadBavBack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_fragment_mine_quit:
                quit();
                break;
        }

    }

    /**
     * 退出登陆
     */
    private void quit() {
        getActivity().getSharedPreferences("badminton", Context.MODE_PRIVATE).edit().clear().commit();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }
}
