package com.tencent.timluo.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tencent.timluo.demo.R;

/**
 * Created by Administrator on 2014/10/26.
 */
public class JniTest extends BaseActivity {
    static {
        System.loadLibrary("hello-jni");
    }

    /* A native method that is implemented by the
     * 'hello-jni' native library, which is packaged
     * with this application.
     */
    public native String stringFromJNI();

    /* This is another native method declaration that is *not*
     * implemented by 'hello-jni'. This is simply to show that
     * you can declare as many native methods in your Java code
     * as you want, their implementation is searched in the
     * currently loaded native libraries only the first time
     * you call them.
     *
     * Trying to call this function will result in a
     * java.lang.UnsatisfiedLinkError exception !
     */
    public native String unimplementedStringFromJNI();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_hello);
        TextView tView = (TextView) findViewById(R.id.jni_hello);
        tView.setText(stringFromJNI());
    }

}