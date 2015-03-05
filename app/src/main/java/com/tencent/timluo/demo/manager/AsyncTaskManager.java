package com.tencent.timluo.demo.manager;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 对外暴露，添加异步任务
 * Created by timluo on 2014/8/27.
 */
public class AsyncTaskManager {
    private static AsyncTaskManager ins = null;
    private static final Object lock = new Object();
    private ExecutorService service = null;
    private Handler mainHandler = null;

    private AsyncTaskManager() {
        service = Executors.newCachedThreadPool();
        mainHandler = new Handler(Looper.getMainLooper());
    }


    public static final AsyncTaskManager getInstance() {
        if (null == ins) {
            synchronized (lock) {
                if (null == ins)
                    ins = new AsyncTaskManager();
            }
        }
        return ins;
    }

    public void addAsyncTaskInUiThread(Runnable runnable) {
        mainHandler.post(runnable);
    }

    public void addAsynvTask(Runnable runnable) {
        service.submit(runnable);
    }

    public void onDestroy() {
        service.shutdown();
    }
}

