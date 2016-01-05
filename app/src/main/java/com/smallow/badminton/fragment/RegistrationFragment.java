package com.smallow.badminton.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.smallow.badminton.BadmintonApplication;
import com.smallow.badminton.R;
import com.smallow.badminton.sys.base.BaseFragment;
import com.smallow.badminton.sys.common.CommonBaseAdapter;
import com.smallow.badminton.sys.common.ViewHolder;
import com.smallow.badminton.sys.ui.MyGridView;
import com.smallow.badminton.sys.ui.MyListView;
import com.smallow.badminton.sys.utils.CommonUtils;
import com.smallow.badminton.view.DataStateBox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import smallow.core.ActionCallbackListener;
import smallow.model.ActivityRecord;
import smallow.model.RegistrationPerson;

/**
 * Created by smallow on 15/12/2.
 */
public class RegistrationFragment extends BaseFragment implements View.OnClickListener, DataStateBox.DataStateBoxListener {
    private DataStateBox mStateView;
    //MyListView mListView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MyGridView myGridView;
    RegistrationPersonAdapter mAdapter;
    private TextView mDate, mAddress, mChargePerson, mChargePersonPhone, mTime, mStatus, mRefresh, mSubmit;
    private LinearLayout activityRecordLayout, noActivityMessageLayout;


    private List<RegistrationPerson> persons = new ArrayList<>();
    private Integer groupId,loginUserId,activityRecordId;
    private String loginName,loginRoleType;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    protected void onInitWidgets(View rootView, Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        sharedPreferences=getActivity().getSharedPreferences("badminton", Context.MODE_PRIVATE);
        groupId=sharedPreferences.getInt("groupId", 0);
        loginUserId =sharedPreferences.getInt("loginUserId", 0);
        loginRoleType=sharedPreferences.getString("roleType", "4");
        loginName=sharedPreferences.getString("name","");
        if(loginRoleType!=null &&  ("0".equals(loginRoleType) || "1".equals(loginRoleType)) ){
            initHeadView(false, getResources().getString(R.string.fragment_registration_title),"发起活动");
        }else{
            initHeadView(false, getResources().getString(R.string.fragment_registration_title),null);
        }

        initView();
        initData();
    }

    private void initView() {
        mStateView = findViewById(R.id.data_state_box);
        mStateView.setDataSateBoxListener(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#F14E41"));
       /* mListView = findViewById(R.id.activity_main_listview);
        String[] fakeTweets = getResources().getStringArray(R.array.fake_tweets);
        mAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,fakeTweets);
        mListView.setAdapter((android.widget.ListAdapter) mAdapter);*/
        myGridView = findViewById(R.id.grid_fragment_registration_today_activity_members);
        mAdapter = new RegistrationPersonAdapter(getActivity(), persons, R.layout.adapter_baoming_personal_item);
        myGridView.setAdapter(mAdapter);
        mDate = findViewById(R.id.tv_fragment_registration_today_activity_date);
        mTime = findViewById(R.id.tv_fragment_registration_today_activity_time);
        mAddress = findViewById(R.id.tv_fragment_registration_today_activity_address);
        mChargePerson = findViewById(R.id.tv_fragment_registration_today_activity_charge_person);
        mChargePersonPhone = findViewById(R.id.tv_fragment_registration_today_activity_charge_person_phone);
        activityRecordLayout = findViewById(R.id.layout_fragment_registration_today_record);
        noActivityMessageLayout = findViewById(R.id.layout_fragment_registration_today_record_no_activity_messge);
        mStatus = findViewById(R.id.tv_fragment_registration_today_activity_status);
        mRefresh = findViewById(R.id.tv_fragment_registration_refresh);
        mSubmit = findViewById(R.id.tv_fragment_registration_submit);
        mRefresh.setOnClickListener(this);
        mSubmit.setOnClickListener(this);


    }

    private void initHeadView(boolean displayNavBackImg, String title,String menuRight) {
        ImageView mHeadBavBack;
        TextView mHeadTitle,mMenuRight;
        mHeadBavBack = findViewById(R.id.common_head_nav_back);
        mHeadTitle = findViewById(R.id.common_head_title);
        mMenuRight=findViewById(R.id.common_head_nav_menu);
        mHeadTitle.setText(title);
        if (!displayNavBackImg) {
            mHeadBavBack.setOnClickListener(null);
            mHeadBavBack.setVisibility(View.GONE);
        } else {
            mHeadBavBack.setOnClickListener(this);
            mHeadBavBack.setVisibility(View.VISIBLE);
        }
        if(menuRight!=null && !"".equals(menuRight)){
            mMenuRight.setText(menuRight);
            mMenuRight.setOnClickListener(this);
            mMenuRight.setVisibility(View.VISIBLE);
        }
    }

    private void initData() {
        mStateView.setState(DataStateBox.State.INIT_LOADING);
        ((BadmintonApplication) getActivity().getApplication()).getAppAction().getTodayActivityRecord(groupId,new ActionCallbackListener<ActivityRecord>() {
            @Override
            public void onSuccess(ActivityRecord data) {
                if (data != null) {
                    mStateView.setState(DataStateBox.State.HIDE);
                    persons = data.getPersons();
                    mAdapter.setData(persons, true);
                    mChargePerson.setText(data.getChargePerson());
                    mChargePersonPhone.setText(data.getContactNumber());
                    mDate.setText(data.getDate() + " " + data.getDate_week());
                    mTime.setText(data.getStartTime() + " -- " + data.getEndTime());
                    mAddress.setText(data.getVenue());
                    mStatus.setText(data.getStatus());
                    activityRecordId=data.getId();
                } else {
                    mStateView.setState(DataStateBox.State.EMPTY_DATA);
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                if (message != null && message.equals("empty data")) {
                    // mStateView.setState(DataStateBox.State.EMPTY_DATA);
                    mStateView.setState(DataStateBox.State.HIDE);
                    activityRecordLayout.setVisibility(View.GONE);
                    noActivityMessageLayout.setVisibility(View.VISIBLE);
                } else {
                    mStateView.setState(DataStateBox.State.LOAD_ERROR);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_fragment_registration_refresh:
                initData();
                break;
            case R.id.tv_fragment_registration_submit:
                submit();
                break;
            case R.id.common_head_nav_menu:
                createActivityRecord();
                break;
            case R.id.layout_create_activity_record_choose_date:
                chooseDate(date);
                break;
            case R.id.layout_create_activity_record_choose_start_time:
                chooseTime(startTime);
                break;
            case R.id.layout_create_activity_record_choose_end_time:
                chooseTime(endTime);
                break;
            case R.id.layout_create_activity_record_choose_charge_person:
                chooseChargePerson(chargePerson);
                break;
            default:
                break;
        }
    }

    private void chooseChargePerson(final TextView chargePerson) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.charge_person_layout, null);
        Spinner spinner= (Spinner) view.findViewById(R.id.charge_person_list);
        String items[]=getActivity().getResources().getStringArray(R.array.charge_person);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String string = adapterView.getItemAtPosition(i).toString();
                chargePerson.setText(string);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        builder.setView(view);
        builder.setTitle("设置日期信息");
        builder.create().show();
    }

    /**
     * 群主或管理发起活动
     */
    TextView date,startTime,endTime,chargePerson;
    private void createActivityRecord() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View contentView=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_create_activity_record, null);
        builder.setTitle("发起活动");
        builder.setView(contentView);
        AlertDialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        RelativeLayout chooseDate= (RelativeLayout) contentView.findViewById(R.id.layout_create_activity_record_choose_date);
        date= (TextView) contentView.findViewById(R.id.tv_dialog_create_activity_record_choose_date);
        startTime= (TextView) contentView.findViewById(R.id.tv_dialog_create_activity_record_choose_start_time);
        endTime= (TextView) contentView.findViewById(R.id.tv_dialog_create_activity_record_choose_end_time);
        chargePerson= (TextView) contentView.findViewById(R.id.tv_dialog_create_activity_record_choose_charge_person);
        RelativeLayout chooseStartTime= (RelativeLayout) contentView.findViewById(R.id.layout_create_activity_record_choose_start_time);
        RelativeLayout chooseEndTime= (RelativeLayout) contentView.findViewById(R.id.layout_create_activity_record_choose_end_time);
        RelativeLayout chargePerson= (RelativeLayout) contentView.findViewById(R.id.layout_create_activity_record_choose_charge_person);
        chooseDate.setOnClickListener(this);
        chooseStartTime.setOnClickListener(this);
        chooseEndTime.setOnClickListener(this);
        chargePerson.setOnClickListener(this);
    }


    private Calendar calendar; // 通过Calendar获取系统时间

    /**
     * 发布活动选择日期
     */
    private void chooseDate(final TextView textView){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.date_layout, null);
        final DatePicker datePicker = (DatePicker) view
                .findViewById(R.id.date_picker);
        // 设置日期简略显示 否则详细显示 包括:星期周
        datePicker.setCalendarViewShown(false);
        // 初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        // 设置date布局
        builder.setView(view);
        builder.setTitle("设置日期信息");
        builder.setPositiveButton("确  定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 日期格式
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%d-%02d-%02d",
                                datePicker.getYear(),
                                datePicker.getMonth() + 1,
                                datePicker.getDayOfMonth()));
                        textView.setText(sb);
                        // 赋值后面闹钟使用
                        //mYear = datePicker.getYear();
                        //mMonth = datePicker.getMonth();
                        //mDay = datePicker.getDayOfMonth();
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("取  消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    private void chooseTime(final TextView textView){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.time_layout, null);
        final TimePicker timePicker= (TimePicker) view.findViewById(R.id.time_picker);
        calendar.setTimeInMillis(System.currentTimeMillis());
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
        timePicker.setIs24HourView(true);
        builder.setView(view);
        builder.setTitle("设置时间信息");
        builder.setPositiveButton("确  定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        // 日期格式
                        StringBuffer sb = new StringBuffer();
                        sb.append(String.format("%02d:%02d",
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute()));
                        textView.setText(sb);
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("取  消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }


    /**
     * 报名参加活动
     */
    private void submit() {
        showLoadingDialog("正在提交数据,请稍后...");
        ((BadmintonApplication) getActivity().getApplication()).getAppAction().registrateTodayActivity(loginUserId,activityRecordId,groupId,new ActionCallbackListener<String>(){
            @Override
            public void onSuccess(String data) {
                dismissLoadingDialog();
                if(data!=null && data.equals("success")){
                    Toast.makeText(getActivity(),"报名成功!",Toast.LENGTH_SHORT).show();
                    initData();
                }else{
                    Toast.makeText(getActivity(),"报名未成功!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                dismissLoadingDialog();
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onReqeustReloadData(DataStateBox.State curState, DataStateBox v) {
        initData();
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
