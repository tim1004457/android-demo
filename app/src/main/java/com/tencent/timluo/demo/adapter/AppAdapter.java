package com.tencent.timluo.demo.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.timluo.demo.R;
import com.tencent.timluo.demo.SelfApp;
import com.tencent.timluo.demo.model.AppModule;

import java.util.List;

/**
 * Created by timluo on 2014/7/15.
 */
public class AppAdapter extends BaseAdapter {
    private List<AppModule> date = null;
    private Context mContext = null;
    private LayoutInflater inflater;
    private PackageManager pm;

    public AppAdapter( Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        pm = SelfApp.getContext().getPackageManager();
    }
    public void setAppList(List<AppModule> list )
    {
        if ( null != list) {
            this.date = list;
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return date == null ? 0 : date.size();
    }

    @Override
    public Object getItem(int position) {
        return date.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.adapter_view, null);
            holder = new Holder();
            holder.iconView = (ImageView) convertView.findViewById(R.id.icon_view);
            holder.appNameView = (TextView) convertView.findViewById(R.id.app_name);
            holder.pkgNameView = (TextView) convertView.findViewById(R.id.pkg_name);
            holder.versionNameView = (TextView) convertView.findViewById(R.id.app_version_name);
            holder.versionCodeView = (TextView) convertView.findViewById(R.id.app_version_code);
            convertView.setTag(holder);
        }
        holder = (Holder) convertView.getTag();
        AppModule item = (AppModule) getItem(position);
        holder.iconView.setImageDrawable(item.getIcon());
        if (!TextUtils.isEmpty(item.getAppName()))
            holder.appNameView.setText(item.getAppName()   );
        holder.pkgNameView.setText(item.getPackageName());
        holder.versionCodeView.setText(item.getVersionCode() + "");
        holder.versionNameView.setText(item.getVersionName());
        return convertView;
    }

    class Holder {
        ImageView iconView;
        TextView appNameView;
        TextView pkgNameView;
        TextView versionNameView;
        TextView versionCodeView;
    }
}
