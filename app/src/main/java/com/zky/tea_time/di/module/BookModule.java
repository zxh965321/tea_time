package com.zky.tea_time.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.zky.tea_time.mvp.contract.BookContract;
import com.zky.tea_time.mvp.model.BookModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 15:06
 */
@Module
public abstract class BookModule {

    @Binds
    abstract BookContract.Model bindBookModel(BookModel model);
}