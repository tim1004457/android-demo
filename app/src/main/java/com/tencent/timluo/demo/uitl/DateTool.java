package com.tencent.timluo.demo.uitl;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by timluo on 2014/8/12.
 */
public class DateTool {
    private static final HashMap<String, SimpleDateFormat> formatMap = new HashMap<String, SimpleDateFormat>(4);

    public static final long nowInMs() {
        return System.currentTimeMillis();
    }

    public static final long nowInS() {
        return System.currentTimeMillis() / 1000;
    }

    public static final String formatTime(long time, String formateString) {
        try {
            if (TextUtils.isEmpty(formateString))
                return "";
            if (formatMap.get(formateString) == null) {
                SimpleDateFormat format = new SimpleDateFormat(formateString);
                formatMap.put(formateString,format);
            }
            return formatMap.get(formateString).format(new Date(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
