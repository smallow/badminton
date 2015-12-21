package smallow.core;

import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

import smallow.api.Api;
import smallow.api.ApiImpl;
import smallow.api.ApiResponse;
import smallow.api.net.HttpEngine;
import smallow.api.utils.HttpUtils;
import smallow.model.ActivityRecord;
import smallow.model.RegistrationPerson;

/**
 * Created by smallow on 15/12/1.
 */
public class AppActionImpl implements AppAction {

    private final static int LOGIN_OS = 1; // 表示Android
    private final static int PAGE_SIZE = 20; // 默认每页20条

    private Context context;
    private Api api;


    public AppActionImpl(Context context) {
        this.context = context;
        this.api = new ApiImpl();
    }

    @Override
    public void sendSmsCode(String phoneNum, ActionCallbackListener<Void> listener) {

    }

    @Override
    public void register(String phoneNum, String code, String password, ActionCallbackListener<Void> listener) {

    }

    @Override
    public void login(final String loginName, final String password, final ActionCallbackListener<Void> listener) {
        // 参数为空检查
        if (TextUtils.isEmpty(loginName)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "登录名为空");
            }
            return;
        }
        if (TextUtils.isEmpty(password)) {
            if (listener != null) {
                listener.onFailure(ErrorEvent.PARAM_NULL, "密码为空");
            }
            return;
        }
        // 请求Api
        new AsyncTask<Void, Void, ApiResponse<Void>>() {
            @Override
            protected ApiResponse<Void> doInBackground(Void... voids) {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId();
                return api.loginByApp(loginName, password, imei, LOGIN_OS);
            }

            @Override
            protected void onPostExecute(ApiResponse<Void> response) {
                if (listener != null & response != null) {
                    if (response.isSuccess()) {
                        listener.onSuccess(null);
                    } else {
                        listener.onFailure(response.getEvent(), response.getMsg());
                    }
                }
            }
        }.execute();
    }

    @Override
    public void listRegistration(final int currentPage, final ActionCallbackListener<List<RegistrationPerson>> listener) {
        /*new AsyncTask<Void,Void,ApiResponse<List<RegistrationPerson>>>(){
            @Override
            protected ApiResponse<List<RegistrationPerson>> doInBackground(Void... voids) {
                return api.listRegistrationPerson(currentPage, PAGE_SIZE);
            }

            @Override
            protected void onPostExecute(ApiResponse<List<RegistrationPerson>> listApiResponse) {
                if (listener != null && listApiResponse != null) {
                    if (listApiResponse.isSuccess()) {
                        listener.onSuccess(listApiResponse.getObjList());
                    } else {
                        listener.onFailure(listApiResponse.getEvent(), listApiResponse.getMsg());
                    }
                }
            }
        }.execute();*/
        RequestParams params = new RequestParams();
        params.put("id", "1");
        params.put("name", "张三");
        HttpUtils.get(HttpEngine.SERVER_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final String result = new String(responseBody);
                Gson gson = new Gson();
                Type type = new TypeToken<ApiResponse<List<RegistrationPerson>>>() {
                }.getType();
                ApiResponse<List<RegistrationPerson>> apiResponse = gson.fromJson(result, type);
                if (apiResponse.isSuccess()) {
                    listener.onSuccess(apiResponse.getObjList());
                } else {
                    listener.onFailure(apiResponse.getEvent(), apiResponse.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ApiResponse<List<RegistrationPerson>> apiResponse = new ApiResponse<List<RegistrationPerson>>("1", "失败");
                listener.onFailure(apiResponse.getEvent(), apiResponse.getMsg());
            }
        });
    }

    @Override
    public void getTodayActivityRecord(final ActionCallbackListener<ActivityRecord> listener) {
        RequestParams params = new RequestParams();
        params.put("appKey", Api.APP_KEY);
        HttpUtils.get(Api.SERVER_URL + Api.GET_TODAY_ACTIVITY_RECORD, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                final String json = new String(responseBody);
                Gson gson = new Gson();
                Type type = new TypeToken<ApiResponse<ActivityRecord>>() {
                }.getType();
                ApiResponse<ActivityRecord> apiResponse = gson.fromJson(json, type);
                if (apiResponse.isSuccess()) {
                    listener.onSuccess(apiResponse.getObj());
                } else {
                    listener.onFailure(apiResponse.getEvent(), apiResponse.getMsg());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                ApiResponse<ActivityRecord> apiResponse = new ApiResponse<ActivityRecord>("1", "失败");
                listener.onFailure(apiResponse.getEvent(), apiResponse.getMsg());
            }
        });
    }
}
