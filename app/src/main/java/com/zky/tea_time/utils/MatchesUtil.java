package com.zky.tea_time.utils;

import java.util.regex.Pattern;

public class MatchesUtil {
    //手机号
    private static final String PhoneMatcher = "^(13[0-9]|14[0-9]|15[0-9]|16[0567]|17[0-9]|18[0-9]|19[9])[0-9]{8}$";
    //密码为8~16位数字和字母的组合
    private static final String PwdMatcher = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";
    //版本号仅支持数字和小数点
    private static final String VersionMatcher = "^(?:[1-9]\\d*|0)(?:\\.\\d+)?$|^(?:\\.0|0\\.)$";

    /**
     * 校核手机号
     * @param phoneStr
     * @return
     */
    public static boolean checkPhone(String phoneStr){
        return Pattern.matches(PhoneMatcher,phoneStr);
    }

    /**
     * 校核密码
     * @param pwdStr
     * @return
     */
    public static boolean checkPwd(String pwdStr){
        return Pattern.matches(PwdMatcher,pwdStr);
    }

    /**
     * 校核版本号
     * @param versionStr
     * @return
     */
    public static boolean checkVersion(String versionStr){
        return Pattern.matches(VersionMatcher,versionStr);
    }
}
