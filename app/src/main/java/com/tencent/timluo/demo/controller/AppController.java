package com.tencent.timluo.demo.controller;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tencent.timluo.demo.SelfApp;
import com.tencent.timluo.demo.listener.AppLoaderCallback;
import com.tencent.timluo.demo.listener.CallbackHelper;
import com.tencent.timluo.demo.manager.AsyncTaskManager;
import com.tencent.timluo.demo.model.AppModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timluo on 2014/8/28.
 */
public class AppController extends CallbackHelper<AppLoaderCallback> {
    /*APK未加载*/
    private static final int LOAD_STATUS_NOT_LOAD = 0;
    /*APK加载中*/
    private static final int LOAD_STATUS_LOADING = LOAD_STATUS_NOT_LOAD + 1;
    /*APK加载成功*/
    private static final int LOAD_STATUS_LOAD_SUC = LOAD_STATUS_LOADING + 1;
    private static AppController ins = null;
    private Context mContext = null;
    private static Object LOCK = new Object();
    /*保存APP信息*/
    private List<AppModule> appModuleList = null;
    /*记录加载状态*/
    private int loadStatus = 0;

    public final static AppController getInstance() {
        if (null == ins) {
            synchronized (LOCK) {
                if (null == ins) {
                    ins = new AppController();
                }
            }
        }
        return ins;
    }

    private AppController() {
         mContext = SelfApp.getContext();
    }

    /*加载本地APP信息*/
    public void loadLocalAppList() {
        if (LOAD_STATUS_NOT_LOAD == loadStatus) {
            AsyncTaskManager.getInstance().addAsynvTask(new Runnable() {
                @Override public void run() {
                    if ( LOAD_STATUS_NOT_LOAD != loadStatus )
                        return ;
                    loadStatus = LOAD_STATUS_LOADING;
                    PackageManager packageManager = mContext.getPackageManager();
                    List<PackageInfo> pkgList = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES);
                    appModuleList = new ArrayList<AppModule>(pkgList.size());
                    for (PackageInfo info : pkgList) {
                        appModuleList.add(new AppModule(info));
                    }
                    notifyListener();
                    loadStatus = LOAD_STATUS_LOAD_SUC;
                }
            });
        } else if (LOAD_STATUS_LOAD_SUC == loadStatus) {
            notifyListener();
        }
    }

    private void notifyListener() {
        notify(new ICallBack<AppLoaderCallback>() {
            @Override public void call(AppLoaderCallback callBack) {
                callBack.onAppLoaderFinish(appModuleList);
            }
        });
    }

}
