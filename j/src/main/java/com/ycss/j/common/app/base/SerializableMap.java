package com.ycss.j.common.app.base;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by 王剑洪
 * 2016/1/20.
 */
@SuppressWarnings("serial")
public class SerializableMap implements  Serializable{
    private Map<String, Object> map;

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
