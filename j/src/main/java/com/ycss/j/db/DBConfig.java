package com.ycss.j.db;

import android.content.Context;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/24 0024.
 */
public class DBConfig {

    public static String DB_NAME="J";
    public static int DB_VERSION=1;
    public static List<String> CREATE_TABLE_SQL=creteTableSql(null);
    public static Context DB_CONTEXT;

    public static List<String> creteTableSql(@Nullable List<String> list){
        if (null==list){
            list=new ArrayList<>();
        }
        if (list.isEmpty()){
            list.add("CREATE Table J (id integer primary key autoincrement, name varchar(20), amount integer,J integer)");
        }
        CREATE_TABLE_SQL=list;
        return list;
    }

    public static void initConfig(Context context,String dbName, int dbVersion, List<String> sql){
        DB_CONTEXT=context;
        DB_NAME=dbName;
        DB_VERSION=dbVersion;
        creteTableSql(sql);
    }





}
