package com.github.codegen.util;
/**
 * StringUtils
 * @author LB
 * @date 2019-1-20 下午5:31:17
 * @version 1.0
 */
public class StringUtils {
	/**
	 * 首字母转换方法(首字母大写)
	 * @param str
	 * @return
	 */
	public static String transferUpper(String str){
		String prefix = str.substring(0, 1).toUpperCase();
		return prefix + str.substring(1);
	}

	/**
	 * 首字母转换方法(首字母大写)
	 * @param str
	 * @return
	 */
	public static String transferLower(String str){
		String prefix = str.substring(0, 1).toLowerCase();
		return prefix + str.substring(1);
	}
}