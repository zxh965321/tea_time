package com.zky.tea_time.app.utils;

import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;
import com.zky.tea_time.app.ConstantsUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * ================================================
 * 放置便于使用 RxJava 的一些工具方法
 * <p>
 * Created by MVPArmsTemplate on 01/09/2019 14:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class RxUtils {

    private RxUtils() {
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(RxLifecycleUtils.bindToLifecycle(view));
            }
        };
    }

    public static <T> ObservableTransformer<T, T> apply(final IView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .compose( RxLifecycleUtils.bindToLifecycle( view ) )
                        .retryWhen( new RetryWithDelay( ConstantsUtils.REQUEST_MAXRETRIES, ConstantsUtils.REQUEST_RETRYDELAYSECOND ) )
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                view.showLoading();//显示进度条
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() {
                                view.hideLoading();//隐藏进度条
                            }
                        });
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applyNoLoading(final IView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .compose( RxLifecycleUtils.bindToLifecycle( view ) )
                        .retryWhen( new RetryWithDelay( ConstantsUtils.REQUEST_MAXRETRIES, ConstantsUtils.REQUEST_RETRYDELAYSECOND ) );
            }
        };
    }

    /**
     * 核心交易不要retry
     * @param view
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applyNoRetry(final IView view) {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() )
                        .compose( RxLifecycleUtils.bindToLifecycle( view ) )
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                view.showLoading();//显示进度条
                            }
                        })
                        .doFinally(new Action() {
                            @Override
                            public void run() {
                                view.hideLoading();//隐藏进度条
                            }
                        });
            }
        };
    }

    /**
     * 核心交易不要retry
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applyNoRetryAndLoading() {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn( Schedulers.io() )
                        .observeOn( AndroidSchedulers.mainThread() );
            }
        };
    }

}
