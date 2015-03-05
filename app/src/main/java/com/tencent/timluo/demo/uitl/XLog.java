package com.tencent.timluo.demo.uitl;

import android.util.Log;

import com.tencent.timluo.demo.SelfApp;

/**
 * Created by timluo on 2014/7/15.
 */
public class XLog {
    public static final void d(String tag, String msg) {
        if (SelfApp.isDev())
            Log.d(tag, msg);
    }

    public static final void d(  String msg) {
        if (SelfApp.isDev())
            Log.d(getClassName(), getCallerInfo()+":"+msg);
    }
    private static  final  String getClassName(){

        StackTraceElement[] traces = new Throwable().getStackTrace();
        if (traces.length > 3) {
            return traces[2].getClassName();
        }
        return "";
    }
    private static final String getCallerInfo() {
        StackTraceElement[] traces = new Throwable().getStackTrace();
        if (traces.length > 3) {
            return traces[2].getClassName() + "#" + traces[2].getMethodName() + "(" + traces[2].getLineNumber()+ "):";
        }
        return "";
    }
}
