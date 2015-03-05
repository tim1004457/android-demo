package com.tencent.timluo.demo.model;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.tencent.timluo.demo.SelfApp;

/**
 * Created by timluo on 2014/7/20.
 */
public class AppModule {
    private PackageInfo pkgInfo;
    private String appName ;
    private Drawable icon;
    public AppModule(PackageInfo pkgInfo) {
        this.pkgInfo = pkgInfo;
        PackageManager pm = SelfApp.getContext().getPackageManager();
        icon = pkgInfo.applicationInfo.loadIcon(pm);
        appName = (String) pm.getApplicationLabel(pkgInfo.applicationInfo);
    }
    public boolean isSystemApp(){
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }
    public String  getPackageName()
    {
        return pkgInfo.packageName;
    }
    public String getAppName() {
        return appName;
    }
    public String getVersionName(){
        return pkgInfo.versionName;
    }
    public int getVersionCode(){
        return pkgInfo.versionCode;
    }
    public Drawable getIcon() {
        return icon;
    }
}
