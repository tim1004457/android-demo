package com.tencent.timluo.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.tencent.timluo.demo.R;


/**
 * Created by timluo on 2014/7/20.
 */
public class TxPageView  extends LinearLayout {
    private Context mContext;
    private ListView listView;
    private  void initView(){
        listView= (ListView) findViewById(R.id.list_view_item);
    }

    public TxPageView(Context context, AttributeSet attrs, Context mContext) {
        super(context, attrs);
        this.mContext = mContext;
        initView();
    }

    public TxPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }
}
