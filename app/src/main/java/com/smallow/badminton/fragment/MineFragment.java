package com.smallow.badminton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smallow.badminton.R;
import com.smallow.badminton.sys.base.BaseFragment;

/**
 * Created by smallow on 15/11/14.
 */
public class MineFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    protected void onInitWidgets(View rootView, Bundle savedInstanceState) {

    }
}
