package com.smallow.badminton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smallow.badminton.sys.base.BaseActivity;
import com.smallow.badminton.sys.ui.CustomProgressDialogOne;
import com.smallow.badminton.sys.ui.CustomProgressDialogThree;
import com.smallow.badminton.sys.ui.CustomProgressDialogTwo;

/**
 * Created by Administrator on 2015/10/28.
 */
public class SplashActivity extends BaseActivity {

    private Button button;
    private CustomProgressDialogOne progressDialogOne;
    private CustomProgressDialogTwo progressDialogTwo;
    private CustomProgressDialogThree progressDialogThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_splash);
        progressDialogOne = new CustomProgressDialogOne(this, "客官请稍后...");
        progressDialogTwo = new CustomProgressDialogTwo(this, "客官请稍后...");
        progressDialogThree = new CustomProgressDialogThree(this);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(progressDialogOne!=null && !progressDialogOne.isShowing()){
                    progressDialogOne.show();
                }else{
                    progressDialogOne.dismiss();
                }*/

                /*if(progressDialogTwo!=null && !progressDialogTwo.isShowing()){
                    progressDialogTwo.show();
                }else{
                    progressDialogTwo.dismiss();
                }*/

                if (progressDialogThree != null && !progressDialogThree.isShowing()) {
                    progressDialogThree.setMsg("客官别急请稍后...");
                    progressDialogThree.show();
                } else {
                    progressDialogThree.dismiss();
                }

            }
        });
    }
}
