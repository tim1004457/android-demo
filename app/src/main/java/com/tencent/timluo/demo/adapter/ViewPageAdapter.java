package com.tencent.timluo.demo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.timluo.demo.uitl.XLog;

import java.util.List;

/**
 * Created by timluo on 2014/7/20.
 */
public class ViewPageAdapter extends PagerAdapter {
    private  List<  View> list = null;
    public ViewPageAdapter(List<   View> views)  {
       list = views;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
     }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
     }
    @Override
    public int getCount() {
        XLog.d("count:"+(list == null ? 0:list.size()));
        return list == null ? 0:list.size() ;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        list.remove(position);
    }
}
