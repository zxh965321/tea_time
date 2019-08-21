package com.zky.tea_time.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zky.tea_time.di.module.SplashModule;
import com.zky.tea_time.mvp.contract.SplashContract;

import com.jess.arms.di.scope.ActivityScope;
import com.zky.tea_time.mvp.ui.activity.SplashActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/21/2019 18:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent {
    void inject(SplashActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SplashComponent.Builder view(SplashContract.View view);

        SplashComponent.Builder appComponent(AppComponent appComponent);

        SplashComponent build();
    }
}