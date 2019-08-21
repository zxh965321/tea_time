package com.zky.tea_time.utils;

import java.math.BigDecimal;

/**
 * 数字金额计算
 * 保留2位小数
 * ROUND_DOWN模式
 */
public class NumberUtils {

    /**
     * 字符串转换Int
     * @param s
     * @return
     */
    public static int toInt(String s){
        String value = trim( s );
        try{
            return Integer.parseInt( value );
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * 加法
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String add(Object num1, Object num2) {
        String d1Str = numToString( num1 );
        String d2Str = numToString( num2 );
        BigDecimal bg1 = new BigDecimal( d1Str );
        BigDecimal bg2 = new BigDecimal( d2Str );
        return scaleToString(bg1.add( bg2 ));
    }

    /**
     * 减法
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String sub(Object num1, Object num2) {
        String d1Str = numToString( num1 );
        String d2Str = numToString( num2 );
        BigDecimal bg1 = new BigDecimal( d1Str );
        BigDecimal bg2 = new BigDecimal( d2Str );
        return scaleToString(bg1.subtract( bg2 ));
    }

    /**
     * 乘法
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String muti(Object num1, Object num2) {
        String d1Str = numToString( num1 );
        String d2Str = numToString( num2 );
        BigDecimal bg1 = new BigDecimal( d1Str );
        BigDecimal bg2 = new BigDecimal( d2Str );
        return scaleToString(bg1.multiply( bg2 ));
    }

    /**
     * 除法
     *
     * @param num1
     * @param num2
     * @return
     */
    public static String div(Object num1, Object num2) {
        String d1Str = numToString( num1 );
        String d2Str = numToString( num2 );
        BigDecimal bg1 = new BigDecimal( d1Str );
        BigDecimal bg2 = new BigDecimal( d2Str );
        //判断被除数是否为0
        if(bg2.compareTo( BigDecimal.ZERO ) == 0){
            return scaleToString( new BigDecimal( "0" ) );
        }
        return scaleToString(bg1.divide( bg2, 2, BigDecimal.ROUND_HALF_UP ));
    }

    /**
     * 保留2位小数
     * @param decimal
     * @return
     */
    private static String scaleToString(BigDecimal decimal){
        return decimal.setScale( 2, BigDecimal.ROUND_DOWN).toString();
    }

    public static double toDouble(String num) {
        String value = trim( num );
        double ret = 0.00;
        try {
            ret = Double.parseDouble( value );
        } catch (Exception e) {
            ret = 0.00;
        } finally {
            return ret;
        }
    }

    /**
     * 数字转字符串
     *
     * @param num
     * @return
     */
    public static String numToString(Object num) {
        if (num instanceof Double) {
            return Double.toString( (Double) num );
        } else if (num instanceof String) {
            double ret = toDouble( (String) num );
            return Double.toString( ret );
        } else if (num instanceof Integer) {
            return Integer.toString( (Integer) num );
        } else if (num instanceof Float) {
            return Float.toString( (Float) num );
        } else if (num instanceof Long) {
            return Long.toString( (Long) num );
        } else {
            return "0";
        }
    }

    private static String trim(String str) {
        if (isEmpty( str )) return "";
        return str.trim();
    }

    private static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

}
