package com.zy.common.utils;


public class StringUtils extends org.springframework.util.StringUtils {
	
	public static String[] cloneStrings(String[] variables) {
		String[] result = null;
		if(variables!=null){
			result = new String[variables.length];
			for (int i = 0; i < variables.length; i++) {
				result[i]=variables[i];
			}
		}
		return result;
	}


	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

}
