package com.zky.tea_time.app;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;

import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.http.imageloader.glide.GlideImageLoaderStrategy;
import com.jess.arms.integration.ConfigModule;
import com.readystatesoftware.chuck.ChuckInterceptor;
import com.zky.tea_time.BuildConfig;
import com.zky.tea_time.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * ================================================
 * App 的全局配置信息在此配置, 需要将此实现类声明到 AndroidManifest 中
 * ConfigModule 的实现类可以有无数多个, 在 Application 中只是注册回调, 并不会影响性能 (多个 ConfigModule 在多 Module 环境下尤为受用)
 *
 * Created by MVPArmsTemplate on 01/09/2019 14:35
 */
public final class GlobalConfiguration implements ConfigModule {
//    public static String sDomain = Api.APP_DOMAIN;

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
//        if (!BuildConfig.LOG_DEBUG) { //Release 时, 让框架不再打印 Http 请求和响应的信息
//            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
//        }

        builder.baseurl(Urls.BASE_URL)
                //强烈建议自己自定义图片加载逻辑, 因为 arms-imageloader-glide 提供的 GlideImageLoaderStrategy 并不能满足复杂的需求
                //请参考 https://github.com/JessYanCoding/MVPArms/wiki#3.4
                .imageLoaderStrategy(new GlideImageLoaderStrategy())

                //想支持多 BaseUrl, 以及运行时动态切换任意一个 BaseUrl, 请使用 https://github.com/JessYanCoding/RetrofitUrlManager
                //如果 BaseUrl 在 App 启动时不能确定, 需要请求服务器接口动态获取, 请使用以下代码
                //以下方式是 Arms 框架自带的切换 BaseUrl 的方式, 在整个 App 生命周期内只能切换一次, 若需要无限次的切换 BaseUrl, 以及各种复杂的应用场景还是需要使用 RetrofitUrlManager 框架
                //以下代码只是配置, 还要使用 Okhttp (AppComponent 中提供) 请求服务器获取到正确的 BaseUrl 后赋值给 GlobalConfiguration.sDomain
                //切记整个过程必须在第一次调用 Retrofit 接口之前完成, 如果已经调用过 Retrofit 接口, 此种方式将不能切换 BaseUrl
//                .baseurl(new BaseUrl() {
//                    @Override
//                    public HttpUrl url() {
//                        return HttpUrl.parse(sDomain);
//                    }
//                })

                //可根据当前项目的情况以及环境为框架某些部件提供自定义的缓存策略, 具有强大的扩展性
//                .cacheFactory(new Cache.Factory() {
//                    @NonNull
//                    @Override
//                    public Cache build(CacheType type) {
//                        switch (type.getCacheTypeId()){
//                            case CacheType.EXTRAS_TYPE_ID:
//                                return new IntelligentCache(500);
//                            case CacheType.CACHE_SERVICE_CACHE_TYPE_ID:
//                                return new Cache(type.calculateCacheSize(context));//自定义 Cache
//                            default:
//                                return new LruCache(200);
//                        }
//                    }
//                })

                //若觉得框架默认的打印格式并不能满足自己的需求, 可自行扩展自己理想的打印格式 (以下只是简单实现)
//                .formatPrinter(new FormatPrinter() {
//                    @Override
//                    public void printJsonRequest(Request request, String bodyString) {
//                        Timber.i("printJsonRequest:" + bodyString);
//                    }
//
//                    @Override
//                    public void printFileRequest(Request request) {
//                        Timber.i("printFileRequest:" + request.url().toString());
//                    }
//
//                    @Override
//                    public void printJsonResponse(long chainMs, boolean isSuccessful, int code,
//                                                  String headers, MediaType contentType, String bodyString,
//                                                  List<String> segments, String message, String responseUrl) {
//                        Timber.i("printJsonResponse:" + bodyString);
//                    }
//
//                    @Override
//                    public void printFileResponse(long chainMs, boolean isSuccessful, int code, String headers,
//                                                  List<String> segments, String message, String responseUrl) {
//                        Timber.i("printFileResponse:" + responseUrl);
//                    }
//                })

                //可以自定义一个单例的线程池供全局使用
//                .executorService(Executors.newCachedThreadPool())

                //这里提供一个全局处理 Http 请求和响应结果的处理类, 可以比客户端提前一步拿到服务器返回的结果, 可以做一些操作, 比如 Token 超时后, 重新获取 Token
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                //用来处理 RxJava 中发生的所有错误, RxJava 中发生的每个错误都会回调此接口
                //RxJava 必须要使用 ErrorHandleSubscriber (默认实现 Subscriber 的 onError 方法), 此监听才生效
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration((context1, gsonBuilder) -> {//这里可以自己自定义配置 Gson 的参数
                    gsonBuilder
                            .serializeNulls()//支持序列化值为 null 的参数
                            .enableComplexMapKeySerialization();//支持将序列化 key 为 Object 的 Map, 默认只能序列化 key 为 String 的 Map
                })
                .retrofitConfiguration((context1, retrofitBuilder) -> {//这里可以自己自定义配置 Retrofit 的参数, 甚至您可以替换框架配置好的 OkHttpClient 对象 (但是不建议这样做, 这样做您将损失框架提供的很多功能)
//                    retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用 FastJson 替代 Gson
                })
                .okhttpConfiguration((context1, okhttpBuilder) -> {//这里可以自己自定义配置 Okhttp 的参数
//                    okhttpBuilder.sslSocketFactory(); //支持 Https, 详情请百度
                    okhttpBuilder.readTimeout( 20,TimeUnit.SECONDS );
                    okhttpBuilder.writeTimeout(20, TimeUnit.SECONDS);
                    //设置公共参数
                    okhttpBuilder.addInterceptor(getBaseParams());
                    //刷新token
//                    okhttpBuilder.addInterceptor(new RefreshTokenInterceptor());
                    //打印日志
                    okhttpBuilder.addInterceptor(getLogging());
                    //网络通知
                    okhttpBuilder.addInterceptor(new ChuckInterceptor(context1));
                    //使用一行代码监听 Retrofit／Okhttp 上传下载进度监听, 以及 Glide 加载进度监听, 详细使用方法请查看 https://github.com/JessYanCoding/ProgressManager
//                    ProgressManager.getInstance().with(okhttpBuilder);
                    //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl, 详细使用方法请查看 https://github.com/JessYanCoding/RetrofitUrlManager
//                    RetrofitUrlManager.getInstance().with(okhttpBuilder);
                })
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {//这里可以自己自定义配置 RxCache 的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    //想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 FastJson, 请 return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());
                    //否则请 return null;
                    return null;
                });
    }

    /**
     * 设置http公共参数
     * head query param
     * @return
     */
    private HttpBaseParamsInterceptor getBaseParams() {
        HttpBaseParamsInterceptor interceptor = new HttpBaseParamsInterceptor();
        interceptor.setUpdateHandler(() -> {
            List<String> urlPatternList = new ArrayList<>();
            urlPatternList.add(Urls.BASE_URL);

            HttpBaseParamsInterceptor.Builder builder = new HttpBaseParamsInterceptor.Builder()
//                    .addHeaderParam( ConstantsUtils.HEAD_TOKEN,UserManager.getToken() )
                    .addHeaderParam( ConstantsUtils.HEAD_PLATFORM,"HXJF" )
                    .addHeaderParam( ConstantsUtils.HEAD_DOWNLOAD_CHANNEL, AppUtils.getUmengChannel( MyApplication.getInstance() ) )
                    .addHeaderParam( ConstantsUtils.HEAD_VERSIONNAME, BuildConfig.VERSION_NAME )
//                    .addHeaderParam( ConstantsUtils.HEAD_JPUSHTOKEN,JPushUtil.getRegistrationID( MyApplication.getInstance() ) )
                    .addHeaderParam( ConstantsUtils.HEAD_DEVICE_ID,AppUtils.getDeviceId( MyApplication.getInstance() ) )
                    .addHeaderParam( ConstantsUtils.HEAD_DEVICE_TYPE,"android" )
                    .addHeaderParam( ConstantsUtils.HEAD_OSVERSION, Build.VERSION.RELEASE )
                    .addHeaderParam( ConstantsUtils.HEAD_OSBRANCH,Build.BRAND + " " +Build.MODEL)
                    .addHeaderParam( ConstantsUtils.HEAD_ANDROIDID,AppUtils.getAndroidId( MyApplication.getInstance() ) )
                    .addUrlPatternList(urlPatternList);//给匹配url地址设置公共参数
            return builder;
        });

        return interceptor;
    }

    /**
     * 打印日志
     *
     * @return
     */
    private HttpLoggingInterceptor getLogging() {
        HttpLoggingInterceptor.Logger logger = message -> Platform.get().log(Platform.WARN, message, null);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger);
        if (BuildConfig.LOG_DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return loggingInterceptor;
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        //AppLifecycles 中的所有方法都会在基类 Application 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(new AppLifecyclesImpl());
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        //ActivityLifecycleCallbacks 中的所有方法都会在 Activity (包括三方库) 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //添加字体标准
        lifecycles.add(new ActivityResourceLifecycleCallback());
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
        //activity管理
        lifecycles.add(new ActivityManagerLifeCallBack());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        //FragmentLifecycleCallbacks 中的所有方法都会在 Fragment (包括三方库) 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(new FragmentResourceLifecycleCallbacks());
        lifecycles.add(new FragmentLifecycleCallbacksImpl());
    }
}
