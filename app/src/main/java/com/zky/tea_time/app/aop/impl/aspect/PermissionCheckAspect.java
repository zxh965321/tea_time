package com.zky.tea_time.app.aop.impl.aspect;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.hjq.toast.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zky.tea_time.app.aop.annotation.aspect.Permission;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 *
 */
@Aspect
public class PermissionCheckAspect {

    @Pointcut("execution(@com.hexin.wealth.app.aop.annotation.aspect.Permission * *(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //获得注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Permission permission = method.getAnnotation(Permission.class);

        final Object object = joinPoint.getThis();
        if (object == null) return;

        Context context = null;
        if (object instanceof Context) {
            context = (Context) object;
        } else if (object instanceof Fragment) {
            context = ((Fragment) object).getActivity();
        } else if (object instanceof android.support.v4.app.Fragment) {
            context = ((android.support.v4.app.Fragment) object).getActivity();
        }else if(object instanceof Activity){
            context = (Context) object;
        }else if(object instanceof FragmentActivity){
            context = (Context) object;
        }else if (object instanceof AppCompatActivity){
            context = (Context) object;
        }else {
            context = null;
        }
        if (context == null) return;

        RxPermissions rxPermissions = new RxPermissions( (FragmentActivity) context );
        rxPermissions.request(permission.value())
                .subscribe(grant -> {
                    if (grant) {
                        //全部通过
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    } else {
                        if (!TextUtils.isEmpty(permission.msg()))
                            ToastUtils.show(permission.msg());
                    }
                });
    }

}
