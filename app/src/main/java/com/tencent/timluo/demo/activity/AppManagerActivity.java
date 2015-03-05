package com.tencent.timluo.demo.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.timluo.demo.R;
import com.tencent.timluo.demo.adapter.AppAdapter;
import com.tencent.timluo.demo.adapter.ViewPageAdapter;
import com.tencent.timluo.demo.listener.UIEventListener;
import com.tencent.timluo.demo.manager.EventManager;
import com.tencent.timluo.demo.manager.LocalAppManager;
import com.tencent.timluo.demo.model.AppModule;

import java.util.ArrayList;
import java.util.List;


public class AppManagerActivity extends BaseActivity implements UIEventListener {
    View userAppView;
    View systemAppView;
    private ViewPager viewPager = null;
    private ViewPageAdapter viewPageAdapter = null;
    private LayoutInflater inflater = null;
    private TextView userAppTitle;
    private TextView systemAppTitle;
    private int currentPage = -1;
    ListView userAppListView, systemListView;
    AppAdapter userAppAdapter, systemAppAdapter;

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.view_page);
        userAppTitle = (TextView) findViewById(R.id.user_app_title);
        systemAppTitle = (TextView) findViewById(R.id.system_app_title);
        userAppTitle.setOnClickListener(onClickListener);
        systemAppTitle.setOnClickListener(onClickListener);
        inflater = getLayoutInflater();
        List<View> viewList = new ArrayList<View>();
        userAppView = inflater.inflate(R.layout.activity_list_view, null);
        systemAppView = inflater.inflate(R.layout.activity_list_view, null);
        userAppListView = (ListView) userAppView.findViewById(R.id.list_view_item);
        userAppAdapter = new AppAdapter(this);
        userAppListView.setAdapter(userAppAdapter);
        systemListView = (ListView) systemAppView.findViewById(R.id.list_view_item);
        systemAppAdapter = new AppAdapter(this);
        systemListView.setAdapter(systemAppAdapter);
        viewList.add(userAppListView);
        viewList.add(systemListView);
        viewPageAdapter = new ViewPageAdapter(viewList);
        viewPager.setAdapter(viewPageAdapter);
        onPageChanged(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onPageChanged(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int toPage = 0;
            if (v == userAppTitle)
                toPage = 0;
            else
                toPage = 1;
            onPageChanged(toPage);
        }
    };

    private void onPageChanged(int pos) {
        if (pos == currentPage)
            return;
        currentPage = pos;
        if (currentPage == 0) {
            userAppTitle.setSelected(true);
            systemAppTitle.setSelected(false);
        } else {
            userAppTitle.setSelected(false);
            systemAppTitle.setSelected(true);
        }
        if (viewPager != null)
            viewPager.setCurrentItem(currentPage);
    }

    private void initDate() {
        LocalAppManager.getInstance().requestAppList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pkg);
        initUI();
        initDate();
        regiestUiEvent(this, EventManager.UI_EVENT_APP_LOAD_SUC);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        unregiestEvent(this, EventManager.UI_EVENT_APP_LOAD_SUC);
    }


    @Override public void handleUiEvent(Message msg) {
        switch (msg.what) {
            case EventManager.UI_EVENT_APP_LOAD_SUC: {
                if (msg.obj != null) {
                    List<AppModule> appModuleList = (List<AppModule>) msg.obj;
                    userAppListView.getAdapter();
                    List<AppModule> userappList = new ArrayList<AppModule>(appModuleList.size());
                    List<AppModule> systemAppList = new ArrayList<AppModule>(appModuleList.size());
                    for (AppModule app : appModuleList) {
                        if (app.isSystemApp()) {
                            systemAppList.add(app);
                        } else {
                            userappList.add(app);
                        }
                    }
                    userAppAdapter.setAppList(userappList);
                    systemAppAdapter.setAppList(systemAppList);
                }
            }
            break;
            default:
                break;
        }
    }
}
