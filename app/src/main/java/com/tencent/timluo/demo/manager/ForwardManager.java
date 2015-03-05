package com.tencent.timluo.demo.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.tencent.timluo.demo.SelfApp;
import com.tencent.timluo.demo.uitl.XLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by timluo on 2014/8/12.
 */
public class ForwardManager {
    public static final String ACTIVITY_FORWARD_PREFIX = "demo://activity";
    public static final String KEY_CLASS_NAME = "class";
    private static Map<String, String> classNameMap = new HashMap<String, String>();
    public static final String TAG = "ForwardManager";

    static {
        try {
            PackageManager pm = SelfApp.getContext().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(SelfApp.getContext().getPackageName(),
                                                        PackageManager.GET_ACTIVITIES);
            for (ActivityInfo activityInfo : packageInfo.activities) {
                classNameMap.put(activityInfo.name.substring(activityInfo.name.lastIndexOf(".")+1),
                                 activityInfo.name);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final boolean onActivity(Context mContext, String uri,
                                            Map<String, Parcelable> parmMap) {
        if (TextUtils.isEmpty(uri) || !uri.startsWith(ACTIVITY_FORWARD_PREFIX))
            return false;
        Uri forwardUri = Uri.parse(uri);
        String className = forwardUri.getQueryParameter(KEY_CLASS_NAME);
        if (TextUtils.isEmpty(className))
            return false;
        openActivity(mContext, className, parmMap);
        return false;
    }

    public static final String getActivityUrl(String className) {
        return ACTIVITY_FORWARD_PREFIX + "?" + KEY_CLASS_NAME + "=" + className;
    }

    private static final void openActivity(Context mContext, String className,
                                           Map<String, Parcelable> parmMap) {
        Intent intent = new Intent();
        intent.setClassName(mContext, classNameMap.get(className));
        int flag = Intent.FLAG_ACTIVITY_NEW_TASK;
        if (parmMap != null) {
            Bundle bundle = new Bundle();
            for (String key : parmMap.keySet()) {
                if (TextUtils.isEmpty(key))
                    continue;
                Parcelable parcelable = parmMap.get(key);
                if (parcelable != null) {
                    bundle.putParcelable(key, parcelable);
                }
            }
            intent.putExtras(bundle);
        }
        intent.setFlags(flag);
        mContext.startActivity(intent);
    }

    public static void forward(Context mContext, String url, Map<String, Parcelable> map) {
        if (!onActivity(mContext, url, map)) {

        }
    }
}
