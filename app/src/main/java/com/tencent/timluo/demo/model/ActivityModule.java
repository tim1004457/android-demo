package com.tencent.timluo.demo.model;

import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.Map;

/**
 * Created by Administrator on 2014/10/26.
 */
public class ActivityModule {
    private String title;
    private String desc;
    private String actionUrl;
    private Map<String, Parcelable> actionParm;

    public Map<String, Parcelable> getActionParm() {
        return actionParm;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public ActivityModule(String title , String actionUrl) {
        this(title,"",actionUrl);
    }
    public ActivityModule(String title, String desc, String actionUrl) {
        this.title = title;
        this.desc = desc;
        this.actionUrl = actionUrl;
    }

    public void addParm(String key, Parcelable value) {
        if (!TextUtils.isEmpty(key) && value != null)
            actionParm.put(key, value);
    }
}
