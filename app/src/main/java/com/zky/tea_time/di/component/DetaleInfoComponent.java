package com.zky.tea_time.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zky.tea_time.di.module.DetaleInfoModule;
import com.zky.tea_time.mvp.contract.DetaleInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.zky.tea_time.mvp.ui.activity.DetaleInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/05/2019 09:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DetaleInfoModule.class, dependencies = AppComponent.class)
public interface DetaleInfoComponent {
    void inject(DetaleInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        DetaleInfoComponent.Builder view(DetaleInfoContract.View view);

        DetaleInfoComponent.Builder appComponent(AppComponent appComponent);

        DetaleInfoComponent build();
    }
}