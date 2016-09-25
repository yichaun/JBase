package com.ycss.j.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/24 0024.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static  DBHelper instance;

    //构造器,传入四个参数Context对象，数据库名字name，操作数据库的Cursor对象，版本号version。
    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //为了简化构造器的使用，我们自定义一个构造器
    private DBHelper(Context context, String name) {
        this(context, name, null, DBConfig.DB_VERSION);//传入Context和数据库的名称，调用上面那个构造器
    }
    //将自定义的数据库创建类单例。
    public static  synchronized  DBHelper getInstance(Context context) {
        if(instance==null){
            instance = new DBHelper(context, DBConfig.DB_NAME+"_"+DBConfig.DB_VERSION+".db");
        }
        return  instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //在创建数据库时，初始化创建数据库中包含的数据库表
        for (int i=0;i<DBConfig.CREATE_TABLE_SQL.size();i++){
            sqLiteDatabase.execSQL(DBConfig.CREATE_TABLE_SQL.get(i));
        }


    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //用于升级数据库，只需要在创建本类对象时传入一个比之前创建传入的version大的数即可。
    }



}
