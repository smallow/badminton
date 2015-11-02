package com.smallow.badminton.sys.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.smallow.badminton.R;

/**
 * Created by Administrator on 2015/11/2.
 */
public class CustomProgressDialogTwo extends Dialog {
    private ImageView mFivIcon;
    private TextView mHtvText;
    private String mText;
    private Context myContext;

    public CustomProgressDialogTwo(Context context) {
        super(context);
    }


    public CustomProgressDialogTwo(Context context, String string) {
        super(context, R.style.custom_progress_dialog_two_style);
        mText = string;
        myContext = context;
        init();
    }


    private void init() {
        setContentView(R.layout.custom_progress_dialog_two_layout);
        mFivIcon = (ImageView) findViewById(R.id.img);
        mHtvText = (TextView) findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                myContext, R.anim.custom_progress_dialog_two_anim);
        // 使用ImageView显示动画
        mFivIcon.startAnimation(hyperspaceJumpAnimation);
        mHtvText.setText(mText);
    }

    public void setText(String text) {
        mHtvText.setText(text);
    }

    @Override
    public void show() {
        super.show();
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                myContext, R.anim.custom_progress_dialog_two_anim);
        // 使用ImageView显示动画
        mFivIcon.startAnimation(hyperspaceJumpAnimation);
    }
}
