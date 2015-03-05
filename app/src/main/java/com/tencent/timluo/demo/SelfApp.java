package com.tencent.timluo.demo;

import android.app.AlarmManager;
import android.app.Application;
import android.content.Context;

/**
 * Created by timluo on 2014/7/14.
 */
public class SelfApp extends Application {
    private static Context selfApp = null;
    @Override
    public void onCreate() {
        super.onCreate();
        selfApp = getApplicationContext();
    }
    public static final Context getContext() {
        return selfApp;
    }

    public static final boolean isDev() {
        return true;
    }
    public static final AlarmManager getAlarmManager(){
        return (AlarmManager) selfApp.getSystemService(ALARM_SERVICE);
    }

}
