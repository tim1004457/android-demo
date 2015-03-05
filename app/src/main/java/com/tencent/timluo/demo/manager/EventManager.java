package com.tencent.timluo.demo.manager;

import android.os.Message;

import com.tencent.timluo.demo.listener.UIEventListener;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by timluo on 2014/8/28.
 */
public class EventManager {
    private static EventManager ins = null;
    private static Object LOCK = new Object();
    public static final int UI_EVENT_APP_LOAD_SUC = 0;
    private ConcurrentHashMap<Integer, ReferenceModel<UIEventListener>> uiListenerCache = null;

    public final static EventManager getInstance() {
        if (null == ins) {
            synchronized (LOCK) {
                if (null == ins) {
                    ins = new EventManager();
                }
            }
        }
        return ins;
    }

    private EventManager() {
        uiListenerCache = new ConcurrentHashMap<Integer, ReferenceModel<UIEventListener>>();
    }

    public void regiestUiEvent(final int UiEvent, final UIEventListener listener) {
        ReferenceModel<UIEventListener> uiListnerList = uiListenerCache.get(UiEvent);
        if (uiListnerList == null)
            uiListnerList = new ReferenceModel<UIEventListener>();
        uiListnerList.regiestReference(listener);
        uiListenerCache.put(UiEvent, uiListnerList);
    }

    public void unregiestUiEvent(final int uiEvent, final UIEventListener listener) {
        ReferenceModel<UIEventListener> uiListenerList = uiListenerCache.get(uiEvent);
        if (null != uiListenerList) {
            uiListenerList.unregiestReference(listener);
        }
    }

    public void notifyUiEvent(final Message msg) {
        AsyncTaskManager.getInstance().addAsyncTaskInUiThread(new Runnable() {
            @Override public void run() {
                int uiEvent = msg.what;
                final ReferenceModel<UIEventListener> uiListenerList = uiListenerCache.get(uiEvent);
                if (null != uiListenerList) {
                    List<UIEventListener> listeners = uiListenerList.getReference();
                    for (UIEventListener listener : listeners) {
                        listener.handleUiEvent(msg);
                    }
                }
            }
        });
    }
}
