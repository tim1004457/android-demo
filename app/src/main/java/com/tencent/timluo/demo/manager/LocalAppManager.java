package com.tencent.timluo.demo.manager;

import android.os.Message;

import com.tencent.timluo.demo.controller.AppController;
import com.tencent.timluo.demo.listener.AppLoaderCallback;
import com.tencent.timluo.demo.model.AppModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by timluo on 2014/8/28.
 */
public class LocalAppManager implements AppLoaderCallback {
    private static LocalAppManager ins = null;
    private static Object LOCK = new Object();
    private List<AppModule> appList = null;

    public final static LocalAppManager getInstance() {
        if (null == ins) {
            synchronized (LOCK) {
                if (null == ins) {
                    ins = new LocalAppManager();
                }
            }
        }
        return ins;
    }

    private LocalAppManager() {
        AppController.getInstance().register(this);
        appList = new ArrayList<AppModule>();
    }

    public void requestAppList() {
        AppController.getInstance().loadLocalAppList();
    }

    public void onDestroy() {
        AppController.getInstance().unregister(this);
        ins = null;
    }

    @Override public void onAppLoaderFinish(List<AppModule> list) {
        Message msg = Message.obtain();
        msg.what = EventManager.UI_EVENT_APP_LOAD_SUC;
        msg.obj = list;
        EventManager.getInstance().notifyUiEvent(msg);
    }
}
