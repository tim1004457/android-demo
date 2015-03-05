package com.tencent.timluo.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.tencent.timluo.demo.listener.UIEventListener;
import com.tencent.timluo.demo.manager.EventManager;

/**
 * Created by timluo on 2014/8/30.
 */
public abstract class BaseActivity extends Activity {
    protected Context mContext;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getBaseContext();
    }

    public void regiestUiEvent(UIEventListener listener, Integer... event) {
        for (Integer e : event) {
            EventManager.getInstance().regiestUiEvent(e, listener);
        }
    }

    public void unregiestEvent(UIEventListener listener, Integer... event) {
        for (Integer e : event) {
            EventManager.getInstance().unregiestUiEvent(e, listener);
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }
}
