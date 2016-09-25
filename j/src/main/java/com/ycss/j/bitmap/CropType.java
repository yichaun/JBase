package com.ycss.j.bitmap;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/20 0020.
 */
@IntDef({CropType.CENTER_CROP, CropType.FIT_CENTER})
@Retention(RetentionPolicy.SOURCE)
public @interface CropType {
    int CENTER_CROP = 0;
    int FIT_CENTER = 1;
}
