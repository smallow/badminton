package smallow.core;

import java.util.List;

import smallow.model.RegistrationPerson;

/**
 * Created by smallow on 15/12/1.
 */
public interface AppAction {
    // 发送手机验证码
    public void sendSmsCode(String phoneNum, ActionCallbackListener<Void> listener);

    // 注册
    public void register(String phoneNum, String code, String password, ActionCallbackListener<Void> listener);

    // 登录
    public void login(String loginName, String password, ActionCallbackListener<Void> listener);

    // 按分页获取报名列表
    public void listCoupon(int currentPage, ActionCallbackListener<List<RegistrationPerson>> listener);
}
