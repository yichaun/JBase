package com.ycss.j.common.app.base;

import com.google.gson.Gson;

import java.io.Serializable;


public class BaseBean implements Serializable{

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
