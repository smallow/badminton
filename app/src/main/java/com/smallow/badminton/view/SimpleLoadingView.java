package com.smallow.badminton.view;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.smallow.badminton.R;

/**
 * Created by smallow on 16/1/13.
 */
public class SimpleLoadingView extends Dialog {

    private TextView textView;

    private String mMsg = "正在加载数据请稍后...";

    public void setMsg(String msg) {
        textView.setText(msg);
    }

    public SimpleLoadingView(Context context) {
        super(context, R.style.simple_progress_dialog_three_style);
        init();
    }

    private void init() {
        setContentView(R.layout.simple_loading_view);
        textView = (TextView) findViewById(R.id.simple_loading_view_text);
    }
}
