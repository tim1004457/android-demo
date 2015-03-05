package com.tencent.timluo.demo.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.timluo.demo.R;
import com.tencent.timluo.demo.SelfApp;
import com.tencent.timluo.demo.uitl.DateTool;
import com.tencent.timluo.demo.uitl.XLog;

public class DActivity extends ActionBarActivity {
    private Context mContext = null;
    private static final String ALARM_TEST = "com.tencent.test.alarm";
    private BroadcastReceiver receive = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            long current = DateTool.nowInMs();
            Toast.makeText(SelfApp.getContext(), DateTool.formatTime(current, "yyyy:MM:dd hh:mm:ss"), Toast.LENGTH_LONG).show();
            XLog.d("receive alarm:" + DateTool.formatTime(current, "yyyy:MM:dd hh:mm:ss"));
        }
    };

    private void registReceive() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ALARM_TEST);
        mContext.registerReceiver(receive, filter);
    }

    private void unRegistReceive() {
        mContext.unregisterReceiver(receive);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);
        mContext = this;
        registReceive();
        Button setAlarmButton = (Button) findViewById(R.id.set_alarm_button);
        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、创建intent
                Intent intent = new Intent(ALARM_TEST);
                //2.创建pendingIntent
                PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                //3.获取系统AlarmManager
                AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(ALARM_SERVICE);
                //4-2 使用setRepeating创建Alarm
                alarmManager.setRepeating(AlarmManager.RTC, DateTool.nowInMs(), 60*15*1000, pendingIntent);
             }
        });
    }

    @Override
    protected void onDestroy() {
        unRegistReceive();
        super.onDestroy();
    }
}
