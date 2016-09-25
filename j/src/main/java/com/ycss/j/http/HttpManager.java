package com.ycss.j.http;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/18 0018.
 */
public class HttpManager {


    private volatile static HttpManager instance;
    private OkHttpClient client;
    private Map<String, String> headers;

    public HttpManager(OkHttpClient client, Map<String, String> headers) {
        if (null == client) {
            this.client = new OkHttpClient();
            this.headers=new HashMap<>();
        } else {
            this.headers=headers;
            this.client = client;
        }
    }

    public static HttpManager initClient(OkHttpClient client, Map<String, String> headers) {
        if (null == instance) {
            synchronized (HttpManager.class) {
                if (null == instance) {
                    OkHttpUtils.initClient(client);
                    instance = new HttpManager(client,headers);
                }
            }
        }

        return instance;
    }



    public static HttpManager getInstance() {
        return initClient(null,null);
    }

    public static void get(String url, Map<String, String> headers, Map<String, String> params, int id, Object tag, final HttpCallBack callBack) {
        callBack.onStart(id);
        GetBuilder getBuilder = OkHttpUtils.get();
        getBuilder = getBuilder.url(url);
        if (null != headers) {
            getBuilder.headers(headers);
        }else {
            if (null!=getInstance().headers){
                getBuilder.headers(getInstance().headers);
            }
        }
        if (null != params) {
            getBuilder.params(params);
        }
        if (id > 0) {
            getBuilder.id(id);
        }
        if (null != tag) {
            getBuilder.tag(tag);
        }
        getBuilder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                callBack.onFailure(call, e, id);
                callBack.onFinish(id);
            }

            @Override
            public void onResponse(String response, int id) {
                callBack.onSuccess(response, id);
                callBack.onFinish(id);
            }
        });
    }


    public static void get(String url, Map<String, String> params, int id, Object tag, HttpCallBack callBack) {
        get(url, null, params, id, tag, callBack);
    }

    public static void get(String url, int id, Object tag, HttpCallBack callBack) {
        get(url, null, null, id, tag, callBack);
    }

    public static void get(String url, int id, HttpCallBack callBack) {
        get(url, null, null, id, null, callBack);
    }

    public static void get(String url) {
        get(url, null, null, 0, null, new HttpCallBack() {
            @Override
            public void onStart(int id) {

            }

            @Override
            public void onLoading(float progress, long total, int id) {

            }

            @Override
            public void onSuccess(String response, int id) {

            }

            @Override
            public void onFailure(Call call, Exception e, int id) {

            }

            @Override
            public void onFinish(int id) {

            }
        });
    }

    public static void post(String url, Map<String, String> headers, Map<String, String> params, int id, Object tag, final HttpCallBack callBack) {
        callBack.onStart(id);
        PostFormBuilder getBuilder = OkHttpUtils.post();
        getBuilder = getBuilder.url(url);
        if (null != headers) {
            getBuilder.headers(headers);
        }else {
            if (null!=getInstance().headers){
                getBuilder.headers(getInstance().headers);
            }
        }
        if (null != params) {
            getBuilder.params(params);
        }
        if (id > 0) {
            getBuilder.id(id);
        }
        if (null != tag) {
            getBuilder.tag(tag);
        }
        getBuilder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                callBack.onFailure(call, e, id);
                callBack.onFinish(id);
            }

            @Override
            public void onResponse(String response, int id) {
                callBack.onSuccess(response, id);
                callBack.onFinish(id);
            }
        });
    }

    public static void post(String url, Map<String, String> params, int id, Object tag, HttpCallBack callBack) {
        get(url, null, params, id, tag, callBack);
    }

    public static void post(String url, int id, Object tag, HttpCallBack callBack) {
        get(url, null, null, id, tag, callBack);
    }

    public static void post(String url, int id, HttpCallBack callBack) {
        get(url, null, null, id, null, callBack);
    }

    public static void post(String url) {
        get(url, null, null, 0, null, new HttpCallBack() {
            @Override
            public void onStart(int id) {

            }

            @Override
            public void onLoading(float progress, long total, int id) {

            }

            @Override
            public void onSuccess(String response, int id) {

            }

            @Override
            public void onFailure(Call call, Exception e, int id) {

            }

            @Override
            public void onFinish(int id) {

            }
        });
    }


    public static void postJson(String url, BaseRequest body, int id, Object tag, final HttpCallBack callBack) {
        callBack.onStart(id);
        PostStringBuilder builder = OkHttpUtils.postString();
        builder.url(url);
        if (id>0){
            builder.id(id);
        }
        if (null!=tag){
            builder.tag(tag);
        }
        builder.mediaType(MediaType.parse("application/json; charset=utf-8"));
        if (null==body){
            builder.content("{}");
        }else {
            builder.content(body.toString());
        }

        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                callBack.onFailure(call, e, id);
                callBack.onFinish(id);
            }

            @Override
            public void onResponse(String response, int id) {
                callBack.onSuccess(response, id);
                callBack.onFinish(id);
            }
        });
    }


    public static void postJson(String url, BaseRequest body, int id, HttpCallBack callBack){
        postJson(url,body,id,null,callBack);
    }
    public static void postJson(String url, int id, Object tag, HttpCallBack callBack){
        postJson(url,null,id,tag,callBack);
    }
    public static void postJson(String url, int id, HttpCallBack callBack){
        postJson(url,null,id,null,callBack);
    }
    public static void postJson(String url, HttpCallBack callBack){
        postJson(url,null,0,null,callBack);
    }
    public static void postJson(String url){
        postJson(url, null, 0, null, new HttpCallBack() {
            @Override
            public void onStart(int id) {

            }

            @Override
            public void onLoading(float progress, long total, int id) {

            }

            @Override
            public void onSuccess(String response, int id) {

            }

            @Override
            public void onFailure(Call call, Exception e, int id) {

            }

            @Override
            public void onFinish(int id) {

            }
        });
    }






}
