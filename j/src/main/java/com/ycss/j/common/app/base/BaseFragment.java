package com.ycss.j.common.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ycss.j.common.app.widget.LoadingDialog;

import java.util.Map;

/**
 * Created by 王剑洪 2016/1/20.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected View root;
    protected BaseCallBack listener;
    protected LayoutInflater inflater;
    protected ViewGroup container;
    protected LoadingDialog loadingDialog;
    protected int lanType=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.container = container;
        this.inflater = inflater;
        initLoadingDialog();
        initView();
        try{
            initEvent();
            initData();
        }catch (Exception e){

        }

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initLoadingDialog() {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(getActivity());
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
    protected void initData() {
    }

    /**
     * 初始化事件
     */
    protected void initEvent() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 懒加载
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于Fragment的onResume
        } else {
            // 相当于Fragment的onPause

        }
    }

    @Override
    public void onClick(View v) {
    }

    /**
     * 绑定ID
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T bind(int viewId) {
        View view = root.findViewById(viewId);
        return (T) view;
    }

    public void setCallBackListener(BaseCallBack callBack) {
        this.listener = callBack;
    }

    /**
     * 跳转到Activity
     *
     * @param cls 目标Activity
     * @param map 传递的参数，可为null
     */
    public void gotoActivity(Class<?> cls, Map<String, Object> map) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (null != map) {
            SerializableMap serializableMap = new SerializableMap();
            serializableMap.setMap(map);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Map", serializableMap);
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}
