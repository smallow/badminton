package com.smallow.badminton.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.smallow.badminton.BadmintonApplication;
import com.smallow.badminton.R;
import com.smallow.badminton.sys.base.BaseFragment;
import com.smallow.badminton.sys.common.CommonBaseAdapter;
import com.smallow.badminton.sys.common.ViewHolder;
import com.smallow.badminton.sys.ui.MyGridView;
import com.smallow.badminton.sys.ui.MyListView;
import com.smallow.badminton.view.DataStateBox;

import java.util.ArrayList;
import java.util.List;

import smallow.core.ActionCallbackListener;
import smallow.model.ActivityRecord;
import smallow.model.RegistrationPerson;

/**
 * Created by smallow on 15/12/2.
 */
public class RegistrationFragment extends BaseFragment implements View.OnClickListener {
    private DataStateBox mStateView;
    //MyListView mListView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MyGridView myGridView;
    RegistrationPersonAdapter mAdapter;
    private TextView mDate,mAddress,mChargePerson,mChargePersonPhone,mTime;


    private List<RegistrationPerson> persons = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    protected void onInitWidgets(View rootView, Bundle savedInstanceState) {
        initHeadView(false, getResources().getString(R.string.fragment_registration_title));
        initView();
        initData();
    }

    private void initView() {
        mStateView = findViewById(R.id.data_state_box);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#F14E41"));
       /* mListView = findViewById(R.id.activity_main_listview);
        String[] fakeTweets = getResources().getStringArray(R.array.fake_tweets);
        mAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,fakeTweets);
        mListView.setAdapter((android.widget.ListAdapter) mAdapter);*/
        myGridView = findViewById(R.id.grid_fragment_registration_today_activity_members);
        mAdapter = new RegistrationPersonAdapter(getActivity(), persons, R.layout.adapter_baoming_personal_item);
        myGridView.setAdapter(mAdapter);
        mDate=findViewById(R.id.tv_fragment_registration_today_activity_date);
        mTime=findViewById(R.id.tv_fragment_registration_today_activity_time);
        mAddress=findViewById(R.id.tv_fragment_registration_today_activity_address);
        mAddress.setText("农业路沙口路千羽羽毛球馆1");
        mChargePerson=findViewById(R.id.tv_fragment_registration_today_activity_charge_person);
        mChargePersonPhone=findViewById(R.id.tv_fragment_registration_today_activity_charge_person_phone);



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
        ((BadmintonApplication) getActivity().getApplication()).getAppAction().getTodayActivityRecord(new ActionCallbackListener<ActivityRecord>() {
            @Override
            public void onSuccess(ActivityRecord data) {
                if (data != null) {
                    mStateView.setState(DataStateBox.State.HIDE);
                    persons = data.getPersons();
                    mAdapter.setData(persons, true);
                    mChargePerson.setText(data.getChargePerson());
                    mChargePersonPhone.setText(data.getId()+"");
                } else {
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

    class RegistrationPersonAdapter extends CommonBaseAdapter<RegistrationPerson> {
        RegistrationPersonAdapter(Context context, List<RegistrationPerson> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder viewHolder, RegistrationPerson item) {
            viewHolder.setText(R.id.baoming_personal_name, item.getName());
        }
    }
}
