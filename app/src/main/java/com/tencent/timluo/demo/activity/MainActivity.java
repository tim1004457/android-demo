package com.tencent.timluo.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tencent.timluo.demo.R;
import com.tencent.timluo.demo.adapter.ActivityAdapter;
import com.tencent.timluo.demo.manager.ForwardManager;
import com.tencent.timluo.demo.model.ActivityModule;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014/10/26.
 */
public class MainActivity extends BaseActivity {
    ListView listView = null;
    ActivityAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_main);
        initDate();
    }

    private void initDate() {
        adapter = new ActivityAdapter(this);
        ArrayList<ActivityModule> list = new ArrayList<ActivityModule>();
        String[] titleArray = getResources().getStringArray(R.array.demo_title);
        String[] forwardArray = getResources().getStringArray(R.array.demo_forward);
        for (int i = 0, n = titleArray.length; i<n; i++) {
            list.add(new ActivityModule(titleArray[i],
                                        ForwardManager.getActivityUrl(forwardArray[i]))
                    );
        }

        adapter.refresh(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ForwardManager.forward(mContext, adapter.getForwardUrl(position),
                                       adapter.getParmMap(position));
            }
        });
    }

}
