package com.plugin.spacerimport.contrast;



import com.google.gson.JsonElement;
import com.plugin.json.Json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypeContrast {

	//数据转换工具
	private class json extends Json {
		@Override
		protected <T> T fromJson(String json, Type type) {
			return super.fromJson(json, type);
		}

		@Override
		protected <T> T fromJson(JsonElement json, Type type) {
			return super.fromJson(json, type);
		}

		@Override
		protected String toJson(Object src) {
			return super.toJson(src);
		}
	}


	/**
	 * 动态代理接口的参数，数组
	 */
	private List<Object> objectargs = new ArrayList<Object>();

	/**
	 * 需要掉的方法的参数，数组
	 */
	private List<Class> classargs = new ArrayList<Class>();

	/**
	 * 转换结果
	 */
	private List<Object> conversionargs = new ArrayList<Object>();

	/**
	 * @param classargs  (远程)需要调的方法的参数，数组
	 * @param objectargs 动态代理接口的参数，数组
	 * @return
	 */
	public TypeContrast(Class<?>[] classargs, Object... objectargs) {
		if(objectargs != null && classargs.length > 0 && objectargs.length > 0){
			this.objectargs.addAll(Arrays.asList(objectargs));
			this.classargs.addAll(Arrays.asList(classargs));
		}
	}

	/**
	 * 参数数量比较
	 *
	 * @return
	 */
	private boolean arguments() {
		return classargs.size() == objectargs.size();
	}

	/**
	 * 输入参数 类型转换
	 * @return
	 */
	public boolean Contrast() {
		if (!arguments()) {
			return false;
		}
		for (int i = 0; i < objectargs.size(); i++) {
			Object o = objectargs.get(i);
			Class c = classargs.get(i);
			//判断是否为系统基础变量
			if (o.getClass().isPrimitive() && c.isPrimitive()) {
				if (o.getClass().getName().equals(c.getName())) {
					conversionargs.add(o);
					continue;
				}
			}
			//判断类型是否相同
			if (o.getClass().equals(c)) {
				conversionargs.add(o);
				continue;
			}
			//region 判断int与Integer等包装类
			/*if(c.isPrimitive()){
				if(o.getClass().getName().equals("java.lang.Integer") && c.getName().equals("int")){
					conversionargs.add(o);
					continue;
				}
				if(o.getClass().getName().equals("java.lang.Short") && c.getName().equals("short")){
					conversionargs.add(o);
					continue;
				}
				if(o.getClass().getName().equals("java.lang.Long") && c.getName().equals("long")){
					conversionargs.add(o);
					continue;
				}
			}*/
			//endregion

			//类型不一致,强制类型转换
			try {
				Object oc  = FromJsonToObject(o,c);
				conversionargs.add(oc);
			} catch (Exception e) {
				e.printStackTrace();
				conversionargs.add(null);
			}
		}
		return true;
	}

	/**
	 * @param classargs  (远程)需要调的方法的参数，数组
	 * @param objectargs 动态代理接口的参数，数组
	 * @return
	 */
	private Object FromJsonToObject(Object objectargs,Class classargs){
		final json json = new json();
		return json.fromJson(json.toJson(objectargs),(Type)classargs);
	}

	/**
	 * 获取转换结果
	 * @return
	 */
	public List<Object> getconversionargs(){
		return this.conversionargs;
	}

}
