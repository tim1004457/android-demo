package com.tencent.timluo.demo.listener;

import com.tencent.timluo.demo.manager.AsyncTaskManager;
import com.tencent.timluo.demo.manager.ReferenceModel;

import java.util.List;

/**
 * Created by timluo on 2014/8/27.
 */
public class CallbackHelper<T extends BaseCallBack> {
    ReferenceModel<T> referenceModel = new ReferenceModel<T>();

    public void register(T call) {
        referenceModel.regiestReference(call);
    }

    public void unregister(T call) {
        referenceModel.unregiestReference(call);
    }

    /*设计ICallBack的目的是避免对子类暴露listener，同时保证不需要每个子类自己实现notify方法*/

    public void notify(ICallBack<T> callBack) {
        List<T> list = referenceModel.getReference();
        for (T item : list) {
            callBack.call(item);
        }
    }

    public void notifyInUIThread(final ICallBack<T> callBack) {
        AsyncTaskManager.getInstance().
                addAsyncTaskInUiThread(new Runnable() {
                                           @Override public void run() {
                                               List<T> list = referenceModel.getReference();
                                               for (T item : list) {
                                                   callBack.call(item);
                                               }
                                           }
                                       }
                                      );
    }

    public interface ICallBack<T> {
        public void call(T callBack);
    }
}
