package com.zky.tea_time.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zky.tea_time.di.module.BookModule;
import com.zky.tea_time.mvp.contract.BookContract;

import com.jess.arms.di.scope.ActivityScope;
import com.zky.tea_time.mvp.ui.activity.BookActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 15:06
 */
@ActivityScope
@Component(modules = BookModule.class, dependencies = AppComponent.class)
public interface BookComponent {
    void inject(BookActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        BookComponent.Builder view(BookContract.View view);

        BookComponent.Builder appComponent(AppComponent appComponent);

        BookComponent build();
    }
}