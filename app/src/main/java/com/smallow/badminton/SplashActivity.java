package com.smallow.badminton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.smallow.badminton.activity.LoginActivity;
import com.smallow.badminton.sys.Constant;
import com.smallow.badminton.sys.base.BaseActivity;
import com.smallow.badminton.sys.utils.HttpUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import smallow.core.ActionCallbackListener;
import smallow.model.Member;

/**
 * Created by Administrator on 2015/10/28.
 */
public class SplashActivity extends BaseActivity {

    private ImageView iv_start;
    private SharedPreferences sharedPreferences;
    private BadmintonApplication application;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_splash);
        sharedPreferences = getSharedPreferences("badminton", MODE_PRIVATE);
        iv_start = (ImageView) findViewById(R.id.iv_start);
        application = ((BadmintonApplication) getApplication());
        initImage();
    }

    private void initImage() {
        File dir = getFilesDir();
        final File imgFile = new File(dir, "start.jpg");
        if (imgFile.exists()) {
            iv_start.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
        } else {
            iv_start.setImageDrawable(getResources().getDrawable(R.drawable.start));
        }


        final ScaleAnimation scaleAnim = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                HttpUtils.get(Constant.BASEURL + Constant.START, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        try {
                            JSONObject jsonObject = new JSONObject(new String(responseBody));
                            String url = jsonObject.getString("img");
                            HttpUtils.get(url, new BinaryHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                                    saveImage(imgFile, binaryData);
                                    startActivity();
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {
                                    startActivity();
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        startActivity();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        iv_start.startAnimation(scaleAnim);

    }


    private void startActivity() {
        boolean isFirstLogin = sharedPreferences.getBoolean("firstLogin", true);
        if (!isFirstLogin) {
            String mobile = sharedPreferences.getString("mobile", "");
            String pwd = sharedPreferences.getString("pwd", "");
            goLogin(mobile, pwd);
        } else {
            sharedPreferences.edit().putBoolean("firstLogin", false).commit();
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            finish();
        }

    }

    private void goLogin(String mobile, String pwd) {
        application.getAppAction().login(mobile, pwd, new ActionCallbackListener<Member>() {
            @Override
            public void onSuccess(Member data) {
               // dismissLoadingDialog();
                //Toast.makeText(SplashActivity.this,"登陆成功:"+data.getName(),Toast.LENGTH_SHORT).show();
                saveLoginInfo(data);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                finish();
            }

            @Override
            public void onFailure(String errorEvent, String message) {
                Toast.makeText(SplashActivity.this, message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                finish();
            }
        });

    }

    public void saveImage(File file, byte[] bytes) {
        try {
            file.delete();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveLoginInfo(Member data) {
        sharedPreferences.edit().putInt("loginUserId",data.getId()).commit();
        sharedPreferences.edit().putInt("groupId",data.getGroupId()).commit();
        sharedPreferences.edit().putString("name", data.getName()).commit();
        sharedPreferences.edit().putString("roleType",data.getRoleType()).commit();
        sharedPreferences.edit().putString("mobile",data.getMobile()).commit();
        sharedPreferences.edit().putString("pwd",data.getPwd());

        //sharedPreferences.edit().putInt("")

    }
}
