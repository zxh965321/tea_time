package com.zky.tea_time.app;

public class MessageEvent {

    private int code;
    private String str;
    private Object object;

    public MessageEvent(int code, String str, Object object) {
        this.code = code;
        this.str = str;
        this.object = object;

    }

    public MessageEvent(int code){
        this.code = code;
        this.str = "";
        this.object = null;
    }

    /**
     * 如果是此事件
     * @param event
     * @return
     */
    public boolean isEvent(int event){
        if (getCode() == event) {
            return true;
        }
        return false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public static final int MESSAGE_PUSH_RECEIVE_RED_POINT = 100; //收到推送消息展示消息中心入口红点
    public static final int APP_UPDATE_NOW = 101; //更新
    public static final int APP_UPDATE_AFTER = 102; //非强制更新
    public static final int APP_LOGOUT = 103; //登出
    public static final int HOMEREFRESHSTATUE = 104; //刷新首页
    public static final int MINE_REFRESH = 105; //我的界面刷新
    public static final int REFRESH_ORDER_FRAGMENT_PAGE = 106; //刷新账单页面
    public static final int BINDCARD_EVENT = 107; //解绑事件
    public static final int REGIST_EVENT = 108; //解绑事件
    public static final int REFRESH_ORDER_LIST_EVENT = 109; //刷新订单列表

    public static final int  CLICK_HOME_PAGE_EVENT=200; //跳转至首页
    public static final int  CLICK_ORDER_PAGE_EVENT=201;//跳转至订单页
    public static final int  CLICK_MINE_PAGE_EVENT=202; //跳转至我的页面
}
