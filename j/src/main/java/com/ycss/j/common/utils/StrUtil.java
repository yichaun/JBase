package com.ycss.j.common.utils;

import android.content.Context;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/25 0025.
 */
public class StrUtil {
    /**
     * 获取string.xml中的资源
     * @param context
     * @param id
     * @return
     */
    public static String getString(Context context,int id) {
        return context.getResources().getString(id);
    }

    /**
     *
     * @param context
     * @param id    <string name="alert">我的名字叫%1$s，我来自%2$s</string>
     * @param args
     * @return
     */
    public static String format(Context context,int id,Object... args){
        return String.format(getString(context,id),args);
    }
}
