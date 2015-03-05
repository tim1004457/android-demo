package com.tencent.timluo.demo.listener;

import com.tencent.timluo.demo.model.AppModule;

import java.util.List;

/**
 * Created by timluo on 2014/8/28.
 */
public interface AppLoaderCallback extends  BaseCallBack {
    public void onAppLoaderFinish(List<AppModule> list);
}
