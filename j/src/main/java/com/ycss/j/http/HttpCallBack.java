package com.ycss.j.http;

import okhttp3.Call;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/18 0018.
 */
public interface HttpCallBack {

    void onStart(int id);
    void onLoading(float progress, long total, int id);
    void onSuccess(String response, int id);
    void onFailure(Call call, Exception e, int id);
    void onFinish(int id);


}
