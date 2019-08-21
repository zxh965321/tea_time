package com.zky.tea_time.app;


import com.zky.tea_time.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class TargetContainer {

    //忘记密码
    public static final String PAGE_FORGET_PASSWD = "page_forget_passwd";
    //平台公告
    public static final String PAGE_PLATFORM_ANNOUNCEMENT = "page_platform_announcement";
    //home
    public static final String PAGE_HOME = "page_home";
    //风险提示页
    public static final String PAGE_RISK_TIP = "page_risk_tip";
    //开户页
    public static final String PAGE_OPEN_ACCOUNT = "page_open_account";
    //协议
    public static final String PAGE_AGREEMENT = "page_agreement";
    //协议
    public static final String PAGE_RISK_RESULT = "page_risk_result";

    private static Map<String, Class> targetMap = new HashMap<>();

    static {
//        targetMap.put(PAGE_PLATFORM_ANNOUNCEMENT, PlatformAnnouncementActivity.class);
//        targetMap.put(PAGE_HOME, MainActivity.class);
    }

    private static Class getTargetActivityClazz(String target) {
        return targetMap.get(target);
    }

    public static void startTargetActivity() {
        String target = ActivityStackManager.getInstance().getTargetPage();
        if (StringUtils.isEmpty(target)) return;
        Class activityClazz = getTargetActivityClazz(target);
        ActivityStackManager.getInstance().startTargetActivity(activityClazz);
    }

}
