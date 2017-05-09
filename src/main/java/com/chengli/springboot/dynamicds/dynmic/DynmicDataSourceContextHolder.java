package com.chengli.springboot.dynamicds.dynmic;

public class DynmicDataSourceContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static String getDataSourceKey() {
		return contextHolder.get();
	}

	public static void setDataSourceKey(String dataSourcekey) {
		System.out.println(dataSourcekey);
		contextHolder.set(dataSourcekey);
	}
}
