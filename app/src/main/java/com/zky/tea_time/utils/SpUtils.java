package com.zky.tea_time.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.zky.tea_time.app.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * sp存储
 */
public class SpUtils {

    public static final String CONFIG = "hexin_wealth";

    private static SpUtils spUtils;

    private SharedPreferences sharedPreferences;

    private SpUtils(){
        sharedPreferences = MyApplication.getInstance().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    }

    private static class Holder{
        private static final SpUtils singleton = new SpUtils();
    }

    public static SharedPreferences getSharedPreferences() {
        return Holder.singleton.sharedPreferences;
    }

    //存储
    public static void putString(String key, String value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        boolean b = sharedPreferences.edit().putString(key, value).commit();
    }

    //存储boolean值
    public static void putBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static void putInt( String key, int value) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        sharedPreferences.edit().putInt(key, value).commit();
    }

    //获取

    public static String getString( String key, String defValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getString(key, defValue);
    }

    //获取boolean值
    public static boolean getBoolean( String key, boolean defValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static int getInt( String key, int defValue) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        return sharedPreferences.getInt(key, defValue);
    }

    //移除某一个key
    public static void remove( String keyName) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        sharedPreferences.edit().remove(keyName).commit();
    }

    public static void clear() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        sharedPreferences.edit().clear().commit();
    }

    /**
     * 存储对象到本地
     *
     * @param obj
     */
    public static void saveObjectToLocal( Object obj, String key) throws IOException {
        saveObject(serialize(obj), key);
    }

    public static Object getObjectFromLocal( String key) throws IOException, ClassNotFoundException {
        return deSerialization(getString( key, null));
    }

    public static void saveObject( String strObject, String key) {
        putString(key, strObject);
    }

    /**
     * 序列化对象
     *
     * @return
     * @throws IOException
     */
    private static String serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        String serStr = byteArrayOutputStream.toString("ISO-8859-1");
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return serStr;
    }

    /**
     * 反序列化对象
     *
     * @param str
     * @return
     * @throws IOException
     */
    private static Object deSerialization(String str) throws IOException, ClassNotFoundException {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String redStr = java.net.URLDecoder.decode(str, "UTF-8");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                redStr.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        Object obj = (Object) objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return obj;
    }

}
