package com.smallow.badminton.sys.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.smallow.badminton.R;

/**
 * Created by Administrator on 2015/11/3.
 */
public class CustomProgressDialogThree extends Dialog {

    private String mMsg = "Loading";

    public void setMsg(String msg)
    {
        this.mMsg = msg;
    }

    public CustomProgressDialogThree(Context context) {
        super(context,R.style.custom_progress_dialog_three_style);
        init();
    }


    private void init(){
        setContentView(R.layout.custom_progress_dialog_three_layout);
        TextView title = (TextView)findViewById(R.id.id_dialog_loading_msg);
        title.setText(mMsg);
    }

}
