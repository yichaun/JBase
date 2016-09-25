package com.ycss.j.http;

import com.google.gson.Gson;

import java.io.Serializable;

/**
 * TODO:
 * Created by 王剑洪
 * on 2016/9/18 0018.
 */
public class BaseRequest implements Serializable{
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
