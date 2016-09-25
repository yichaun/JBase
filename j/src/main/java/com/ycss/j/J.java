package com.ycss.j;


import android.content.Context;
import android.text.TextUtils;

import com.ycss.j.bitmap.GlideConfig;
import com.ycss.j.bitmap.GlideManager;
import com.ycss.j.common.utils.SPUtil;
import com.ycss.j.db.DBConfig;
import com.ycss.j.db.DBManager;
import com.ycss.j.http.HttpManager;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/20 0020.
 */
public class J {

    /**
     * 初始化http
     * @param client
     * @param headers
     */
    public static void initHttp(OkHttpClient client, Map<String, String> headers) {
        HttpManager.initClient(client, headers);
    }

    public static HttpManager http() {
        return HttpManager.getInstance();
    }

    /**
     * 初始化bitmap
     * @param config
     */
    public static void initImage(GlideConfig config) {
        GlideManager.initDefConfig(config);
    }

    public static GlideManager image() {
        return GlideManager.getInstance();
    }

    /**
     * 初始化数据库
     * @param context
     * @param dbName
     * @param dbVersion
     * @param sql
     */
    public static void initDBConfig(Context context, String dbName, int dbVersion, List<String> sql) {
        DBConfig.initConfig(context, dbName, dbVersion, sql);
    }

    public static DBManager db() {
        return DBManager.getInstance();
    }

    /**
     * 设置SP文件名
     * @param fileName
     */
    public static void initSP(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            SPUtil.SP_FILE_NAME = fileName;
        }
    }


}
