package com.smallow.badminton.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.smallow.badminton.R;
import com.smallow.badminton.bean.BaoMingPersona;
import com.smallow.badminton.sys.Constant;
import com.smallow.badminton.sys.base.BaseFragment;
import com.smallow.badminton.sys.base.InnerBaseAdapter;
import com.smallow.badminton.sys.utils.HttpUtils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smallow on 15/11/14.
 */
public class BaoMingFragment extends BaseFragment {
    private List<BaoMingPersona> data=new ArrayList<BaoMingPersona>();

    private PersonalAdapter adapter;
    private GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_baoming, container, false);
    }

    @Override
    protected void onInitWidgets(View rootView, Bundle savedInstanceState) {
        gridView= (GridView) findViewById(R.id.baoming_grid);
        adapter=new PersonalAdapter(data);
        gridView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {
        showLoadingDialog("正在获取今日活动数据...");
        HttpUtils.get(Constant.START, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                dismissLoadingDialog();
                BaoMingPersona bean1=new BaoMingPersona();
                bean1.setName("喜洋洋");
                BaoMingPersona bean2=new BaoMingPersona();
                bean2.setName("流星");
                BaoMingPersona bean3=new BaoMingPersona();
                bean3.setName("大梁");
                BaoMingPersona bean4=new BaoMingPersona();
                bean4.setName("凉白开");
                BaoMingPersona bean5=new BaoMingPersona();
                bean5.setName("休闲");
                data.clear();
                data.add(bean1);
                data.add(bean2);
                data.add(bean3);
                data.add(bean4);
                data.add(bean5);
                adapter.setData(data,true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                dismissLoadingDialog();
                Toast.makeText(getActivity(), "错误代码:"+statusCode, Toast.LENGTH_SHORT).show();
            }
        });
    }


        class PersonalAdapter extends InnerBaseAdapter<BaoMingPersona> {

        public PersonalAdapter(List<BaoMingPersona> data){
            setData(data,false);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoler viewHoler;
            if(convertView==null){
                viewHoler=new ViewHoler();
                convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_baoming_personal_item,null);
                viewHoler.name= (TextView) convertView.findViewById(R.id.baoming_personal_name);
                viewHoler.img= (ImageView) convertView.findViewById(R.id.baoming_personal_img);
                convertView.setTag(viewHoler);
            }else{
                viewHoler= (ViewHoler) convertView.getTag();
            }

            BaoMingPersona bean=getData(position);
            viewHoler.name.setText(bean.getName());
            return convertView;
        }

        class ViewHoler{
            TextView name;
            ImageView img;
        }
    }
}
