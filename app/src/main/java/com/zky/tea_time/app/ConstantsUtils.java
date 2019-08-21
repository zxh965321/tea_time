package com.zky.tea_time.app;

public class ConstantsUtils {

    /*********** 公共请求头head ************/

    public final static String HEAD_PLATFORM = "platform";
    public final static String HEAD_DOWNLOAD_CHANNEL = "downloadChannel";
    public final static String HEAD_VERSIONNAME = "versionName";
    public final static String HEAD_TOKEN = "token";
    public final static String HEAD_JPUSHTOKEN = "jpushToken";
    public final static String HEAD_DEVICE_ID = "deviceId";
    public final static String HEAD_DEVICE_TYPE = "deviceType";
    public final static String HEAD_OSVERSION = "osVersion";
    public final static String HEAD_OSBRANCH = "osBranch";
    public final static String HEAD_ANDROIDID = "androidId";

    /*********** 第三方集成 ************/
    public final static String BUGLY_APPID = "e46661c4e1";

    //倒计时时间
    public final static Long TIMECURRENT = 60_000L;
    //请求最大次数
    public static final int REQUEST_MAXRETRIES = 2;
    //请求间隔
    public static final int REQUEST_RETRYDELAYSECOND = 2;

    public final static String INTENT_FLAG = "INTENT_FLAG";

    /*********** 用户体系 ************/
    //用户id
    public static final String USER_ID = "userId";
    //token
    public static final String TOKEN = "token";
    //refresh token
    public static final String REFRESH_TOKEN = "refresh_token";

    //用户信息
    public static final String USER_INFO = "user_info";
    public static final String YF_TOKEN = "yf_token";

    //用户手机号
    public static final String USER_MOBEL = "user_mobel";

    /*********** 其他 ************/
    //引导页
    public static final String GUIDE_KEY = "guide_key";
    public static boolean UPADA_KEY = true;
}