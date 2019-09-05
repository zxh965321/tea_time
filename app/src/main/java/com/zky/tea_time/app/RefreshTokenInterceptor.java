package com.zky.tea_time.app;

import com.zky.tea_time.utils.StringUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RefreshTokenInterceptor implements Interceptor {

    private static final String LOGOUT_CODE = "10001";
    private static final String REFRESH_CODE = "10022";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed( request );
        String code = response.header( "code" );
        if (StringUtils.equals( code, LOGOUT_CODE )) {
            //退出登录
            logout();
            return null;
        } else if (StringUtils.equals( code, REFRESH_CODE )) {
            //刷新token
            refreshToken();
            return null;
        }

        return response;
    }

    //刷新
    private void refreshToken(){
//        Context context = MyApplication.getInstance();
//        Map map = new HashMap<>(  );
//        map.put( "refreshToken", UserManager.getRefreshToken());
//        map.put( "token",UserManager.getToken() );
//        ArmsUtils.obtainAppComponentFromContext(context)
//                .repositoryManager()
//                .obtainRetrofitService(Api.class)
//                .refreshToken(map)
//                .compose(RxUtils.applyNoRetryAndLoading())
//                .subscribe(new ErrorHandleSubscriber<BaseDataBean<RefreshTokenBean>>(ArmsUtils.obtainAppComponentFromContext(context).rxErrorHandler()) {
//                    @Override
//                    public void onNext(BaseDataBean<RefreshTokenBean> dataBean) {
//                        if (dataBean.isSuccessful()) {
//                            if (dataBean.data != null) {
//                                UserManager.login( StringUtils.trim( dataBean.data.getRefreshToken() ),StringUtils.trim( dataBean.data.getToken() ) );
//                            }
//                        } else {
//                            logout();
//                        }
//                    }
//
//                });
    }

    //退出登录
    private void logout(){
//        UserManager.logout();
//        ActivityStackManager.getInstance().startTargetActivity( RegistOrLoginActivity.class );
//        ActivityStackManager.getInstance().clear();
    }

}
