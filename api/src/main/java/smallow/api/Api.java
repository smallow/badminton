package smallow.api;

import java.util.List;

import smallow.model.RegistrationPerson;

/**
 * Created by smallow on 15/12/1.
 */
public interface Api {

    //public static String SERVER_URL = "http://smallow.imwork.net/";
    public static String SERVER_URL = "http://192.168.1.102:8080/";
    public final static String APP_KEY = "ANDROID_BADMINTON";


    // 发送验证码
    public final static String SEND_SMS_CODE = "service.sendSmsCode4Register";
    // 注册
    public final static String REGISTER = "customer.registerByPhone";
    // 登录
    public final static String LOGIN = "customer.loginByApp";

    // 报名列表
    public final static String LIST_REGISTRATION = "member.listRegistration";

    //获取当日活动信息
    public final static String GET_TODAY_ACTIVITY_RECORD="activity/getTodayActivityRecord.do";

    /**
     * 发送验证码
     *
     * @param phoneNum 手机号码
     * @return 成功时返回：{ "event": "0", "msg":"success" }
     */
    public ApiResponse<Void> sendSmsCode4Register(String phoneNum);

    /**
     * 注册
     *
     * @param phoneNum 手机号码
     * @param code     验证码
     * @param password MD5加密的密码
     * @return 成功时返回：{ "event": "0", "msg":"success" }
     */
    public ApiResponse<Void> registerByPhone(String phoneNum, String code, String password);

    /**
     * 登录
     *
     * @param loginName 登录名（手机号）
     * @param password  MD5加密的密码
     * @param imei      手机IMEI串号
     * @param loginOS   Android为1
     * @return 成功时返回：{ "event": "0", "msg":"success" }
     */
    public ApiResponse<Void> loginByApp(String loginName, String password, String imei, int loginOS);


    /**
     * 报名列表
     *
     * @param currentPage 当前页数
     * @param pageSize    每页显示数量
     * @return 成功时返回：{ "event": "0", "msg":"success", "objList":[...] }
     */
    public ApiResponse<List<RegistrationPerson>> listRegistrationPerson(int currentPage, int pageSize);
}
