package com.smallow.badminton.sys.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.smallow.badminton.R;

/**
 * Created by smallow on 2015/10/30.
 */
public class CustomProgressDialogOne extends Dialog{


    private TextView textView;

    private String text;

    public CustomProgressDialogOne(Context context) {
        super(context, R.style.custom_progress_dialog_one_style);

        init();
    }

    public CustomProgressDialogOne(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    private void init(){
        setContentView(R.layout.custom_progress_dialog_one_layout);
        textView=(TextView)findViewById(R.id.text);
        if(text!=null)
            textView.setText(text);
        else
            textView.setText("正在处理请稍后...");
    }

    public void setMsg(String msg)
    {
        this.text = msg;
    }
}
