package com.tencent.timluo.demo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by timluo on 2014/11/3.
 */
public class ImageScrollView extends ScrollView<ImageView> {
    public ImageScrollView(Context context, ScrollDirection scrollDirection, ScrollMode mode) {
        super(context, scrollDirection, mode);
    }

    public ImageScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean isReadyForScrollStart() {
        return false;
    }

    @Override
    protected boolean isReadyForScrollEnd() {
        return false;
    }

    @Override
    protected ImageView createScrollContentView(Context context) {
        ImageView img = new ImageView(context);
        return img;
    }
}
