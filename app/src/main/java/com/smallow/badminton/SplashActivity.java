package com.smallow.badminton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smallow.badminton.sys.base.BaseActivity;
import com.smallow.badminton.sys.ui.CustomProgressDialogOne;

/**
 * Created by Administrator on 2015/10/28.
 */
public class SplashActivity extends BaseActivity{

    private Button button;
    private CustomProgressDialogOne progressDialogOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_splash);
        progressDialogOne=new CustomProgressDialogOne(this,"");
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressDialogOne!=null && !progressDialogOne.isShowing()){
                    progressDialogOne.show();
                }else{
                    progressDialogOne.dismiss();
                }


            }
        });
    }
}
