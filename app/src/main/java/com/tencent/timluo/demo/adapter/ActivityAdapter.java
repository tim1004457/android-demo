package com.tencent.timluo.demo.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tencent.timluo.demo.R;
import com.tencent.timluo.demo.model.ActivityModule;
import com.tencent.timluo.demo.model.AppModule;

import java.util.Map;

/**
 * Created by Administrator on 2014/10/26.
 */
public class ActivityAdapter extends BaseDemoAdapter<ActivityModule> {

    public ActivityAdapter(Context context) {
        super(context);
    }

    public String getForwardUrl(int pos) {
        ActivityModule module = getItem(pos);
        if (module != null) {
            return module.getActionUrl();
        }
        return "";
    }

    public Map<String, Parcelable> getParmMap(int pos) {
        ActivityModule module = getItem(pos);
        if (module != null) {
            return module.getActionParm();
        }
        return null;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_activity, null);
        }
        Holder holder = (Holder) convertView.getTag();
        if (holder == null) {
            holder = new Holder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
        }
        convertView.setTag(holder);
        ActivityModule module = getItem(position);
        holder.title.setText(module.getTitle());
        return convertView;
    }

    class Holder {
        TextView title;
        TextView desc;
    }
}
