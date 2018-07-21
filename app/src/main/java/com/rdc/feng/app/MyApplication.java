package com.rdc.feng.app;

import android.app.Application;
import android.content.Context;

/**
 * @author Feng Zhaohao
 * Created on 2018/7/20
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //获取Context
        context = getApplicationContext();
    }

    //返回
    public static Context getContextObject() {
        return context;
    }
}
