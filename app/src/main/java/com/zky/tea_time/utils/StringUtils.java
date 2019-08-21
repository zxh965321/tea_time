package com.zky.tea_time.utils;

import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串统一处理工具
 *
 * @author chentong
 * @desc 避免边界判空不严格
 */
public class StringUtils {
    public static String hide4PhoneNum(String phoneNum) {
        String phoneNo = trim(phoneNum);
        if (!TextUtils.isEmpty(phoneNo) && phoneNo.length() == 11) {
            return phoneNo.substring(0, 3) + " **** " + phoneNo.substring(7, 11);
        }
        return "";
    }

    public static boolean isContainNumber(@NotNull String company) {
        return Pattern.compile("[0-9]").matcher((CharSequence) company).find();
    }


    public static boolean isContainLetter(@NotNull String str) {
        return Pattern.compile(".*[a-zA-Z]+.*").matcher((CharSequence) str).matches();
    }

    /**
     * 去除标题中花括号
     *
     * @param str
     * @return
     */
    public static String replaceWebTitle(String str) {
        String title = trim(str);
        return title.replace("《", "").replace("》", "");
    }

    /**
     * 隐藏银行卡号
     *
     * @param cardNo
     * @return
     */
    public static String hideBankCardNo(String cardNo) {
        String bankno = trim(cardNo);
        int length = bankno.length();
        if (length < 8) return "xxxx***********xxxx";
        String hideNo = bankno.substring(0, 4) + "***********" + bankno.substring(length - 4, length);
        return hideNo;
    }

    /**
     * 获得银行卡号后四位
     *
     * @param cardNo
     * @return
     */
    public static String getLastFourCardNo(String cardNo) {
        String cardstr = trim(cardNo);
        if (cardstr.length() < 4) return "";
        return cardstr.substring(cardstr.length() - 4, cardstr.length());
    }

    /**
     * 对手机号进行整理格式化
     *
     * @param phoneNo
     * @return
     */
    public static String clearPhoneNo(String phoneNo) {
        String phone = trim(phoneNo);
        return phone.replace(" ", "").replace("-", "");
    }

    /**
     * replace函数安全处理
     *
     * @param str
     * @param oldChar
     * @param newChar
     * @return
     */
    public static String replace(String str, String oldChar, String newChar) {
        if (TextUtils.isEmpty(str)) return "";
        if (oldChar == null || newChar == null) return str;
        return str.replace(oldChar, newChar);
    }

    /**
     * 字符串比较
     *
     * @param leftStr
     * @param rightStr
     * @return
     */
    public static boolean equals(String leftStr, String rightStr) {
        if (leftStr == null || rightStr == null) return false;
        String str1 = trim(leftStr);
        String str2 = trim(rightStr);
        return str1.equals(str2);
    }

    public static boolean contains(String content, String str) {
        if (content == null) return false;
        String str1 = trim(content);
        String str2 = trim(str);
        return str1.contains(str2);
    }

    /**
     * 字符串不相等
     *
     * @param leftStr
     * @param rightStr
     * @return
     */
    public static boolean isNotEquals(String leftStr, String rightStr) {
        return !equals(leftStr, rightStr);
    }

    //清空空格
    public static String trim(String str) {
        if (isEmpty(str)) return "";
        return str.trim();
    }

    //判断是否是空字符串  纯空格也包含
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.toString().trim().length() == 0;
    }

    /**
     * 内容不为空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    //内容长度
    public static int length(String str) {
        return trim(str).length();
    }

    /**
     * startsWith 以prefix开始
     */
    public static boolean startsWith(String str, String prefix) {
        return trim(str).startsWith(trim(prefix));
    }

    /**
     * 判断手机号是否正确
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() < 11) {
            return false;
        } else {
            Pattern pattern = Pattern.compile(regex);
            Matcher mat = pattern.matcher(phone);
            return mat.matches();
        }
    }

    /**
     * 是否包含中文
     */
    public static boolean isContainsChinese(String str) {
        String regEx = "[\\u4e00-\\u9fa5]+";
        return Pattern.compile(regEx).matcher((CharSequence) str).find();
    }

    /**
     * 是否全部是中文
     */
    public static boolean isAllChinese(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断名字
     */
    public static boolean isMatchName(String str) {
        String regEx = "^[\\u4e00-\\u9fa5]{0,}";
        return Pattern.compile(regEx).matcher((CharSequence) str).find();
    }

    /**
     * 判断密码
     */
    public static boolean isMatchPasswd(String str) {
        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{8,16}";
        return Pattern.compile(regEx).matcher((CharSequence) str).find();
    }

    /**
     * 判断idNo
     */
    public static boolean isMatchIdNo(String str) {
        String regEx = "\\d{17}[[0-9],0-9xX]";
        return Pattern.compile(regEx).matcher((CharSequence) str).find();
    }

    /**
     * 追加字符串
     *
     * @param frontStr
     * @param bindStr
     * @return
     */
    public static String appendStrings(String frontStr, String bindStr) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(frontStr);
        buffer.append(bindStr);
        return buffer.toString();
    }

    /**
     * 校核字符串。空。0 。0.0 纯输出0.00格式
     *
     * @param str
     * @return
     */
    public static String checkDoubleStr(String str) {
        String result = trim(str);
        if (isEmpty(result) || result.equals("0") || result.equals("0.0"))
            return "0.00";
        else
            return result;
    }

    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return 结果说明：0代表相等，1代表version1大于version2，-1代表version1小于version2
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        Log.d("HomePageActivity", "version1Array==" + version1Array.length);
        Log.d("HomePageActivity", "version2Array==" + version2Array.length);
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        Log.d("HomePageActivity", "verTag2=2222=" + version1Array[index]);
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 字符串 千位符  保留两位小数点后两位
     *
     * @param num
     * @return
     */
    public static String num2thousand00(String num) {
        String numStr = "";
        if (isEmpty(num)) {
            return numStr;
        }
        NumberFormat nf = NumberFormat.getInstance();
        try {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            numStr = df.format(nf.parse(num));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numStr;
    }

    public static String shutNullStr(String str) {

        String result = trim(str);
        return result.replace(" ", "");
    }

    public static boolean isIdCardNum(String str) {
        str = str.trim();
        Pattern pattern = Pattern.compile("[0-9]*");
        if (str.contains("X") || str.contains("x")) {
            if ((str.endsWith("x")||str.endsWith("X"))&& pattern.matcher(str.substring(0,str.length()-1)).matches()){
                return true;
            }else
                return false;
        } else {
            Matcher isNum = pattern.matcher(str);
            if (!isNum.matches()) {
                return false;
            }
            return true;
        }
    }
}
