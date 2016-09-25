package com.ycss.j.common.app.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.ycss.j.common.app.widget.LoadingDialog;
import com.ycss.j.common.utils.L;

import java.util.Map;

/**
 * Created by 王剑洪 2016/1/20.
 */
public abstract class BaseFragmentActivity extends FragmentActivity implements OnClickListener {
    protected String TAG =this.getClass().getSimpleName();
    protected Context ctx;
    protected LoadingDialog loadingDialog;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止横屏
        ctx = this;
        try {
            // 修改状态栏颜色，4.4+生效
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus();
            }
            int barBg=initBarBg();
            if (barBg != -1) {
                SystemBarTintManager tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintResource(initBarBg());//通知栏所需颜色
            }
            initView();
            initLoadingDialog();
            initData();
            initEvent();
        } catch (Exception e) {
            L.e(TAG, e.getMessage());
        }

    }

    /**
     * 返回界面布局文件
     *
     * @return
     */
    protected abstract int initBarBg();

    protected void initLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(ctx);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    public void showLoading(String text) {
        loadingDialog.setText(text);
        loadingDialog.show();
    }

    public void hideLoading() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
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
     * 事件
     */
    protected abstract void initEvent();

    @Override
    public void onClick(View v) {
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 绑定ID
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T bind(int viewId) {
        View view = findViewById(viewId);
        return (T) view;
    }

    /**
     * 跳转到Activity
     *
     * @param cls 目标Activity
     * @param map 传递的参数，可为null
     */
    public void gotoActivity(Class<?> cls, @Nullable Map<String, Object> map) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (null != map) {
            SerializableMap serializableMap = new SerializableMap();
            serializableMap.setMap(map);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Map", serializableMap);
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public Map<String, Object> getMap() {
        if (getIntent().hasExtra("Map")) {
            SerializableMap map = (SerializableMap) getIntent().getSerializableExtra("Map");
            return map.getMap();
        } else {
            return null;
        }
    }


    @TargetApi(19)
    protected void setTranslucentStatus() {
        Window window = getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }


}
