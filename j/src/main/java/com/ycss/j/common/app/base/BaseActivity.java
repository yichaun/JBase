package com.ycss.j.common.app.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/7/6 0006.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener {
    protected String TAG;
    protected Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横屏
        TAG = this.getClass().getSimpleName();
        ctx = this;
        initView();
        try{
            initEvent();
            initData();
        }catch (Exception e){

        }

    }


    /**
     * 载入布局
     */
    protected abstract void initView();

    /**
     * 载入数据初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化事件
     */
    protected abstract void initEvent();
}
