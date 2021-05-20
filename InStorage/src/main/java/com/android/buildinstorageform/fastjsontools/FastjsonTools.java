package com.android.buildinstorageform.fastjsontools;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;

/**
 * ������
 * 
 * @author sowhat
 * 
 */
public class FastjsonTools {
	/**
	 * 将jsonString转换成ArrayList数组
	 * @param jsonString
	 * @param jsonPath
	 * @param cls
	 * @return ArrayList<T>
	 */
	public static <T, cls> ArrayList<T> jsonStringParseToArrayList(String jsonString, String jsonPath, Class<T> cls) {
		ArrayList<T> arrayList = new ArrayList<>();
		try {
			Object jsonObject = JSONPath.read(jsonString,jsonPath);
			arrayList = (ArrayList<T>)JSON.parseArray(jsonObject.toString(),cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arrayList;
	}
	/**
	 * 将jsonString转换成List数组
	 * @param jsonString
	 * @param jsonPath
	 * @param cls
	 * @return List<T>
	 */
	public static <T> List<T> jsonStringParseToList(String jsonString, String jsonPath, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			list =(List<T>)JSONPath.read(jsonString, jsonPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}