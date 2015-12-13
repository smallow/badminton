package smallow.api;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import smallow.api.net.HttpEngine;
import smallow.api.utils.EncryptUtil;
import smallow.model.RegistrationPerson;

/**
 * Created by smallow on 15/12/1.
 */
public class ApiImpl implements Api{
    private final static String APP_KEY = "ANDROID_BADMINTON";
    private final static String TIME_OUT_EVENT = "CONNECT_TIME_OUT";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    // http引擎
    private HttpEngine httpEngine;

    public ApiImpl() {
        httpEngine = HttpEngine.getInstance();
    }

    @Override
    public ApiResponse<Void> sendSmsCode4Register(String phoneNum) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appKey", APP_KEY);
        paramMap.put("method", SEND_SMS_CODE);
        paramMap.put("phoneNum", phoneNum);

        Type type = new TypeToken<ApiResponse<Void>>(){}.getType();
        try {
            return httpEngine.postHandle(paramMap, type);
        } catch (IOException e) {
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<Void> registerByPhone(String phoneNum, String code, String password) {
        return null;
    }

    @Override
    public ApiResponse<Void> loginByApp(String loginName, String password, String imei, int loginOS) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appKey", APP_KEY);
        paramMap.put("method", LOGIN);
        paramMap.put("loginName", loginName);
        paramMap.put("password", EncryptUtil.makeMD5(password));
        paramMap.put("imei", imei);
        paramMap.put("loginOS", String.valueOf(loginOS));

        Type type = new TypeToken<ApiResponse<List<RegistrationPerson>>>(){}.getType();
        try {
            return httpEngine.postHandle(paramMap, type);
        } catch (IOException e) {
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }

    @Override
    public ApiResponse<List<RegistrationPerson>> listRegistrationPerson(int currentPage, int pageSize) {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("appKey", APP_KEY);
        paramMap.put("method", LIST_REGISTRATION);
        paramMap.put("currentPage", String.valueOf(currentPage));
        paramMap.put("pageSize", String.valueOf(pageSize));

        Type type = new TypeToken<ApiResponse<List<RegistrationPerson>>>(){}.getType();
        try {
            return httpEngine.postHandle(paramMap, type);
        } catch (IOException e) {
            return new ApiResponse(TIME_OUT_EVENT, TIME_OUT_EVENT_MSG);
        }
    }
}
