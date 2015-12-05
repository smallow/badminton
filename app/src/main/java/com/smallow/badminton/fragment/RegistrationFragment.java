package com.smallow.badminton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smallow.badminton.BadmintonApplication;
import com.smallow.badminton.R;
import com.smallow.badminton.sys.base.BaseFragment;
import com.smallow.badminton.view.DataStateBox;

import java.util.List;

import smallow.core.ActionCallbackListener;
import smallow.model.RegistrationPerson;

/**
 * Created by smallow on 15/12/2.
 */
public class RegistrationFragment extends BaseFragment implements View.OnClickListener {
    private DataStateBox mStateView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    protected void onInitWidgets(View rootView, Bundle savedInstanceState) {
        initHeadView(false, getResources().getString(R.string.fragment_registration_title));
        mStateView=findViewById(R.id.data_state_box);
        initData();
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

    private void initData() {
        mStateView.setState(DataStateBox.State.INIT_LOADING);
        ((BadmintonApplication)getActivity().getApplication()).getAppAction().listCoupon(1,new ActionCallbackListener<List<RegistrationPerson>>() {
            @Override
            public void onSuccess(List<RegistrationPerson> data) {
                if(data!=null && data.size()>0){
                    mStateView.setState(DataStateBox.State.HIDE);
                }else{
                    mStateView.setState(DataStateBox.State.EMPTY_DATA);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                mStateView.setState(DataStateBox.State.LOAD_ERROR);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
                break;
        }
    }
}
