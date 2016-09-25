package com.ycss.j.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/24 0024.
 */
public class DBManager {
    private static DBManager instance;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    /**
     * 私有化构造器
     */
    private DBManager(Context context) {
        //创建数据库
        dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getReadableDatabase();
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (db.isOpen()) {
            db.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
        if (instance != null) {
            instance = null;
        }
    }

    /**
     * 单例DbManager类
     *
     * @return 返回DbManager对象
     */
    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager(DBConfig.DB_CONTEXT);
        }
        return instance;
    }

    /**
     * 获取数据库的对象
     *
     * @return 返回SQLiteDatabase数据库的对象
     */
    public SQLiteDatabase getDataBase() {
        return db;
    }


    /**
     * 查询数据库的名，数据库的添加
     *
     * @param tableName  查询的数据库的名字
     * @param entityType 查询的数据库所对应的module
     * @param fieldName  查询的字段名
     * @param value      查询的字段值
     * @param <T>        泛型代表AttendInformation，Customer，Order，User，WorkDaily类
     * @return 返回查询结果，结果为AttendInformation，Customer，Order，User，WorkDaily对象
     */
    public <T> ArrayList<T> query(String tableName, Class<T> entityType, String fieldName, String value) {
        ArrayList<T> list = new ArrayList();
        Cursor cursor = instance.db.query(tableName, null, fieldName + " like ?", new String[]{value}, null, null, " id desc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String content = cursor.getString(i);//获得获取的数据记录第i条字段的内容
                String columnName = cursor.getColumnName(i);// 获取数据记录第i条字段名的
                map.put(columnName, content);
            }
            Gson gson = new Gson();
            T t = new Gson().fromJson( gson.toJson(map), entityType);
            list.add(t);
            cursor.moveToNext();
        }
        return list;
    }

//    public <T> ArrayList<T> query(String tableName, Class<T> entityType, String fieldName, String value) {
//
//        ArrayList<T> list = new ArrayList();
//        Cursor cursor = instance.db.query(tableName, null, fieldName + " like ?", new String[]{value}, null, null, " id desc", null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            try {
//                T t = entityType.newInstance();
//                for (int i = 0; i < cursor.getColumnCount(); i++) {
//                    String content = cursor.getString(i);//获得获取的数据记录第i条字段的内容
//                    String columnName = cursor.getColumnName(i);// 获取数据记录第i条字段名的
//                    Field field = entityType.getDeclaredField(columnName);//获取该字段名的Field对象。
//                    field.setAccessible(true);//取消对age属性的修饰符的检查访问，以便为属性赋值
//                    field.set(t, content);
//                    field.setAccessible(false);//恢复对age属性的修饰符的检查访问
//                }
//                list.add(t);
//                cursor.moveToNext();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//        }
//        return list;
//    }

    /**
     * 向数据库插入数据
     *
     * @param tableName 数据库插入数据的数据表
     * @param object    数据库插入的对象
     */
    public static long insert(String tableName, Object object) {
        ContentValues value = new ContentValues();
        Map<String,String> map=new HashMap<>();
        Gson gson=new Gson();
        String json=gson.toJson(object);
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        map=g.fromJson(json, new TypeToken<Map<String, String>>() {}.getType());
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            if (!entry.getKey().toString().equals("id")){
                value.put(entry.getKey().toString(),entry.getValue().toString());
            }
        }
        return instance.db.insert(tableName, null, value);
    }

//    public static void insert(String tableName, Object object) {
//
//        Class clazz = object.getClass();
//        Field[] fields = clazz.getDeclaredFields();//获取该类所有的属性
//        ContentValues value = new ContentValues();
//
//        for (Field field : fields) {
//            try {
//                field.setAccessible(true); //取消对属性的修饰符的检查访问，以便为属性赋值
//                String content;
//                try {
//                    if (field.get(object) instanceof Integer) {
//                        content = field.get(object) + "";
//                    } else {
//                        content = (String) field.get(object);
//                    }
//                } catch (ClassCastException e) {
//                    e.printStackTrace();
//                    return;
//                }
////                String content = (String) field.get(object);//获取该属性的内容
//                if (!field.getName().equals("id")) {
//                    value.put(field.getName(), content);
//                }
//                field.setAccessible(false);//恢复对属性的修饰符的检查访问
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        instance.db.insert(tableName, null, value);
//    }
    /**
     * 删除数据
     *
     * @param tableName 删除数据库的表名
     * @param fieldName 删除的字段名
     * @param value     删除的字段的值
     */
    public static void delete(String tableName, String fieldName, String value) {
        instance.db.delete(tableName, fieldName + "=?", new String[]{value});
    }

    /**
     * 更新数据库内容
     *
     * @param tableName   更改数据的数据表
     * @param columnName  更改的数据的字段名
     * @param columnValue 更改的数据的字段值
     * @param object      更改的数据
     */
    public static void update(String tableName, String columnName, String columnValue, Object object) {
        ContentValues value = new ContentValues();
        Map<String,String> map=new HashMap<>();
        Gson gson=new Gson();
        String json=gson.toJson(object);
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        map=g.fromJson(json, new TypeToken<Map<String, String>>() {}.getType());
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (!entry.getKey().toString().equals("id")){
                value.put(entry.getKey().toString(),entry.getValue().toString());
            }
        }
        instance.db.update(tableName, value, columnName + "=?", new String[]{columnValue});
    }


//    public static void uptate(String tableName, String columnName, String columnValue, Object object) {
//        try {
//            Class clazz = object.getClass();
//            Field[] fields = clazz.getDeclaredFields();//获取该类所有的属性
//            ContentValues value = new ContentValues();
//            for (Field field : fields) {
//                field.setAccessible(true); //取消对age属性的修饰符的检查访问，以便为属性赋值
//                String content = (String) field.get(object);//获取该属性的内容
//                value.put(field.getName(), content);
//                field.setAccessible(false);//恢复对age属性的修饰符的检查访问
//            }
//            instance.db.update(tableName, value, columnName + "=?", new String[]{columnValue});
//        } catch (IllegalAccessException e1) {
//            e1.printStackTrace();
//        }
//    }


}
