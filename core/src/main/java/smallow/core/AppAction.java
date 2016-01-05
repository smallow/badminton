package smallow.core;

import java.util.List;

import smallow.model.ActivityRecord;
import smallow.model.Member;
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
    public void login(String loginName, String password, ActionCallbackListener<Member> listener);

    // 按分页获取报名列表
    public void listRegistration(int currentPage, ActionCallbackListener<List<RegistrationPerson>> listener);

    //获取当日活动信息
    public void getTodayActivityRecord(Integer groupId,ActionCallbackListener<ActivityRecord> listener);

    //报名今日活动
    public void registrateTodayActivity(Integer memberId,Integer activityRecordId,Integer groupId,ActionCallbackListener listener);

    //群主或者管理发起活动
    public void createActivityRecord(String date,String chargePerson,String playFieldNum,String startTime,String endTime,String venue ,Integer groupId,String contactNumber,ActionCallbackListener listener);



}
