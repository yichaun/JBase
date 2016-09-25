package com.ycss.j.bitmap;

import android.graphics.Bitmap;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/23 0023.
 */
public interface BitmapLoadListener {
    void onSuccess(Bitmap b);
    void onError();
}
